package game.tool.util;

import java.util.Properties;


public class PropertiesUtils {
    
    private PropertiesUtils() {}

    public static String getString(Properties properties, String key) {
        return properties.getProperty(key);
    }

    public static String getString(Properties properties, String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static int getInt(Properties properties, String key) {
        return Integer.parseInt(properties.getProperty(key).trim());
    }

    public static int getInt(Properties properties, String key, int defaultValue) {
        try {
            return getInt(properties, key);
        } catch(Exception e) {
            return defaultValue;
        }
    }

    public static long getLong(Properties properties, String key) {
        return Long.parseLong(properties.getProperty(key).trim());
    }

    public static long getLong(Properties properties, String key, long defaultValue) {
        try {
            return getLong(properties, key);
        } catch(Exception e) {
            return defaultValue;
        }
    }
    
    public static boolean getBoolean(Properties properties, String key) {
        String property = properties.getProperty(key).trim();
        if("1".equals(property) || "t".equalsIgnoreCase(property) || "true".equalsIgnoreCase(property) ||
           "y".equalsIgnoreCase(property) || "yes".equalsIgnoreCase(property)) {
            return true;
        }
        return false;
    }
    
    public static boolean getBoolean(Properties properties, String key, boolean defaultValue) {
        String property = properties.getProperty(key);
        if(property == null) {
            return defaultValue;
        }
        return getBoolean(properties, key);
    }

    public static <T extends Enum<T>> T getEnum(Properties properties, String key, Class<T> clazz) {
        return Enum.valueOf(clazz, properties.getProperty(key).trim());
    }

    public static <T extends Enum<T>> T getEnum(Properties properties, String key, T defaultValue, Class<T> clazz) {
        try {
            return getEnum(properties, key, clazz);
        } catch(Exception e) {
            return defaultValue;
        }
    }
}
