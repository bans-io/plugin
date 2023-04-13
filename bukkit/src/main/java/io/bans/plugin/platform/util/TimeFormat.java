package io.bans.plugin.platform.util;

import java.util.EnumMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TimeFormat {
    private enum DurationUnit {
        S, M, H, D, W;
    }
    private static final EnumMap<DurationUnit, Long> DURATIONS = new EnumMap<>(DurationUnit.class);
    private static final Pattern DURATION_PATTERN = Pattern.compile("^([0-9]+[smhdw]\\s?)+$");
    private static Matcher matcher;

    static {
        DURATIONS.put(DurationUnit.S, TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS));
        DURATIONS.put(DurationUnit.M, TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
        DURATIONS.put(DurationUnit.H, TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS));
        DURATIONS.put(DurationUnit.D, TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
        DURATIONS.put(DurationUnit.W, TimeUnit.MILLISECONDS.convert(7, TimeUnit.DAYS));
    }

    public boolean isValidDuration(String durationStr) {
        if(matcher == null) matcher = DURATION_PATTERN.matcher(durationStr);
        else matcher.reset(durationStr);
        return matcher.matches();
    }

    public long getDuration(String durationStr) {
        if (!isValidDuration(durationStr)) {
            throw new IllegalArgumentException("Invalid duration format. Please use valid format example: 1h 5m, 1d, 2w");
        }

        long duration = 0;
        String[] parts = durationStr.split("\\s");

        for (String part : parts) {
            long value = Long.parseLong(part.substring(0, part.length() - 1));
            DurationUnit unit = DurationUnit.valueOf(part.substring(part.length() - 1).toUpperCase());
            duration += value * DURATIONS.get(unit);
        }

        return duration;
    }

    public String getDuration(long duration) {
        long weeks = TimeUnit.MILLISECONDS.toDays(duration) / 7;
        duration -= DURATIONS.get(DurationUnit.W) * weeks;
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        duration -= DURATIONS.get(DurationUnit.D) * days;
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        duration -= DURATIONS.get(DurationUnit.H) * hours;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        duration -= DURATIONS.get(DurationUnit.M) * minutes;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        StringBuilder sb = new StringBuilder();
        if (weeks > 0) {
            sb.append(weeks).append("w ");
        }
        if (days > 0) {
            sb.append(days).append("d ");
        }
        if (hours > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0) {
            sb.append(minutes).append("m ");
        }
        if (seconds > 0) {
            sb.append(seconds).append("s ");
        }
        return sb.toString().trim();
    }
}