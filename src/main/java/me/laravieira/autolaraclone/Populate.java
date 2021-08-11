package me.laravieira.autolaraclone;

import me.laravieira.autolaraclone.installer.Downloader;
import me.laravieira.autolaraclone.resource.*;

import java.io.File;
import java.nio.file.ProviderNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Populate {

    public static ArrayList<Version> versions = new ArrayList<>();
    public static ArrayList<Loader>  loaders  = new ArrayList<>();
    public static ArrayList<Mod>     mods     = new ArrayList<>();
    public static ArrayList<Texture> textures = new ArrayList<>();
    public static ArrayList<Shader>  shaders  = new ArrayList<>();

    public Populate() {
        this.populateVersions();
        this.populateLoaders();
        this.populateMods();
        this.populateTextures();
        this.populateShaders();
    }

    private void populateVersions() {
        List<String> versions = (List<String>)Config.config.getObject("versions");
        versions.forEach(a -> {Populate.versions.add(new Version(a));});
    }

    private void populateLoaders() {
        Map<String, Object> loaders = (Map<String, Object>)Config.config.getObject("loaders");
        loaders.forEach((k, v) -> {
            Config data = new Config(v);
            Loader loader = new Loader(k, data.getString("name"));
            loader.setPage(data.getString("page"));
            if(data.has("tip"))
                loader.setTip(data.getString("tip"));
            if(data.has("selected"))
                loader.setChecked(data.getBoolean("selected"));
            loader.setProvider(providerByName(data.getString("provider")));
            if(loader.getProvider() == Downloader.FILE_PROVIDER) {
                String path = "loaders"+File.separator+data.getString("file");
                loader.setFile(new File(getClass().getClassLoader().getResource(path).getFile()));
            }
            Populate.loaders.add(loader);
        });
    }

    private void populateMods() {
        Map<String, Object> mods = (Map<String, Object>)Config.config.getObject("mods");
        mods.forEach((k, v) -> {
            Config data = new Config(v);
            Mod mod = new Mod(data.getLong("id"), data.getString("name"));
            mod.setPage(data.getString("page"));
            List<String> loaders = data.getList("loaders");
            mod.setLoaders(Populate.loaders.stream().filter(p -> {
                for(String names : loaders)
                    if(names.equalsIgnoreCase(p.getIdentifier()))
                        return true;
                return false;
            }).collect(Collectors.toList()).toArray(new Loader[0]));
            if(data.has("tip"))
                mod.setTip(data.getString("tip"));
            if(data.has("selected"))
                mod.setChecked(data.getBoolean("selected"));
            mod.setProvider(providerByName(data.getString("provider")));
            if(mod.getProvider() == Downloader.FILE_PROVIDER) {
                String path = "mods"+File.separator+data.getString("file");
                mod.setFile(new File(getClass().getClassLoader().getResource(path).getFile()));
            }
            Populate.mods.add(mod);
        });
    }

    private void populateTextures() {
        Map<String, Object> textures = (Map<String, Object>)Config.config.getObject("textures");
        textures.forEach((k, v) -> {
            Config data = new Config(v);
            Texture texture = new Texture(data.getLong("id"), data.getString("name"));
            texture.setPage(data.getString("page"));
            if(data.has("tip"))
                texture.setTip(data.getString("tip"));
            if(data.has("selected"))
                texture.setChecked(data.getBoolean("selected"));
            texture.setProvider(providerByName(data.getString("provider")));
            if(texture.getProvider() == Downloader.FILE_PROVIDER) {
                String path = "resourcepacks"+File.separator+data.getString("file");
                texture.setFile(new File(getClass().getClassLoader().getResource(path).getFile()));
            }
            Populate.textures.add(texture);
        });
    }

    private void populateShaders() {
        Map<String, Object> shaders = (Map<String, Object>)Config.config.getObject("shaders");
        shaders.forEach((k, v) -> {
            Config data = new Config(v);
            Shader shader = new Shader(data.getLong("id"), data.getString("name"));
            shader.setPage(data.getString("page"));
            if(data.has("tip"))
                shader.setTip(data.getString("tip"));
            if(data.has("selected"))
                shader.setChecked(data.getBoolean("selected"));
            shader.setProvider(providerByName(data.getString("provider")));
            if(shader.getProvider() == Downloader.FILE_PROVIDER) {
                String path = "shaderpacks"+File.separator+data.getString("file");
                shader.setFile(new File(getClass().getClassLoader().getResource(path).getFile()));
            }
            Populate.shaders.add(shader);
        });
    }

    private int providerByName(String name) {
        switch(name) {
            case "fabric-site": return Downloader.FABRIC_PROVIDER;
            case "forge-site": return Downloader.FORGE_PROVIDER;
            case "curse-forge": return Downloader.CURSE_PROVIDER;
            case "github-release": return Downloader.GITHUB_PROVIDER;
            case "sildurs-site": return Downloader.SILDURS_PROVIDER;
            case "file": return Downloader.FILE_PROVIDER;
            default: throw new ProviderNotFoundException("This provide is not supported.");
        }
    }
}
