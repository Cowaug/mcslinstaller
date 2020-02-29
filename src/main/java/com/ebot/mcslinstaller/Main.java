package com.ebot.mcslinstaller;

import java.util.Arrays;

/**
 * Start point
 */
public class Main {
    public static final String path = System.getenv("APPDATA") + "\\.minecraft";
    public static final String javaPath = Arrays.stream(System.getenv("path").split(";")).filter(p->p.contains("Java")).toArray()[0].toString();
    public static final String desktopPath = System.getProperty("user.home") + "\\Desktop";
    public static void main(String[] strings) {
        new installer().StartForm();
    }
}
