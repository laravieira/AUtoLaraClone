package me.laravieira.autolaraclone.installer;

import com.therandomlabs.curseapi.CurseAPI;
import com.therandomlabs.curseapi.CurseException;
import com.therandomlabs.curseapi.file.CurseFile;
import com.therandomlabs.curseapi.file.CurseFileFilter;
import com.therandomlabs.curseapi.file.CurseFiles;
import me.laravieira.autolaraclone.gui.Installing;
import me.laravieira.autolaraclone.resource.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.kohsuke.github.GHAsset;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Downloader {
    private final Installing panel;
    private final String version;
    private final Loader loader;
    private String folder;

    public Downloader(Installing panel, String version, Loader loader) {
        this.panel = panel;
        this.version = version;
        this.loader = loader;
    }

    public void download(Resource resource) {
        if (resource.getChecked()) {
            panel.log("Downloading "+resource.getName());
            if(resource instanceof Loader)
                this.folder = "temp"+File.separator;
            if(resource instanceof Mod)
                this.folder = "temp"+File.separator+ "mods" +File.separator;
            if(resource instanceof Texture)
                this.folder = "temp"+File.separator+"resourcepacks"+File.separator;
            if(resource instanceof Shader)
                this.folder = "temp"+File.separator+ "shaderpacks" +File.separator;
            if(resource instanceof Setting)
                this.folder = "temp"+File.separator+"config"+File.separator;
            new File(folder).mkdirs();

            try {
                switch (resource.getProvider()) {
                    case Resource.FABRIC_PROVIDER -> downloadFromFabric(resource);
                    case Resource.FORGE_PROVIDER -> downloadFromForge(resource);
                    case Resource.CURSE_PROVIDER -> downloadFromCurse(resource);
                    case Resource.GITHUB_PROVIDER -> downloadFromGitHub(resource);
                    case Resource.FILE_PROVIDER -> downloadFromResources(resource);
                }
                panel.log(resource.getName() + " downloaded.");
            }catch (Exception e) {
                panel.log("Unable to download "+resource.getName()+": "+e.getMessage());
            }
        }else panel.log("Skipping download of "+resource.getName());
        panel.addProgress();
    }

    private void downloadFromResources(Resource resource) throws IOException {
        String path = new File(folder).getName()+File.separator+resource.getFile().getName();
        InputStream stream = getClass().getClassLoader().getResource(path).openStream();

        ReadableByteChannel rbc = Channels.newChannel(stream);
        FileOutputStream fos = new FileOutputStream(folder+new File(path).getName());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
        resource.setFile(new File(folder + new File(path).getName()));
    }

    private void downloadFromGitHub(Resource resource) throws IOException {
        GitHub gh = GitHub.connectAnonymously();
        GHRepository repo = gh.getRepositoryById(resource.getId());
        List<GHAsset> assets = repo.getLatestRelease().listAssets().toList();
        String file = assets.stream()
                .filter(e -> e.getBrowserDownloadUrl().endsWith(".jar"))
                .collect(Collectors.toList())
                .get(0)
                .getBrowserDownloadUrl();
        downloadFile(file);
        resource.setFile(new File(folder + new File(file).getName()));
    }

    private void downloadFromFabric(Resource resource) throws IOException {
        String latest = listLinks("https://maven.fabricmc.net/net/fabricmc/fabric-installer/", "/fabric-installer", "", "/").get(0);
        String installer = listLinks(latest, "", "", ".jar").get(0);
        downloadFile(installer);
        resource.setFile(new File(folder + new File(installer).getName()));
    }

    private void downloadFromForge(Resource resource) throws IOException {
        String url = "https://files.minecraftforge.net/net/minecraftforge/forge/index_"+this.version+".html";
        List<String> links = listLinks(url, "", "=https", ".jar");
        downloadFile(links.get(0));
        resource.setFile(new File(folder + new File(links.get(0)).getName()));
    }

    private void downloadFile(String file) throws IOException {
        ReadableByteChannel rbc = Channels.newChannel(new URL(file).openStream());
        FileOutputStream fos = new FileOutputStream(folder+new File(file).getName());
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    private List<String> listLinks(String url, String contains, String notContains, String endWith) throws IOException {
        List<String> links = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        for (Element element : doc.select("a"))
            links.add(element.absUrl("href"));
        links = links.stream()
                .filter(e
                        -> (contains.isEmpty()    ||  e.contains(contains))
                        && (notContains.isEmpty() || !e.contains(notContains))
                        && (endWith.isEmpty()     ||  e.endsWith(endWith))
                )
                .sorted()
                .collect(Collectors.toList());
        Collections.reverse(links);
        return links;
    }

    private void downloadFromCurse(Resource resource) throws CurseException {
        final Optional<CurseFiles<CurseFile>> optionalFiles = CurseAPI.files((int) resource.getId());

        if (optionalFiles.isPresent()) {
            CurseFileFilter filter = new CurseFileFilter();
            filter.gameVersionStrings(this.version);

            if (resource instanceof Mod) {
                if (loader.getId() == Resource.FABRIC_LOADER)
                    filter.and(p -> p.displayName().toLowerCase().contains("fabric"));
                if (loader.getId() == Resource.FORGE_LOADER)
                    filter.and(p -> p.displayName().toLowerCase().contains("forge"));
            }

            final CurseFiles<CurseFile> files = optionalFiles.get();
            files.filter(filter);

            files.first().downloadToDirectory(Path.of(folder));
            resource.setFile(new File(folder+files.first().nameOnDisk()));
        }
    }
}
