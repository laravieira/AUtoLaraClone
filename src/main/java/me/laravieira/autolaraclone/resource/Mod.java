package me.laravieira.autolaraclone.resource;

public class Mod extends Resource {
    private int[] loaders;

    public Mod(int id, String name) {
        super(id, name);
    }

    public Mod(String name) {
        super(name);
    }

    public int[] loaders() {
        return loaders;
    }

    public void setLoaders(int[] loaders) {
        this.loaders = loaders;
    }

    public boolean isFabric() {
        boolean is = false;
        for(int loader : loaders)
            if(loader == Resource.FABRIC_LOADER)
                return true;
        return false;
    }

    public boolean isForge() {
        boolean is = false;
        for(int loader : loaders)
            if(loader == Resource.FORGE_LOADER)
                return true;
        return false;
    }

}
