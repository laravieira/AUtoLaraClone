package me.laravieira.autolaraclone.gui;

import me.laravieira.autolaraclone.AutoLaraClone;
import me.laravieira.autolaraclone.Populate;
import me.laravieira.autolaraclone.resource.Resource;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import java.awt.*;
import java.net.URL;

public class About extends JFrame {
    public About() {
        this.setTitle(AutoLaraClone.ALC_ABOUT);
        URL favicon = getClass().getClassLoader().getResource("favicon.png");
        this.setIconImage(new ImageIcon(favicon).getImage());
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.getContentPane().add(new JScrollPane(text()));

        // Alignment window to center screen and show it
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(450, 600);
        this.setLocation((dim.width-getSize().width)/2, (dim.height-getSize().height)/2);
        this.setVisible(true);
    }

    private JTextPane text() {
        JTextPane text = new JTextPane();
        text.setContentType("text/html");
        text.setText(this.buildContent());
        text.setEditable(false);
        text.setBackground(null);
        text.setBorder(null);
        text.setMargin(new Insets(10, 10, 10, 10));
        text.setFont(text.getFont().deriveFont(14f));
        text.addHyperlinkListener(this::hyperlinkListener);
        text.setCaretPosition(0);
        return text;
    }

    private String buildContent() {
        String text = "<html>";
        text += "<h1><strong>"+AutoLaraClone.ALC_TITLE+"</strong></h1>";
        text += "<br>";
        text += "<p>This is a simple tool to help "+AutoLaraClone.ALC_NAME+" and yours friends to auto setup minecraft mods and update they.</p>";
        text += "<p>This is a simple tool to help "+AutoLaraClone.ALC_NAME+" and yours friends to auto setup minecraft mods and update they.</p>";
        text += "<p>This auto install all, if you keep they checked, resources that "+AutoLaraClone.ALC_NAME+" uses.</p>";

        text += buildList("Mods",     Populate.mods.toArray(new Resource[0]));
        text += buildList("Textures", Populate.textures.toArray(new Resource[0]));
        text += buildList("Shaders",  Populate.shaders.toArray(new Resource[0]));

        text += "<br><br>";
        text += "<p>You can check the source code <a href=\""+AutoLaraClone.ALC_SOURCE+"\" target=\"_blank\">here</a>.";
        text += "<br>Made by <a href=\""+AutoLaraClone.ALC_SITE+"\" target=\"_blank\">"+AutoLaraClone.ALC_NAME+"</a> on 2021. Fell free to help.</p>";
        text += "</html>";
        return text;
    }

    private String buildList(String title, Resource[] resources) {
        String text = "<h2>"+title+"</h2>";
        for(Resource res : resources)
            text += " - <a href=\""+res.getPage()+"\" target=\"_blank\">"+res.getName()+"</a>"
                    +(res.getTip() == null ? "" : " ("+res.getTip()+")") + "<br>";
        return text;
    }

    private void hyperlinkListener(HyperlinkEvent e) {
        if(HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) try {
            Desktop.getDesktop().browse(e.getURL().toURI());
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
