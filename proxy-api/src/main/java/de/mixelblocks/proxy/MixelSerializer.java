package de.mixelblocks.proxy;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class MixelSerializer {

    private MixelSerializer() {} // prevent instanciation
    public static final LegacyComponentSerializer sectionRGB = LegacyComponentSerializer.builder().character('§').hexCharacter('#').hexColors().build();
    public static final LegacyComponentSerializer unusualSectionRGB = LegacyComponentSerializer.builder().character('§').hexCharacter('#').hexColors().useUnusualXRepeatedCharacterHexFormat().build();
    public static final LegacyComponentSerializer ampersandRGB = LegacyComponentSerializer.builder().character('&').hexCharacter('#').hexColors().build();
    public static final LegacyComponentSerializer section = LegacyComponentSerializer.builder().character('§').build();

}
