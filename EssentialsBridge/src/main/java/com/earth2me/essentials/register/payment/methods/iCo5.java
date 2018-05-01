package com.earth2me.essentials.register.payment.methods;

import com.earth2me.essentials.register.payment.Method;
import org.bukkit.plugin.Plugin;

public class iCo5 implements Method {

    @Override
    public Object getPlugin() {
        return null;
    }

    @Override
    public String getName() {
        return "Deprecated";
    }

    @Override
    public String getLongName() {
        return "Bssentials ~ use vault";
    }

    @Override
    public String getVersion() {
        // TODO Auto-generated method stub
        return "0.0.0";
    }

    @Override
    public int fractionalDigits() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String format(double amount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasBanks() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasBank(String bank) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasAccount(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasBankAccount(String bank, String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean createAccount(String name) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean createAccount(String name, Double balance) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public MethodAccount getAccount(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MethodBankAccount getBankAccount(String bank, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isCompatible(Plugin plugin) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setPlugin(Plugin plugin) {
        // TODO Auto-generated method stub

    }
}
