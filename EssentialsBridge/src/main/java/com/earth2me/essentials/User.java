package com.earth2me.essentials;

import com.earth2me.essentials.commands.IEssentialsCommand;
import com.earth2me.essentials.messaging.IMessageRecipient;
import com.earth2me.essentials.messaging.SimpleMessageRecipient;
import com.earth2me.essentials.register.payment.Method;
import com.earth2me.essentials.register.payment.Methods;
import com.earth2me.essentials.utils.DateUtil;
import com.earth2me.essentials.utils.FormatUtil;
import com.earth2me.essentials.utils.NumberUtil;

import bssentials.commands.Afk;
import net.ess3.api.IEssentials;
import net.ess3.api.MaxMoneyException;
import net.ess3.api.events.AfkStatusChangeEvent;
import net.ess3.api.events.JailStatusChangeEvent;
import net.ess3.api.events.MuteStatusChangeEvent;
import net.ess3.api.events.UserBalanceUpdateEvent;
import net.ess3.nms.refl.ReflUtil;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.earth2me.essentials.I18n.tl;

public class User extends UserData implements Comparable<User>, IMessageRecipient, net.ess3.api.IUser {

    private static final Logger logger = Logger.getLogger("Essentials");
    private IMessageRecipient messageRecipient;
    private transient UUID teleportRequester;
    private transient boolean teleportRequestHere;
    private transient Location teleportLocation;
    private transient boolean vanished;
    private transient final Teleport teleport;
    private transient long teleportRequestTime;
    private transient long lastOnlineActivity;
    private transient long lastThrottledAction;
    private transient long lastActivity = System.currentTimeMillis();
    private boolean hidden = false;
    private boolean rightClickJump = false;
    private transient Location afkPosition = null;
    private boolean invSee = false;
    private boolean recipeSee = false;
    private boolean enderSee = false;
    private transient long teleportInvulnerabilityTimestamp = 0;
    private boolean ignoreMsg = false;
    private String afkMessage;
    private long afkSince;
    private Map<User, BigDecimal> confirmingPayments = new WeakHashMap<>();
    private long lastNotifiedAboutMailsMs;
    
    private bssentials.api.User bss;

    public User(final Player base, final IEssentials ess) {
        super(base, ess);
        this.bss = new bssentials.api.User(base);
        teleport = new Teleport(this, ess);
        if (isAfk())
            afkPosition = base.getLocation();

        if (this.getBase().isOnline())
            lastOnlineActivity = System.currentTimeMillis();

        this.messageRecipient = new SimpleMessageRecipient(ess, this);
    }

    User update(final Player base) {
        setBase(base);
        return this;
    }

    @Override
    public boolean isAuthorized(final IEssentialsCommand cmd) {
        return isAuthorized(cmd, "essentials.");
    }

    @Override
    public boolean isAuthorized(final IEssentialsCommand cmd, final String permissionPrefix) {
        return isAuthorized(permissionPrefix + (cmd.getName().equals("r") ? "msg" : cmd.getName()));
    }

    @Override
    public boolean isAuthorized(final String node) {
        final boolean result = isAuthorizedCheck(node);
        if (ess.getSettings().isDebug())
            ess.getLogger().log(Level.INFO, "checking if " + base.getName() + " has " + node + " - " + result);

        return result;
    }

    private boolean isAuthorizedCheck(final String node) {

        if (base instanceof OfflinePlayer) { return false; }

        try {
            return ess.getPermissionsHandler().hasPermission(base, node);
        } catch (Exception ex) {
            if (ess.getSettings().isDebug()) {
                ess.getLogger().log(Level.SEVERE, "Permission System Error: " + ess.getPermissionsHandler().getName()
                        + " returned: " + ex.getMessage(), ex);
            } else {
                ess.getLogger().log(Level.SEVERE, "Permission System Error: " + ess.getPermissionsHandler().getName()
                        + " returned: " + ex.getMessage());
            }

            return false;
        }
    }

    @Override
    public void healCooldown() throws Exception {
        final Calendar now = new GregorianCalendar();
        if (getLastHealTimestamp() > 0) {
            final double cooldown = ess.getSettings().getHealCooldown();
            final Calendar cooldownTime = new GregorianCalendar();
            cooldownTime.setTimeInMillis(getLastHealTimestamp());
            cooldownTime.add(Calendar.SECOND, (int) cooldown);
            cooldownTime.add(Calendar.MILLISECOND, (int) ((cooldown * 1000.0) % 1000.0));
            if (cooldownTime.after(now) && !isAuthorized("essentials.heal.cooldown.bypass")) { throw new Exception(
                    tl("timeBeforeHeal", DateUtil.formatDateDiff(cooldownTime.getTimeInMillis()))); }
        }
        setLastHealTimestamp(now.getTimeInMillis());
    }

