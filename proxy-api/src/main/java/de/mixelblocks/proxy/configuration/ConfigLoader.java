package de.mixelblocks.proxy.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class ConfigLoader {

    private static JsonParser parser = new JsonParser();
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Loads a provided config object from a given JSON file.
     * If the file does not exist it also creates the file using the given object defaults
     *
     * @param clazz The object type you wish to load, also dictates the class of the returned object
     * @param file   The file that is to be created/read from
     * @return The object loaded from file
     * @throws IOException
     */
    public static <T> T loadConfig(Class<T> clazz, File file) throws IOException {
        if (file.createNewFile()) { //File does not exist, save to file
            String json = gson.toJson(parser.parse(gson.toJson(clazz)));
            try (PrintWriter out = new PrintWriter(file)) {
                out.println(json);
            }
        } else { //File exists, load from file
            return gson.fromJson(new String(Files.readAllBytes(file.toPath())), clazz);
        }

        return null;
    }

    /**
     * Saves a config object to the specified file in JSON format
     *
     * @param config The object to be saved
     * @param file   The file to which the object is saved
     * @throws IOException
     */
    public static void saveConfig(Object config, File file) throws IOException {
        file.createNewFile();
        String json = gson.toJson(parser.parse(gson.toJson(config)));
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(json);
        }
    }

}
