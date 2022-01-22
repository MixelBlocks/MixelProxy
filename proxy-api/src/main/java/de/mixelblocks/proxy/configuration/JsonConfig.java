package de.mixelblocks.proxy.configuration;

import java.io.File;
import java.io.IOException;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class JsonConfig<T> implements Config {

    private final File dataFile;

    private Class<T> configurationClazz;

    private T configuration;

    /**
     * JsonConfig which can hold plain Java Objects which are GSON serializable ( needs to have Full Args Constructor AND Getter and Setter for each field )
     * @param clazz GSON serializable class
     * @param dataFile the configuration file
     */
    public JsonConfig(Class<T> clazz, File dataFile) {
        this.dataFile = dataFile;
        this.configurationClazz = clazz;
    }

    @Override
    public void setDefault(Class clazz, Object defaultConfig) {
        this.configurationClazz = clazz;
        this.configuration = (T) defaultConfig;
    }

    @Override
    public void load() throws IOException {
        if(dataFile.exists()) configuration = ConfigLoader.loadConfig(configurationClazz, dataFile);
    }

    @Override
    public void load(boolean overrideDefault) throws IOException {
        if(!overrideDefault && configuration != null) return;
        load();
    }

    @Override
    public void save() throws IOException {
        ConfigLoader.saveConfig(configuration, dataFile);
    }

    @Override
    public void save(boolean overwrite) throws IOException {
        if(!overwrite && dataFile.exists()) return;
        save();
    }

    @Override
    public T get() {
        return configuration;
    }

}
