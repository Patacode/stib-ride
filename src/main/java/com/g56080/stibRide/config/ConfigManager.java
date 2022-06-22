package com.g56080.stibRide.config;

import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.Properties;

public class ConfigManager{

    private final Properties props;
    private final String config_url;
    private static final String CONFIG_RELPATH = "/config/config.properties";

    private ConfigManager(){
        config_url = getClass().getResource(CONFIG_RELPATH).getPath();
        props = new Properties();
    }

    private final String parseUrl(String url){
        System.out.println(url);
        String[] subs = url.split("/");
        String nws = String.join("/", 
            Arrays.stream(subs)
                .map(sub -> sub.charAt(sub.length() - 1) == '!' ? "classes" : sub)
                .toArray(sz -> new String[sz]));

        return nws.substring(nws.indexOf(':') + 1);
    }

    public void load(){
        load(false);
    }

    public void load(boolean parseFlag){
        try(FileReader fins = new FileReader(parseFlag ? parseUrl(config_url) : config_url)){
            props.load(fins);
        } catch(IOException exc){
            System.err.println("Error: " + exc.getMessage());
        }
    }

    public String propertyOf(String key){
        return props.getProperty(key);
    }

    public static ConfigManager instance(){
        return Holder.INSTANCE;
    }

    private static class Holder{
        
        public static final ConfigManager INSTANCE;

        static{
            INSTANCE = new ConfigManager();
        }
    }
}
