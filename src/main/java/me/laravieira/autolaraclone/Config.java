package me.laravieira.autolaraclone;

import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {
    public static Config config;

    public static void load() {
        config   = new Config("config.yml",   true);
    }

    private final Map<String, Object> yaml = new HashMap<>();

    Config(String path, boolean resource) {
        try {
            URL file = resource?Config.class.getClassLoader().getResource(path):new URL(path);
            Load load = new Load(LoadSettings.builder().build());
            yaml.putAll((Map<String, Object>)load.loadFromInputStream(file.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Config(Object object) {
        Load load = new Load(LoadSettings.builder().build());
        yaml.putAll((Map<String, Object>)object);
    }

    private Object parse(String path) {
        String[] keywords = path.split("\\.");
        Object object = this.yaml;
        for(String keyword : keywords) {
            if(object instanceof Map) {
                Config yaml = new Config(object);
                object = yaml.has(keyword)?yaml.getObject(keyword):object;
            }
        }
        return object;
    }

    public boolean has(String keyword) {
        return yaml.containsKey(keyword);
    }

    public Object getObject(String keyword) {
        return yaml.get(keyword);
    }
    public Config get(String keyword) {
        return new Config(parse(keyword));
    }
    public String getString(String keyword) {
        return (String)parse(keyword);
    }
    public int getInt(String keyword) {
        return (int)parse(keyword);
    }
    public long getLong(String keyword) {return Long.valueOf(""+parse(keyword));}
    public float getFloat(String keyword) {
        return (float)parse(keyword);
    }
    public boolean getBoolean(String keyword) {
        return (boolean)parse(keyword);
    }
    public List getList(String keyword) {
        return (List)parse(keyword);
    }

    public boolean isString(String keyword) {
        return parse(keyword) instanceof String;
    }
    public boolean isInt(String keyword) {
        return parse(keyword) instanceof Integer;
    }
    public boolean isLong(String keyword) {
        return parse(keyword) instanceof Long;
    }
    public boolean isFloat(String keyword) {
        return parse(keyword) instanceof Float;
    }
    public boolean isBoolean(String keyword) {
        return parse(keyword) instanceof Boolean;
    }
    public boolean isList(String keyword) {
        return parse(keyword) instanceof List;
    }
}
