package net.phips4.waorecorder;

import net.phips4.waorecorder.util.Util;

import java.io.IOException;

/**
 * Created by phips4
 */
public class Teeest {

    public static void main(String[] args) {
        String path = "abc/abc\\123\\123/12 - d dawd.mp3";
        System.out.println(Util.getDirectoryFromFile(path));

        try {
            System.out.println(Util.getBody("https://raw.githubusercontent.com/phips4/WAOTracklog/master/tracks.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
