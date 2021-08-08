package me.laravieira.autolaraclone.resource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class Resource {
    public static final int FORGE_LOADER     = 1;
    public static final int FABRIC_LOADER    = 2;
    public static final int CURSE_PROVIDER   = 3;
    public static final int FABRIC_PROVIDER  = 4;
    public static final int FORGE_PROVIDER   = 5;
    public static final int SILDURS_PROVIDER = 6;
    public static final int GITHUB_PROVIDER  = 7;
    public static final int FILE_PROVIDER    = 8;

    private long   id;
    private String name;
    private String tip;
    private String page;
    private String link;
    private boolean checked = false;
    private int provider;
    private int loader;
    private File file;

    public long getId()   {return id;}
    public String getName() {return name;}

    public String getTip() {
        return tip;
    }

    public String getPage() {return page;}
    public String getLink() {return link;}

    public File getFile() {
        return file;
    }

    public int getLoader() {
        return loader;
    }

    public boolean getChecked() {
        return checked;
    }

    public int getProvider() {
        return provider;
    }

    public void setId(long id)     {this.id = id;}
    public void setName(String name) {this.name = name;}

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setPage(String page) {
        if(page.contains("curseforge.com"))
            this.provider = Resource.CURSE_PROVIDER;
        else if(page.contains("github.com"))
            this.provider = Resource.GITHUB_PROVIDER;
        this.page = page;
    }
    public void setLink(String link) {this.link = link;}

    public void setFile(File file) {
        this.file = file;
    }
    public boolean hasFile() {
        return this.file.exists();
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setProvider(int provider) {
        this.provider = provider;
    }

    public void setLoader(int loader) {
        this.loader = loader;
    }

    public Resource(int id, String name) {
        this.id = id;
        this.name = name;
        this.file = new File("");
    }
    public Resource(String name) {
        this.name = name;
        this.file = new File("");
    }

    public JCheckBox toJCheckBox() {
        JCheckBox box = new JCheckBox(this.name);
        box.setSelected(this.checked);
        box.setToolTipText(this.tip);
        box.addActionListener(e -> this.actionListener(e));
        return box;
    }

    private void actionListener(ActionEvent e) {
        JCheckBox box = (JCheckBox)e.getSource();
        this.checked = box.isSelected();
    }
}


