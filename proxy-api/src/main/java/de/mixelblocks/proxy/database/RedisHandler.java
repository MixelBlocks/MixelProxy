package de.mixelblocks.proxy.database;

import redis.clients.jedis.Jedis;

/**
 * A simple-to-use Redis Database Handler
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class RedisHandler {

    private Jedis client;

    /**
     * @param redisUrl
     */
    public RedisHandler(String redisUrl) {
        this.client = new Jedis(redisUrl);
        connection();
    }

    /**
     * @param redisUrl
     * @param authentication
     */
    public RedisHandler(String redisUrl, String authentication) {
        this.client = new Jedis(redisUrl);
        client.auth(authentication);
        connection();
    }

    /**
     * @param redisUrl
     * @param username
     * @param authentication
     */
    public RedisHandler(String redisUrl, String username, String authentication) {
        this.client = new Jedis(redisUrl);
        client.auth(username, authentication);
        connection();
    }

    /**
     * Set a value to a key ( No Expiry )
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        connection();
        return client.set(key, value);
    }

    /**
     * Set a value to a key ( Secondy Expiry )
     * @param seconds
     * @param key
     * @param value
     * @return
     */
    public String setEx(long seconds, String key, String value) {
        connection();
        return client.setex(key, seconds, value);
    }

    /**
     * Get a value by a key
     * @param key
     * @param defaultValue
     * @return value - the value which is present for a key ( If not existing or expired returns value )
     */
    public String get(String key, String defaultValue) {
        connection();
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Get a value by a key
     * @param key
     * @return value - the value which is present for a key ( If not existing or expired returns null )
     */
    public String get(String key) {
        connection();
        return client.get(key);
    }

    public void connection() {
        if(!client.isConnected()) client.connect();
    }

}

