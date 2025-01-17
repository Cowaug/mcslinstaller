package com.ebot.mcslinstaller;

import mslinks.ShellLink;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class installer {
    private static JFrame frame;
    private JButton installButton;
    private JPanel panel1;
    private JButton exitButton;
    private JLabel label;

    /**
     * Add Install and Cancel function to button
     */
    public installer() {
        installButton.addActionListener(e -> {
            label.setText("Downloading...");
            exitButton.setEnabled(false);
            installButton.setEnabled(false);
            new Thread(() -> {
                try {
                    // Get MCSL from github
                    new File(Main.path).mkdir();
                    Files.copy(Main.class.getResourceAsStream("/Minecraft.ico"), Paths.get(Main.path + "\\" + "Minecraft.ico"), StandardCopyOption.REPLACE_EXISTING);
                    InputStream in = new URL("https://raw.githubusercontent.com/exos288/minecraft-server-launcher/master/out/artifacts/MinecraftServerLauncher.jar").openStream();
                    Files.copy(in, Paths.get(Main.path + "\\" + "MinecraftServerLauncher.jar"), StandardCopyOption.REPLACE_EXISTING);
                    ShellLink.createLink(Main.javaPath + "\\javaw.exe").setCMDArgs("-jar MinecraftServerLauncher.jar").setWorkingDir(Main.path).setIconLocation(Main.path + "\\" + "Minecraft.ico").saveTo(Main.desktopPath + "\\" + "Minecraft Server.lnk");
                    done("Install completed.");
                } catch (IOException ex) {
                    done("Install failed: " + ex.getMessage());
                }
            }).start();
        });
        exitButton.addActionListener(e -> {
            frame.dispose();
        });
    }

    /**
     * Change UI to notify user the installation has completed
     * @param message
     */
    private void done(final String message) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> done(message));
            return;
        }
        if (message != null) {
            label.setText(message);
            int w = frame.getWidth();
            int h = frame.getHeight();
            frame.setContentPane(this.panel1);
            frame.pack();
            frame.setVisible(true);
            frame.setSize(w, h);
            exitButton.setEnabled(true);
            if (message.contains("failed")) {
                installButton.setEnabled(true);
                installButton.setText("Retry");
            }
        }
    }

    /**
     * Open form
     */
    public void StartForm() {
        frame = new JFrame("Welcome to MCSL installer");
        frame.setContentPane(new installer().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - frame.getWidth() / 2, screenSize.height / 2 - frame.getHeight() / 2);
    }
}
