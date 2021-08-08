package me.laravieira.autolaraclone;

import me.laravieira.autolaraclone.resource.*;

import java.io.File;
import java.util.ArrayList;

public class Populate {
    public static final String ALC_SOURCE_CODE = "https://github.com/laravieira";
    public static final String ALC_AUTHOR_NAME = "L4R4V131R4";
    public static final String ALC_AUTHOR_LINK = "https://laravieira.me";
    public static final String ALC_TITLE = "Auto Lara Clone";
    public static final String ALC_ABOUT = "About";

    public static ArrayList<Loader>  loaders  = new ArrayList<>();
    public static ArrayList<Version> versions = new ArrayList<>();
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
        Version v1_17_1 = new Version("1.17.1");
        versions.add(v1_17_1);

        Version v1_17   = new Version("1.17");
        versions.add(v1_17);

        Version v1_16_5 = new Version("1.16.5");
        versions.add(v1_16_5);
    }

    private void populateLoaders() {
        Loader fabricLoader = new Loader("Fabric Loader");
        fabricLoader.setId(Resource.FABRIC_LOADER);
        fabricLoader.setPage("https://fabricmc.net");
        fabricLoader.setTip("load all the mods");
        fabricLoader.setChecked(true);
        fabricLoader.setProvider(Resource.FABRIC_PROVIDER);
        loaders.add(fabricLoader);

        Loader forgeLoader = new Loader("Forge Loader");
        forgeLoader.setId(Resource.FORGE_LOADER);
        forgeLoader.setPage("https://files.minecraftforge.net/net/minecraftforge/forge/");
        forgeLoader.setTip("load all the mods");
        forgeLoader.setChecked(true);
        forgeLoader.setProvider(Resource.FORGE_PROVIDER);
        loaders.add(forgeLoader);
    }

    private void populateMods() {
        Mod fabricAPI = new Mod(306612, "Fabric API");
        fabricAPI.setPage("https://www.curseforge.com/minecraft/mc-mods/fabric-api");
        fabricAPI.setTip("is required by some mods");
        fabricAPI.setLoaders(new int[]{Resource.FABRIC_LOADER});
        mods.add(fabricAPI);

        Mod modMenu = new Mod(308702, "Mod Menu");
        modMenu.setPage("https://www.curseforge.com/minecraft/mc-mods/modmenu");
        modMenu.setTip("add mods menu on game menu");
        modMenu.setLoaders(new int[]{Resource.FABRIC_LOADER});
        mods.add(modMenu);

        Mod sodium = new Mod(237553513, "Sodium");
        sodium.setPage("https://github.com/CaffeineMC/sodium-fabric/releases");
        sodium.setTip("FPS boost mod");
        sodium.setLoaders(new int[]{Resource.FABRIC_LOADER});
        mods.add(sodium);

        Mod iris = new Mod(455508, "Iris");
        iris.setPage("https://www.curseforge.com/minecraft/mc-mods/irisshaders");
        iris.setTip("add support to shaders");
        iris.setLoaders(new int[]{Resource.FABRIC_LOADER, Resource.FORGE_LOADER});
        mods.add(iris);

        Mod entityCulling = new Mod(448233, "Entity Culling");
        entityCulling.setPage("https://www.curseforge.com/minecraft/mc-mods/entityculling");
        entityCulling.setTip("improve GPU performance");
        entityCulling.setLoaders(new int[]{Resource.FABRIC_LOADER, Resource.FORGE_LOADER});
        mods.add(entityCulling);

        Mod fpsReducer = new Mod(280294, "FPS Reducer");
        fpsReducer.setPage("https://www.curseforge.com/minecraft/mc-mods/fps-reducer");
        fpsReducer.setTip("slowdown mine when inactive");
        fpsReducer.setLoaders(new int[]{Resource.FABRIC_LOADER, Resource.FORGE_LOADER});
        mods.add(fpsReducer);

        Mod physics = new Mod(442735, "Physics");
        physics.setPage("https://www.curseforge.com/minecraft/mc-mods/physics-mod");
        physics.setTip("add cool physics");
        physics.setLoaders(new int[]{Resource.FABRIC_LOADER, Resource.FORGE_LOADER});
        mods.add(physics);

        Mod miniHud = new Mod(244260, "Mini HUD");
        miniHud.setPage("https://www.curseforge.com/minecraft/mc-mods/minihud");
        miniHud.setTip("show TSP, FPS and more on screen");
        miniHud.setLoaders(new int[]{Resource.FABRIC_LOADER, Resource.FORGE_LOADER});
        mods.add(miniHud);

        Mod maLiLib = new Mod(303119, "MaLiLib");
        maLiLib.setPage("https://www.curseforge.com/minecraft/mc-mods/malilib");
        maLiLib.setTip("is required by MiniHUD");
        maLiLib.setLoaders(new int[]{Resource.FABRIC_LOADER, Resource.FORGE_LOADER});
        mods.add(maLiLib);

        Mod xaerosMinimap = new Mod(263420, "Xaeros Minimap");
        xaerosMinimap.setPage("https://www.curseforge.com/minecraft/mc-mods/xaeros-minimap");
        xaerosMinimap.setTip("best mimi-map");
        xaerosMinimap.setLoaders(new int[]{Resource.FABRIC_LOADER, Resource.FORGE_LOADER});
        mods.add(xaerosMinimap);

        Mod xaerosWorldmap = new Mod(317780, "Xaeros Worldmap");
        xaerosWorldmap.setPage("https://www.curseforge.com/minecraft/mc-mods/xaeros-world-map");
        xaerosWorldmap.setTip("best world-map");
        xaerosWorldmap.setLoaders(new int[]{Resource.FABRIC_LOADER, Resource.FORGE_LOADER});
        mods.add(xaerosWorldmap);

        Mod inventoryProfilesNext = new Mod(495267, "Inventory Profiles Next");
        inventoryProfilesNext.setPage("https://www.curseforge.com/minecraft/mc-mods/inventory-profiles-next");
        inventoryProfilesNext.setTip("auto sort inventories");
        inventoryProfilesNext.setLoaders(new int[]{Resource.FABRIC_LOADER, Resource.FORGE_LOADER});
        mods.add(inventoryProfilesNext);
    }

    private void populateTextures() {
        Texture defaultDarkMode = new Texture(349116, "Default Dark Mode");
        defaultDarkMode.setPage("https://www.curseforge.com/minecraft/texture-packs/default-dark-mode");
        textures.add(defaultDarkMode);
    }

    private void populateShaders() {
        Shader sildurs = new Shader("Sildurs");
        sildurs.setFile(new File("SildursShaders-Vibrant-High-1.283.zip"));
        sildurs.setPage("https://sildurs-shaders.github.io/downloads/");
        sildurs.setProvider(Resource.FILE_PROVIDER);
        shaders.add(sildurs);
    }
}
