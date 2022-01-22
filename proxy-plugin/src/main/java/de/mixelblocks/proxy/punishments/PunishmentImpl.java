package de.mixelblocks.proxy.punishments;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class PunishmentImpl implements Punishment {

    private BanType type;
    private long expiry;
    private BanUnit unit;
    private String reason;

    public PunishmentImpl(BanType type, long expiry, BanUnit unit, String reason) {
        this.type = type;
        this.expiry = expiry;
        this.unit = unit;
        this.reason = reason;
    }

    public PunishmentImpl(BanType type, long expiry, BanUnit unit) {
        this.type = type;
        this.expiry = expiry;
        this.unit = unit;
        this.reason = "&6Kein Grund angegeben!";
    }

    @Override
    public BanType getType() {
        return type;
    }

    @Override
    public long getExpiry() {
        return expiry;
    }

    @Override
    public boolean expired() {
        if(unit == BanUnit.INFINITY) return false;
        if(expiry >= System.currentTimeMillis()) return false;
        return true;
    }

    @Override
    public BanUnit getUnit() {
        return unit;
    }

    @Override
    public String getReason() {
        return reason;
    }

}
