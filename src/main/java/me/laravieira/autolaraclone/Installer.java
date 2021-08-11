package me.laravieira.autolaraclone;

import me.laravieira.autolaraclone.gui.Installing;
import me.laravieira.autolaraclone.installer.Downloader;
import me.laravieira.autolaraclone.installer.Profile;
import me.laravieira.autolaraclone.resource.Loader;
import me.laravieira.autolaraclone.resource.Mod;
import me.laravieira.autolaraclone.resource.Shader;
import me.laravieira.autolaraclone.resource.Texture;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import org.json.JSONObject;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Installer extends Thread {
    private final String baseDir;
    private final String gameDir;
    private final String version;
    private final Installing panel;
    private final Loader loader;
    private final Map<String, Boolean> preferences;
    private final Map<String, File> oldMods = new HashMap<>();

    public Installer(Installing panel, String version, Loader loader, Map<String, Boolean> preferences) {
        this.panel = panel;
        this.version = version;
        this.loader = loader;
        this.preferences = preferences;

        this.baseDir = System.getenv("APPDATA") + File.separator + ".minecraft.shignima";
        if (Config.config.getBoolean("create-versioned-folder"))
            this.gameDir = this.baseDir + File.separator + this.version;
        else this.gameDir = this.baseDir;
        new File(this.gameDir).mkdirs();

        getOldModsIdentifies();
    }

    private void getOldModsIdentifies() {
        File folder = new File(this.gameDir+File.separator+"mods");
        if(folder.exists() && folder.isDirectory())
            Arrays.stream(folder.listFiles()).toList().forEach(f -> {
                this.oldMods.put(buildModIdentifier(f.getName()), f);
            });
    }

    private String buildModIdentifier(String name) {
        return name.replaceAll("[-1234567890\\._]|(jar)", "");
    }

    @Override
    public void run() {
        panel.log("Downloading updated resources...");

        Downloader downloader = new Downloader(this.panel, this.version, this.loader);

        // Download and install loader
        downloader.download(loader);
        runJarAndWait(loader.getFile().getPath());

        // Download and copy all other resources
        for (Mod mod : Populate.mods) {
            downloader.download(mod);
            copy(mod.getFile(), "mods");
        }for(Texture texture :Populate.textures) {
            downloader.download(texture);
            copy(texture.getFile(), "resourcepacks");
        }for (Shader shader : Populate.shaders) {
            downloader.download(shader);
            copy(shader.getFile(), "shaderpacks");
        }

        // Add new Profile
        String name = loader.getFile().getName();
        addProfile(findVersionId(name));

        panel.log("Done! You can close this.");
        panel.addProgress(true);
    }

    // Bugs to fix:
    // 1. ignore Fabric-API copy action.

    private void copy(File file, String path) {
        try {
            if(file.exists()) {
                path = gameDir+File.separator+path+File.separator;
                new File(path).mkdirs();

                this.oldMods.forEach((i, f) -> {
                    if(i.equalsIgnoreCase(buildModIdentifier(file.getName()))) {
                        try {
                            String fpath = f.getParent()+File.separator+"old"+File.separator+f.getName();
                            Files.move(f.toPath(), new File(fpath).toPath(), REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Files.move(file.toPath(), new File(path+file.getName()).toPath(), REPLACE_EXISTING);
                panel.log(file.getName()+" copied.");
            }
            panel.addProgress();
        } catch (IOException e) {
            panel.log("Unable to copy: "+e.getMessage());
            e.printStackTrace();
        }
    }

    private void addProfile(String versionId) {
        Config profile = Config.config.get("profile");
        if(!profile.getBoolean("create-new"))
            return;
        try {
            String name = profile.getString("name");
            name = profile.getBoolean("concat-verion")? name+" "+this.version:name;
            Profile lara = new Profile(name, this.gameDir, versionId);
            if(profile.has("icon"))
                lara.icon = profile.getString("icon");
            if(profile.has("java-args"))
                lara.javaArgs = profile.getString("java-args");
            String path = baseDir+File.separator+"launcher_profiles.json";
            JSONObject profiles = new JSONObject(Files.readString(new File(path).toPath()));
            if(!profile.getBoolean("keep-old"))
                profiles.getJSONObject("profiles").remove(lara.name);
            profiles.getJSONObject("profiles").put(lara.name, lara.toJSONObject());
            FileWriter writer = new FileWriter(path);
            writer.write(profiles.toString());
            writer.close();
            panel.log("Profile created.");
        } catch (IOException e) {
            panel.log("Unable to create profile: "+e.getMessage());
        }
    }

    private String findVersionId(String name) {
        List<String>  ids = Arrays.stream(new File(baseDir+File.separator+"versions").list()).toList();

        // Filter to only MC version of loaders
        // Make the first match always be the lasted loader version
        ids = ids.stream()
                .filter(e -> e.contains("-") && e.contains(this.version))
                .sorted()
                .collect(Collectors.toList());
        Collections.reverse(ids);

        for(String id : ids) {
            String[] subsId = id
                    // Remove MC version to avoid wrong matches
                    .replace(this.version+"-", "")
                    .replace("-"+this.version, "")
                    .split("-");
            for(String subId : subsId)
                if (name.toLowerCase().contains(subId.toLowerCase()))
                    return id;
        }
        return this.version;
    }

    public void runJarAndWait(String file) {
        try {
            panel.log("Starting Loader installer...");
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("java -jar "+file);
            process.getInputStream().transferTo(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    char c = (char)b;
                    panel.getLog().append(""+c);
                }
            });
            process.waitFor();
            panel.log("Loader should be installed.");
        } catch (IOException | InterruptedException e) {
            panel.log("Loader not installed: "+e.getMessage());
        }
    }
}
