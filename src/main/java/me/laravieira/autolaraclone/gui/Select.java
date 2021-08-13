package me.laravieira.autolaraclone.gui;

import me.laravieira.autolaraclone.Config;
import me.laravieira.autolaraclone.Populate;
import me.laravieira.autolaraclone.resource.*;
import me.laravieira.autolaraclone.Installer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Select extends JPanel {
    private final JComboBox<String> version = new JComboBox<>();
    private final JFrame parent;
    private final Map<String, Boolean> preferences;

    public Select(JFrame parent) {
        this.parent = parent;
        JLabel section;
        Font font = new JLabel().getFont().deriveFont(Font.BOLD, 16);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        for(Version v : Populate.versions)
            version.addItem(v.getName());
        version.setSize(100, 0);

        section = new JLabel("Minecraft Version");
        section.setFont(font);
        this.add(alignment(section, FlowLayout.CENTER));
        this.add(alignment(version, FlowLayout.CENTER));

        JTextField dir = new JTextField();
        dir.setText(System.getenv("APPDATA")+ File.separator+".minecraft");
        dir.setToolTipText("Set game directory");

        JPanel left = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

        // List all mods
        section = new JLabel("Mods");
        section.setFont(font);
        left.add(section);
        left.add(new JSeparator());
        for(Mod mod : Populate.mods)
            left.add(mod.toJCheckBox());
        left.add(Box.createVerticalGlue());

        JPanel right = new JPanel() {
            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

        // List all textures
        section = new JLabel("Textures");
        section.setFont(font);
        right.add(section);
        right.add(new JSeparator());
        for(Texture texture : Populate.textures)
            right.add(texture.toJCheckBox());
        right.add(new JLabel(" "));

        // List all shaders
        section = new JLabel("Shaders");
        section.setFont(font);
        right.add(section);
        right.add(new JSeparator());
        for(Shader shader : Populate.shaders)
            right.add(shader.toJCheckBox());
        right.add(new JLabel(" "));

        // List all settings
        section = new JLabel("Settings");
        section.setFont(font);
        right.add(section);
        right.add(new JSeparator());

        preferences = new HashMap<>();
        preferences.put("create-versioned-folder", Config.config.getBoolean("create-versioned-folder"));
        preferences.put("create-new", Config.config.getBoolean("profile.create-new"));

        right.add(buildPrefCheckBox("create-ver-dir", "Versioned directory",  preferences.get("create-versioned-folder")));
        right.add(buildPrefCheckBox("create-profile", "Create new profile",   preferences.get("create-new")));
        //right.add(buildPrefCheckBox("import-lara",    "Import lara settings", true));
        //right.add(buildPrefCheckBox("import-saves",   "Copy old saves",       true));
        right.add(Box.createVerticalGlue());

        JPanel topCenterLeft = new JPanel();
        topCenterLeft.setLayout(new BoxLayout(topCenterLeft, BoxLayout.Y_AXIS));
        topCenterLeft.add(left, Component.TOP_ALIGNMENT);
        topCenterLeft.add(Box.createVerticalGlue());

        JPanel topCenterRight = new JPanel();
        topCenterRight.setLayout(new BoxLayout(topCenterRight, BoxLayout.Y_AXIS));
        topCenterRight.add(right, Component.TOP_ALIGNMENT);
        topCenterRight.add(Box.createVerticalGlue());

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        center.add(topCenterLeft, Component.TOP_ALIGNMENT);
        center.add(topCenterRight, Component.TOP_ALIGNMENT);
        this.add(alignment(center, FlowLayout.CENTER));

        JButton install = new JButton("Install");
        install.addActionListener(e -> install());
        this.add(new JSeparator());
        this.add(alignment(install, FlowLayout.RIGHT));
    }

    private JPanel alignment(JComponent component, int position) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(position));
        panel.setAlignmentX(position);
        panel.add(component);
        return panel;
    }

    private JCheckBox buildPrefCheckBox(String id, String text, boolean selected) {
        preferences.put(id, selected);
        JCheckBox box = new JCheckBox(text);
        box.setSelected(selected);
        box.addActionListener(e -> preferences.put(id, ((JCheckBox)e.getSource()).isSelected()));
        return box;
    }

    private void install() {
        String version = (String)this.version.getSelectedItem();
        Installing panel = new Installing(parent);
        parent.getContentPane().removeAll();
        parent.getContentPane().add(panel);
        parent.revalidate();
        parent.setSize(new Dimension(450, 300));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        parent.setLocation((dim.width-getSize().width)/2, (dim.height-getSize().height)/2);
        new Installer(panel, version, Populate.loaders.get(0), preferences).start();
    }
}
