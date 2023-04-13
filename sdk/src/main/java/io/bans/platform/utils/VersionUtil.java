package io.bans.platform.utils;

/**
 * Utility class for version comparison.
 *
 * @author kgm (kylegrahammatzen)
 */
public class VersionUtil {
    /**
     * Compare two version strings.
     *
     * @param currentVersion the current version
     * @param newVersion the new version
     * @return true if the new version is newer than the current version
     */
    public static boolean isNewerVersion(String currentVersion, String newVersion) {
        String[] currentVersionArray = currentVersion.split("\\.");
        String[] newVersionArray = newVersion.split("\\.");

        int length = Math.max(currentVersionArray.length, newVersionArray.length);

        for (int i = 0; i < length; i++) {
            int current = i < currentVersionArray.length ? Integer.parseInt(currentVersionArray[i]) : 0;
            int next = i < newVersionArray.length ? Integer.parseInt(newVersionArray[i]) : 0;

            if (next > current) {
                return true;
            } else if (next < current) {
                return false;
            }
        }

        return false;
    }
}