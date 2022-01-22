package de.mixelblocks.proxy;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MixelProxyAPI {

    private static MixelProxy apiInstance;

    public static MixelProxy get() {
        return apiInstance;
    }

    static void register(MixelProxy apiImplementation) {
        apiInstance = apiImplementation;
    }

}