    @Override
    public void giveMoney(final BigDecimal value) throws MaxMoneyException {
        giveMoney(value, null);
    }

    @Override
    public void giveMoney(final BigDecimal value, final CommandSource initiator) throws MaxMoneyException {
        if (value.signum() == 0) { return; }
        setMoney(getMoney().add(value));
        sendMessage(tl("addedToAccount", NumberUtil.displayCurrency(value, ess)));
        if (initiator != null) {
            initiator.sendMessage(tl("addedToOthersAccount", NumberUtil.displayCurrency(value, ess),
                    this.getDisplayName(), NumberUtil.displayCurrency(getMoney(), ess)));
        }
    }

    @Override
    public void payUser(final User reciever, final BigDecimal value) throws Exception {
        if (value.compareTo(BigDecimal.ZERO) < 1) { throw new Exception(tl("payMustBePositive")); }

        if (canAfford(value)) {
            setMoney(getMoney().subtract(value));
            reciever.setMoney(reciever.getMoney().add(value));
            sendMessage(tl("moneySentTo", NumberUtil.displayCurrency(value, ess), reciever.getDisplayName()));
            reciever.sendMessage(tl("moneyRecievedFrom", NumberUtil.displayCurrency(value, ess), getDisplayName()));
        } else {
            throw new ChargeException(tl("notEnoughMoney", NumberUtil.displayCurrency(value, ess)));
        }
    }

    @Override
    public void takeMoney(final BigDecimal value) {
        takeMoney(value, null);
    }

    @Override
    public void takeMoney(final BigDecimal value, final CommandSource initiator) {
        if (value.signum() == 0) { return; }
        try {
            setMoney(getMoney().subtract(value));
        } catch (MaxMoneyException ex) {
            ess.getLogger().log(Level.WARNING,
                    "Invalid call to takeMoney, total balance can't be more than the max-money limit.", ex);
        }
        sendMessage(tl("takenFromAccount", NumberUtil.displayCurrency(value, ess)));
        if (initiator != null) {
            initiator.sendMessage(tl("takenFromOthersAccount", NumberUtil.displayCurrency(value, ess),
                    this.getDisplayName(), NumberUtil.displayCurrency(getMoney(), ess)));
        }
    }

    @Override
    public boolean canAfford(final BigDecimal cost) {
        return canAfford(cost, true);
    }

    public boolean canAfford(final BigDecimal cost, final boolean permcheck) {
        if (cost.signum() <= 0) { return true; }
        final BigDecimal remainingBalance = getMoney().subtract(cost);
        if (!permcheck || isAuthorized(
                "essentials.eco.loan")) { return (remainingBalance.compareTo(ess.getSettings().getMinMoney()) >= 0); }
        return (remainingBalance.signum() >= 0);
    }

    public void dispose() {
        ess.runTaskAsynchronously(() -> _dispose() );
    }

    private void _dispose() {
        if (!base.isOnline()) 
            this.base = new OfflinePlayer(getConfigUUID(), ess.getServer());

        cleanup();
    }

    @Override
    public Boolean canSpawnItem(final int itemId) {
        return !ess.getSettings().itemSpawnBlacklist().contains(itemId);
    }

    @Override
    public void setLastLocation() {
        setLastLocation(this.getLocation());
    }

    @Override
    public void setLogoutLocation() {
        setLogoutLocation(this.getLocation());
    }

    @Override
    public void requestTeleport(final User player, final boolean here) {
        teleportRequestTime = System.currentTimeMillis();
        teleportRequester = player == null ? null : player.getBase().getUniqueId();
        teleportRequestHere = here;
        if (player == null) 
            teleportLocation = null;
        else
            teleportLocation = here ? player.getLocation() : this.getLocation();
    }

    @Override
    public boolean hasOutstandingTeleportRequest() {
        if (getTeleportRequest() != null) { // Player has outstanding teleport
            // request.
            long timeout = ess.getSettings().getTpaAcceptCancellation();
            if (timeout != 0) {
                if ((System.currentTimeMillis() - getTeleportRequestTime()) / 1000 <= timeout) { // Player
                    // has outstanding request
                    return true;
                } else { // outstanding request expired.
                    requestTeleport(null, false);
                    return false;
                }
            } else { // outstanding request does not expire
                return true;
            }
        }
        return false;
    }

