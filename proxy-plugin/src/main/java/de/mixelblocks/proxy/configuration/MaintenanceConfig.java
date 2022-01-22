package de.mixelblocks.proxy.configuration;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MaintenanceConfig {

    private boolean active;
    private String reason;

    public MaintenanceConfig(boolean active, String reason) {
        this.active = active;
        this.reason = reason;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
