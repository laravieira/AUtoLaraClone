package me.laravieira.autolaraclone.resource;

public class Loader extends Resource{
    private final String id;
    public Loader(String id, String name) {
        super(name);
        this.id = id;
    }

    public String getIdentifier() {
        return id;
    }
}
