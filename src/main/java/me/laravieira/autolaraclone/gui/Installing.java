package me.laravieira.autolaraclone.gui;

import me.laravieira.autolaraclone.Populate;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Installing extends JPanel {
    private final JTextArea log;
    private final JProgressBar bar;
    private final JFrame parent;

    public Installing(JFrame parent) {
        this.parent = parent;

        this.bar = new JProgressBar();
        bar.setMinimum(0);
        // Download and installation of each resource
        int maximum = 2*(Populate.loaders.size()+Populate.mods.size()+Populate.textures.size()+Populate.shaders.size());
        bar.setPreferredSize(new Dimension(450, 10));
        bar.setMaximum(maximum);
        bar.setValue(0);

        this.log = new JTextArea();
        this.log.setMinimumSize(new Dimension(450, 300));
        this.log.setEditable(false);
        this.log.setAutoscrolls(true);
        DefaultCaret caret = (DefaultCaret)this.log.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.log.append("Loading...");

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(bar);
        this.add(new JScrollPane(this.log));
    }

    public JTextArea getLog() {
        return log;
    }

    public void log(String message) {
        log.append("\r\n"+message);
    }

    public void addProgress() {
        this.bar.setValue(this.bar.getValue()+1);
    }

    public void addProgress(boolean finished) {
        this.bar.setValue(this.bar.getMaximum());
    }
}
