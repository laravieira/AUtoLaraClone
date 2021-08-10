package me.laravieira.autolaraclone;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.settings.ThemeSettings;
import com.github.weisj.darklaf.theme.DarculaTheme;
import me.laravieira.autolaraclone.gui.About;
import me.laravieira.autolaraclone.gui.Select;

import static com.github.weisj.darklaf.LafManager.getPreferredThemeStyle;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;


public class AutoLaraClone extends JFrame {
    public static final String ALC_SOURCE = "https://github.com/laravieira/autolaraclone";
    public static final String ALC_SITE   = "https://laravieira.me";
    public static final String ALC_NAME   = "L4R4V131R4";
    public static final String ALC_TITLE  = "Auto Lara Clone";
    public static final String ALC_ABOUT  = "About";

    public static void main(String[] args) {
        LafManager.install(new DarculaTheme());
        LafManager.themeForPreferredStyle(getPreferredThemeStyle());
        Config.load();
        new Populate();
        new AutoLaraClone();
    }

    public AutoLaraClone() {
        this.setTitle(ALC_TITLE);
        URL favicon = getClass().getClassLoader().getResource("favicon.png");
        this.setIconImage(new ImageIcon(favicon).getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenu menu = new JMenu("Help");

            JMenuItem source = new JMenuItem("Source");
            source.addActionListener(e -> this.sourceMenuListener());
            menu.add(source);

            JMenuItem style  = new JMenuItem("Theme");
            style.addActionListener((e) -> this.themeMenuListener());
            menu.add(style);

            JMenuItem about  = new JMenuItem("About");
            about.addActionListener(e -> this.aboutMenuListener());
            menu.add(about);

        JMenuBar bar = new JMenuBar();
        bar.add(menu);
        this.setJMenuBar(bar);

        this.add(new Select(this));

        this.pack();

        // Alignment window to center screen and show it
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dim.width-getSize().width)/2, (dim.height-getSize().height)/2);
        this.setVisible(true);
    }

    private void sourceMenuListener() {
        try {
            Desktop.getDesktop().browse(URI.create(ALC_SOURCE));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void themeMenuListener() {
        ThemeSettings.showSettingsDialog(this);
    }

    private void aboutMenuListener() {
        new About();
    }
}
