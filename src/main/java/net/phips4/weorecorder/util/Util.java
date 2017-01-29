package net.phips4.weorecorder.util;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by phips4
 */
public class Util {

    private static DateTimeFormatter dtf;

    static {
        dtf = DateTimeFormatter.ofPattern("d-M-y H.m");
    }

    public static String currentDate() {
        return dtf.format(LocalDateTime.now());
    }

    public static File getDefaultFile() {
        new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + File.separator + "records" + File.separator).mkdir();

        File file = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath() + File.separator + "records" + File.separator + Util.currentDate() + ".mp3");
        return file;
    }
}
