package de.mixelblocks.proxy.configuration;

import java.io.IOException;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public interface Config {

    /**
     * Set the default instance of the config
     * @param clazz GSON serializable class
     * @param defaultConfig
     */
    void setDefault(Class clazz, Object defaultConfig);

    /**
     * Load Configuration
     * @throws IOException
     */
    void load() throws IOException;

    /**
     * Load Configuration
     * @param overrideDefault
     * @throws IOException
     */
    void load(boolean overrideDefault) throws IOException;

    /**
     * Save configuration do tile
     * @throws IOException
     */
    void save() throws IOException;

    /**
     * Save configuration do tile
     * @param overwrite set to true if config should be overwritten
     * @throws IOException
     */
    void save(boolean overwrite) throws IOException;

    /**
     * Get the loaded config object
     * @return configuration
     */
    Object get();

}
