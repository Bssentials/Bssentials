package com.earth2me.essentials;

import com.earth2me.essentials.utils.NumberUtil;
import net.ess3.api.IEssentials;
import net.ess3.api.IUser;
import net.ess3.api.MaxMoneyException;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Level;
import static com.earth2me.essentials.I18n.tl;

public class Trade {
    private final transient String command;
    private final transient Trade fallbackTrade;
    private final transient BigDecimal money;
    private final transient ItemStack itemStack;
    private final transient Integer exp;
    private final transient IEssentials ess;

    public enum TradeType {
        MONEY, EXP, ITEM
    }

    public enum OverflowType {
        ABORT, DROP, RETURN
    }

    public Trade(final String command, final IEssentials ess) {
        this(command, null, null, null, null, ess);
    }

    public Trade(final String command, final Trade fallback, final IEssentials ess) {
        this(command, fallback, null, null, null, ess);
    }

    @Deprecated
    public Trade(final double money, final com.earth2me.essentials.IEssentials ess) {
        this(null, null, BigDecimal.valueOf(money), null, null, (IEssentials) ess);
    }

    public Trade(final BigDecimal money, final IEssentials ess) {
        this(null, null, money, null, null, ess);
    }

    public Trade(final ItemStack items, final IEssentials ess) {
        this(null, null, null, items, null, ess);
    }

    public Trade(final int exp, final IEssentials ess) {
        this(null, null, null, null, exp, ess);
    }

    private Trade(final String command, final Trade fallback, final BigDecimal money, final ItemStack item, final Integer exp, final IEssentials ess) {
        this.command = command;
        this.fallbackTrade = fallback;
        this.money = money;
        this.itemStack = item;
        this.exp = exp;
        this.ess = ess;
    }

    public void isAffordableFor(final IUser user) throws ChargeException {

        if (getMoney() != null && getMoney().signum() > 0 && !user.canAfford(getMoney()))
            throw new ChargeException(tl("notEnoughMoney", NumberUtil.displayCurrency(getMoney(), ess)));

        BigDecimal money;
        if (command != null && !command.isEmpty() && (money = getCommandCost(user)).signum() > 0 && !user.canAfford(money))
            throw new ChargeException(tl("notEnoughMoney", NumberUtil.displayCurrency(money, ess)));

    }

    public boolean pay(final IUser user) throws MaxMoneyException {
        return pay(user, OverflowType.ABORT) == null;
    }

    public Map<Integer, ItemStack> pay(final IUser user, final OverflowType type) throws MaxMoneyException {
        if (getMoney() != null && getMoney().signum() > 0)
            user.giveMoney(getMoney());
        return null;
    }

    public void charge(final IUser user) throws ChargeException {
        if (getMoney() != null) {
            if (ess.getSettings().isDebug()) {
                ess.getLogger().log(Level.INFO, "charging user " + user.getName() + " money " + getMoney().toPlainString());
            }
            if (!user.canAfford(getMoney()) && getMoney().signum() > 0) {
                throw new ChargeException(tl("notEnoughMoney", NumberUtil.displayCurrency(getMoney(), ess)));
            }
            user.takeMoney(getMoney());
        }

        if (command != null) {
            final BigDecimal cost = getCommandCost(user);
            if (!user.canAfford(cost) && cost.signum() > 0)
                throw new ChargeException(tl("notEnoughMoney", NumberUtil.displayCurrency(cost, ess)));
            user.takeMoney(cost);
        }
    }

    public BigDecimal getMoney() {
        return money;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Integer getExperience() {
        return exp;
    }

    public TradeType getType() {
        return TradeType.MONEY;
    }

    public BigDecimal getCommandCost(final IUser user) {
        BigDecimal cost = BigDecimal.ZERO;
        if (command != null && !command.isEmpty()) {
            cost = ess.getSettings().getCommandCost(command.charAt(0) == '/' ? command.substring(1) : command);
            if (cost.signum() == 0 && fallbackTrade != null)
                cost = fallbackTrade.getCommandCost(user);
        }
        if (cost.signum() != 0 && (user.isAuthorized("essentials.nocommandcost.all") || user.isAuthorized("essentials.nocommandcost." + command)))
            return BigDecimal.ZERO;
        return cost;
    }

    private static FileWriter fw = null;

    public static void log(String type, String subtype, String event, String sender, Trade charge, String receiver, Trade pay, Location loc, IEssentials ess) {
    }

    public static void closeLog() {
    }
}
