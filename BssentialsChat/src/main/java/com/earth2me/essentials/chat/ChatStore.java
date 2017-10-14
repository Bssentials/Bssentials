package com.earth2me.essentials.chat;

import org.bukkit.entity.Player;

//import com.earth2me.essentials.Trade;
//import com.earth2me.essentials.User;
//import net.ess3.api.IEssentials;


public class ChatStore {
    private final Player user;
    private final String type;
    private final String charge;
    private long radius;

    ChatStore(final /* IEssentials */Object ess, final Player user, final String type) {
        this.user = user;
        this.type = type;
        this.charge = getLongType();
    }

    public Player getUser() {
        return user;
    }

    public /* Trade */String getCharge() {
        return charge;
    }

    public String getType() {
        return type;
    }

    public final String getLongType() {
        return type.length() == 0 ? "chat" : "chat-" + type;
    }

    public long getRadius() {
        return radius;
    }

    public void setRadius(long radius) {
        this.radius = radius;
    }
}
