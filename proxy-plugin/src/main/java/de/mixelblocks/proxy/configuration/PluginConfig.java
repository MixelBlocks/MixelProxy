package de.mixelblocks.proxy.configuration;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class PluginConfig {

    private String version;
    private DatabaseConfig databaseConfig;
    private RedisConfig redisConfig;

    public PluginConfig(String version, DatabaseConfig databaseConfig, RedisConfig redisConfig) {
        this.version = version;
        this.databaseConfig = databaseConfig;
        this.redisConfig = redisConfig;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }

    public static class DatabaseConfig {

        private String connectionString;
        private String databaseName;

        public DatabaseConfig(String connectionString, String databaseName) {
            this.connectionString = connectionString;
            this.databaseName = databaseName;
        }

        public String getConnectionString() {
            return connectionString;
        }

        public void setConnectionString(String connectionString) {
            this.connectionString = connectionString;
        }

        public String getDatabaseName() {
            return databaseName;
        }

        public void setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
        }
    }

    public static class RedisConfig {

        private String connectionString;
        private String userName;
        private String authentication;

        public RedisConfig(String connectionString, String userName, String authentication) {
            this.connectionString = connectionString;
            this.userName = userName;
            this.authentication = authentication;
        }

        public String getConnectionString() {
            return connectionString;
        }

        public void setConnectionString(String connectionString) {
            this.connectionString = connectionString;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setAuthentication(String authentication) {
            this.authentication = authentication;
        }

        public String getAuthentication() {
            return authentication;
        }
    }

}