    public UUID getTeleportRequest() {
        return teleportRequester;
    }

    public boolean isTpRequestHere() {
        return teleportRequestHere;
    }

    public Location getTpRequestLocation() {
        return teleportLocation;
    }

    public String getNick(final boolean longnick) {
        return getNick(longnick, true, true);
    }

    public String getNick(final boolean longnick, final boolean withPrefix, final boolean withSuffix) {
        return bss.nick;
    }

    public void setDisplayNick() {
        if (base.isOnline() && ess.getSettings().changeDisplayName()) {
            this.getBase().setDisplayName(getNick(true));
            if (isAfk())
                updateAfkListName();
            else if (ess.getSettings().changePlayerListName()) {

                String name = getNick(true, ess.getSettings().isAddingPrefixInPlayerlist(),
                        ess.getSettings().isAddingSuffixInPlayerlist());
                try {
                    this.getBase().setPlayerListName(name);
                } catch (IllegalArgumentException e) {
                    if (ess.getSettings().isDebug())
                        logger.info("Playerlist for " + name
                                + " was not updated. Name clashed with another online player.");
                }
            }
        }
    }

    @Override public String getDisplayName() {
        return super.getBase().getDisplayName() == null ? super.getBase().getName() : super.getBase().getDisplayName();
    }

    @Override
    public Teleport getTeleport() {
        return teleport;
    }

    public long getLastOnlineActivity() {
        return lastOnlineActivity;
    }

    public void setLastOnlineActivity(final long timestamp) {
        lastOnlineActivity = timestamp;
    }

    @Override
    public BigDecimal getMoney() {
        return bss.getMoney();
    }

    private BigDecimal _getMoney() {
        return bss.getMoney();
    }

    @Override
    public void setMoney(final BigDecimal value) throws MaxMoneyException {
        bss.setMoney(value);
    }

    public void updateMoneyCache(final BigDecimal value) {
        if (ess.getSettings().isEcoDisabled()) { return; }
        if (Methods.hasMethod() && super.getMoney() != value) {
            try {
                super.setMoney(value, false);
            } catch (MaxMoneyException ex) {
                // We don't want to throw any errors here, just updating a cache
            }
        }
    }

    @Override
    public void setAfk(final boolean set) {
        Afk.setAFK(this.base, set); // BSSENTIALS
    }

    private void updateAfkListName() {
        if (ess.getSettings().isAfkListName()) {
            if (isAfk()) {
                String afkName = ess.getSettings().getAfkListName().replace("{PLAYER}", getDisplayName())
                        .replace("{USERNAME}", getName());
                getBase().setPlayerListName(afkName);
            } else
                getBase().setPlayerListName(null);
        }
    }

    public boolean toggleAfk() {
        setAfk(!isAfk());
        return isAfk();
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    public boolean isHidden(final Player player) {
        return hidden || !player.canSee(getBase());
    }

    @Override
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
        if (hidden)
            setLastLogout(getLastOnlineActivity());
    }

