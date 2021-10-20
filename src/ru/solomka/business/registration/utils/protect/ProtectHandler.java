package ru.solomka.business.registration.utils.protect;

import ru.solomka.business.registration.utils.Checker;
import ru.zmmxasl.dl.CallbackDefender;

public class ProtectHandler implements CallbackDefender {

    private final String pluginName = "UltimateBusiness";
    private final String stringForHash = "75983f96e7aa94b907d7692d264f843162787fe2ca6bdd18aee73fd9662c88d8";
    private final String publicKey = "";
    private String buyHash = "";
    private boolean register = true;
    private boolean verification = true;

    @Override
    public String setServerIP() {
        return new Checker().getIp();
    }

    @Override
    public String setServerPort() {
        return "";
    }

    @Override
    public String setPluginName() {
        return this.pluginName;
    }

    @Override
    public String setStringForHash() {
        return this.stringForHash;
    }

    @Override
    public String setPublicKey() {
        return this.publicKey;
    }

    @Override
    public void callbackBuyHash(String s) {
        buyHash = s;
    }

    @Override
    public void callbackRegister(boolean b) {
        register = b;
    }

    @Override
    public void callbackVerification(boolean b) {
        verification = b;
    }
}