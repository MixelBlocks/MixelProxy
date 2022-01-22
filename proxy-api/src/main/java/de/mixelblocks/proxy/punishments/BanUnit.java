package de.mixelblocks.proxy.punishments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public enum BanUnit {

    /**
     * Represents 1 second of time
     */
    SECOND("Sekunde(n)", TimeUnit.SECONDS.toMillis(1L), "sec"),
    /**
     * Represents 1 minute of time
     */
    MINUTE("Minute(n)", TimeUnit.MINUTES.toMillis(1L), "min"),
    /**
     * Represents 1 hour of time
     */
    HOUER("Stunde(n)", TimeUnit.HOURS.toMillis(1L), "hour"),
    /**
     * Represents 1 day of time
     */
    DAY("Tag(e)", TimeUnit.DAYS.toMillis(1L), "day"),
    /**
     * Represents 7 days of time
     */
    WEEK("Woche(n)", TimeUnit.DAYS.toMillis(30L), "week"),
    /**
     * Represents 30 days of time
     */
    MONTH("Monat(e)", TimeUnit.DAYS.toMillis(300L), "month"),
    /**
     * Represents 12 BanUnit.MONTH of time
     */
    YEAR("Jahr(e)", BanUnit.MONTH.toMillis() * 12L, "year"),
    /**
     * Represents 10 BanUnit.YEAR of time
     */
    DECADE("Jahrzehnt(e)", BanUnit.YEAR.toMillis() * 10L, "decade"),
    /**
     * Represents an infinite amount of time
     */
    INFINITY("Unendlich", -1L, "infinity");


    private String name;
    private long toMillis;
    private String shortcut;

    /**
     * The properties of ban/mute times
     * @param name
     * @param toMillis
     * @param shortcut
     */
    BanUnit(String name, long toMillis, String shortcut) {
        this.name = name;
        this.toMillis = toMillis;
        this.shortcut = shortcut;
    }

    /**
     * Get the full name of a unit
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Convert the unit to seconds
     * @return seconds
     */
    public long toMillis() {
        return this.toMillis;
    }

    public long expirySinceCreated(long now) {
        return now + toMillis();
    }

    /**
     * returns the unit shotName
     * @return shortcut
     */
    public String shortcut() {
        return this.shortcut;
    }

    /**
     * All units string
     * @return
     */
    public static List<String> getUnitsAsString() {
        ArrayList<String> units = new ArrayList<String>();
        for (BanUnit unit : BanUnit.values()) {
            units.add(unit.shortcut().toLowerCase());
        }
        return units;
    }

    public static BanUnit getUnit(String shortcut) {
        for (BanUnit unit : BanUnit.values()) {
            if (!unit.shortcut().toLowerCase().equals(shortcut.toLowerCase())) continue;
            return unit;
        }
        return null;
    }

    public static String getRemainingTime(BanUnit unit, long current, long end) {
        if (end == -1L || unit == BanUnit.INFINITY) {
            return "§4Permanent";
        }
        long millis = end - current;
        long seconds = 0L;
        long minutes = 0L;
        long hours = 0L;
        long days = 0L;
        long weeks = 0L;
        while (millis > 1000L) {
            millis -= 1000L;
            ++seconds;
        }
        while (seconds > 60L) {
            seconds -= 60L;
            ++minutes;
        }
        while (minutes > 60L) {
            minutes -= 60L;
            ++hours;
        }
        while (hours > 24L) {
            hours -= 24L;
            ++days;
        }
        while (days > 7L) {
            days -= 7L;
            ++weeks;
        }
        return "§e" + weeks + " Woche(n) " + days + " Tag(e) " + hours + " Stunde(n) " + minutes + " Minute(n) "
                + seconds + " Sekunde(n)";
    }

}
