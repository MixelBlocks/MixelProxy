package de.mixelblocks.proxy.punishments;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public enum BanType {

    MUTE("mute", "gemuted"),
    KICK("kick", "gekickt"),
    BAN("ban", "gebannt");

    private String name;
    private String actionName;

    BanType(String name, String actionName) {
        this.name = name;
        this.actionName = actionName;
    }

    public String getName() {
        return name;
    }

    public String getActionName() {
        return actionName;
    }

}
