package net.phips4.weorecorder.util;

/**
 * Created by phips4
 */
public class Debug {

    public static boolean DEBUG = false;

    public static void debug(String s) {
        if ( DEBUG ) {
            System.out.println("[Debug] " + s);
        }
    }
}