    // Returns true if status expired during this check
    public boolean checkJailTimeout(final long currentTime) {
        if (getJailTimeout() > 0 && getJailTimeout() < currentTime && isJailed()) {
            final JailStatusChangeEvent event = new JailStatusChangeEvent(this, null, false);
            ess.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                setJailTimeout(0);
                setJailed(false);
                sendMessage(tl("haveBeenReleased"));
                setJail(null);
                try {
                    getTeleport().back();
                } catch (Exception ex) {
                    try {
                        getTeleport().respawn(null, TeleportCause.PLUGIN);
                    } catch (Exception ex1) {}
                }
                return true;
            }
        }
        return false;
    }

    // Returns true if status expired during this check
    public boolean checkMuteTimeout(final long currentTime) {
        if (getMuteTimeout() > 0 && getMuteTimeout() < currentTime && isMuted()) {
            final MuteStatusChangeEvent event = new MuteStatusChangeEvent(this, null, false);
            ess.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                setMuteTimeout(0);
                sendMessage(tl("canTalkAgain"));
                setMuted(false);
                return true;
            }
        }
        return false;
    }

    public void updateActivity(final boolean broadcast) {
        if (isAfk() && ess.getSettings().cancelAfkOnInteract()) {
            setAfk(false);
            if (broadcast && !isHidden()) {
                setDisplayNick();
                final String msg = tl("userIsNotAway", getDisplayName());
                if (!msg.isEmpty())
                    ess.broadcastMessage(this, msg);
            }
        }
        lastActivity = System.currentTimeMillis();
    }

    public void checkActivity() {
        // Graceful time before the first afk check call.
        if (System.currentTimeMillis() - lastActivity <= 10000) { return; }

        final long autoafkkick = ess.getSettings().getAutoAfkKick();
        if (autoafkkick > 0 && lastActivity > 0 && (lastActivity + (autoafkkick * 1000)) < System.currentTimeMillis()
                && !isAuthorized("essentials.kick.exempt") && !isAuthorized("essentials.afk.kickexempt")) {
            final String kickReason = tl("autoAfkKickReason", autoafkkick / 60.0);
            lastActivity = 0;
            this.getBase().kickPlayer(kickReason);

            for (User user : ess.getOnlineUsers()) {
                if (user.isAuthorized("essentials.kick.notify"))
                    user.sendMessage(tl("playerKicked", Console.NAME, getName(), kickReason));
            }
        }
        final long autoafk = ess.getSettings().getAutoAfk();
        if (!isAfk() && autoafk > 0 && lastActivity + autoafk * 1000 < System.currentTimeMillis()
                && isAuthorized("essentials.afk.auto")) {
            setAfk(true);
            if (!isHidden()) {
                setDisplayNick();
                final String msg = tl("userIsAway", getDisplayName());
                if (!msg.isEmpty())
                    ess.broadcastMessage(this, msg);
            }
        }
    }

    public Location getAfkPosition() {
        return afkPosition;
    }

    @Override
    public boolean isGodModeEnabled() {
        if (super.isGodModeEnabled()) {
            // This enables the no-god-in-worlds functionality where the actual
            // player god mode state is never modified in disabled worlds,
            // but this method gets called every time the player takes damage.
            // In the case that the world has god-mode disabled then this method
            // will return false and the player will take damage, even though
            // they are in god mode (isGodModeEnabledRaw()).
            if (!ess.getSettings().getNoGodWorlds().contains(this.getLocation().getWorld().getName())) return true;
        }
        if (isAfk()) {
            // Protect AFK players by representing them in a god mode state to
            // render them invulnerable to damage.
            if (ess.getSettings().getFreezeAfkPlayers()) return true;
        }
        return false;
    }

    public boolean isGodModeEnabledRaw() {
        return super.isGodModeEnabled();
    }

    @Override
    public String getGroup() {
        final String result = ess.getPermissionsHandler().getGroup(base);
        if (ess.getSettings().isDebug())
            ess.getLogger().log(Level.INFO, "looking up groupname of " + base.getName() + " - " + result);

        return result;
    }

    @Override
    public boolean inGroup(final String group) {
        final boolean result = ess.getPermissionsHandler().inGroup(base, group);
        if (ess.getSettings().isDebug())
            ess.getLogger().log(Level.INFO, "checking if " + base.getName() + " is in group " + group + " - " + result);

        return result;
    }

    @Override
    public boolean canBuild() {
        return this.getBase().isOp() || ess.getPermissionsHandler().canBuild(base, getGroup());
    }

    @Override public long getTeleportRequestTime() {
        return teleportRequestTime;
    }

    public boolean isInvSee() {
        return invSee;
    }

    public void setInvSee(final boolean set) {
        invSee = set;
    }

    public boolean isEnderSee() {
        return enderSee;
    }

    public void setEnderSee(final boolean set) {
        enderSee = set;
    }

    @Override
    public void enableInvulnerabilityAfterTeleport() {
        final long time = ess.getSettings().getTeleportInvulnerability();
        if (time > 0)
            teleportInvulnerabilityTimestamp = System.currentTimeMillis() + time;
    }

    @Override
    public void resetInvulnerabilityAfterTeleport() {
        if (teleportInvulnerabilityTimestamp != 0 && teleportInvulnerabilityTimestamp < System.currentTimeMillis())
            teleportInvulnerabilityTimestamp = 0;
    }

    @Override
    public boolean hasInvulnerabilityAfterTeleport() {
        return teleportInvulnerabilityTimestamp != 0 && teleportInvulnerabilityTimestamp >= System.currentTimeMillis();
    }

    public boolean canInteractVanished() {
        return isAuthorized("essentials.vanish.interact");
    }

    @Override
    public boolean isIgnoreMsg() {
        return ignoreMsg;
    }

    @Override
    public void setIgnoreMsg(boolean ignoreMsg) {
        this.ignoreMsg = ignoreMsg;
    }

    @Override
    public boolean isVanished() {
        return vanished;
    }

    @Override
    public void setVanished(final boolean set) {
        vanished = set;
        if (set) {
            for (User user : ess.getOnlineUsers())
                if (!user.isAuthorized("essentials.vanish.see"))
                    user.getBase().hidePlayer(getBase());

            setHidden(true);
            ess.getVanishedPlayers().add(getName());
            if (isAuthorized("essentials.vanish.effect"))
                this.getBase()
                .addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false));

        } else {
            for (Player p : ess.getOnlinePlayers())
                p.showPlayer(getBase());
            setHidden(false);
            ess.getVanishedPlayers().remove(getName());
            if (isAuthorized("essentials.vanish.effect"))
                this.getBase().removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }

    public boolean checkSignThrottle() {
        if (isSignThrottled()) { return true; }
        updateThrottle();
        return false;
    }

    public boolean isSignThrottled() {
        final long minTime = lastThrottledAction + (1000 / ess.getSettings().getSignUsePerSecond());
        return (System.currentTimeMillis() < minTime);
    }

    public void updateThrottle() {
        lastThrottledAction = System.currentTimeMillis();
    }

    public boolean isFlyClickJump() {
        return rightClickJump;
    }

    public void setRightClickJump(boolean rightClickJump) {
        this.rightClickJump = rightClickJump;
    }

    @Override
    public boolean isIgnoreExempt() {
        return this.isAuthorized("essentials.chat.ignoreexempt");
    }

    public boolean isRecipeSee() {
        return recipeSee;
    }

    public void setRecipeSee(boolean recipeSee) {
        this.recipeSee = recipeSee;
    }

    @Override
    public void sendMessage(String message) {
        if (!message.isEmpty())
            base.sendMessage(message);
    }

    @Override
    public int compareTo(final User other) {
        return FormatUtil.stripFormat(getDisplayName())
                .compareToIgnoreCase(FormatUtil.stripFormat(other.getDisplayName()));
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof User)) { return false; }
        return this.getName().equalsIgnoreCase(((User) object).getName());

    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }

    @Override
    public CommandSource getSource() {
        return new CommandSource(getBase());
    }

    @Override
    public String getName() {
        return this.getBase().getName();
    }

    @Override
    public boolean isReachable() {
        return getBase().isOnline();
    }

    @Override
    public MessageResponse sendMessage(IMessageRecipient recipient, String message) {
        return this.messageRecipient.sendMessage(recipient, message);
    }

    @Override
    public MessageResponse onReceiveMessage(IMessageRecipient sender, String message) {
        return this.messageRecipient.onReceiveMessage(sender, message);
    }

    @Override
    public IMessageRecipient getReplyRecipient() {
        return this.messageRecipient.getReplyRecipient();
    }

    @Override
    public void setReplyRecipient(IMessageRecipient recipient) {
        this.messageRecipient.setReplyRecipient(recipient);
    }

    @Override
    public String getAfkMessage() {
        return this.afkMessage;
    }

    @Override
    public void setAfkMessage(String message) {
        if (isAfk())
            this.afkMessage = message;
    }

    @Override
    public long getAfkSince() {
        return afkSince;
    }

    @Override
    public Map<User, BigDecimal> getConfirmingPayments() {
        return confirmingPayments;
    }

    /**
     * Returns the {@link ItemStack} in the main hand or off-hand. If the main
     * hand is empty then the offhand item is returned - also nullable.
     */
    public ItemStack getItemInHand() {
        if (ReflUtil.isLowerThan(ReflUtil.V1_9_R1))
            return getBase().getInventory().getItemInHand();
        else {
            PlayerInventory inventory = getBase().getInventory();
            return inventory.getItemInMainHand() != null ? inventory.getItemInMainHand() : inventory.getItemInOffHand();
        }
    }

    public void notifyOfMail() {
        List<String> mails = getMails();
        if (mails != null && !mails.isEmpty()) {
            int notifyPlayerOfMailCooldown = ess.getSettings().getNotifyPlayerOfMailCooldown() * 1000;
            if (System.currentTimeMillis() - lastNotifiedAboutMailsMs >= notifyPlayerOfMailCooldown) {
                sendMessage(tl("youHaveNewMail", mails.size()));
                lastNotifiedAboutMailsMs = System.currentTimeMillis();
            }
        }
    }

}