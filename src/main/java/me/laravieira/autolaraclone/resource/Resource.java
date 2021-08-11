package me.laravieira.autolaraclone.resource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class Resource {
    private long   id;
    private String name;
    private String tip;
    private String page;
    private boolean checked = false;
    private int provider;
    private File file;

    public long    getId() {return id;}
    public String  getName() {return name;}
    public String  getTip() {return tip ;}
    public String  getPage() {return page;}
    public File    getFile() {return file;}
    public boolean getChecked() {return checked;}
    public int     getProvider() {return provider;}

    public void setName(String name) {this.name = name;}
    public void setTip(String tip) {this.tip = tip;}
    public void setPage(String page) {this.page = page;}
    public void setFile(File file) {this.file = file;}
    public void setChecked(boolean checked) {this.checked = checked;}
    public void setProvider(int provider) {this.provider = provider;}

    public Resource(long id, String name) {
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


