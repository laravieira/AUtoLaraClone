package me.laravieira.autolaraclone.resource;

public class Mod extends Resource {
    private Loader[] loaders;

    public Mod(long id, String name) {
        super(id, name);
    }

    public Mod(String name) {
        super(name);
    }

    public Loader[] loaders() {
        return loaders;
    }

    public Loader[] getLoaders() {
        return loaders;
    }

    public void setLoaders(Loader[] loaders) {
        this.loaders = loaders;
    }

    public boolean isFabric() {
        boolean is = false;
        for(Loader loader : loaders)
            if(loader.getIdentifier() == "fabric")
                return true;
        return false;
    }

    public boolean isForge() {
        boolean is = false;
        for(Loader loader : loaders)
            if(loader.getIdentifier() == "forge")
                return true;
        return false;
    }

}
