package net.phips4.weorecorder.frames;

import javax.swing.*;
import java.awt.*;

/**
 * Created by phips4
 */
public final class Alerts {

    public static void alertError( String error ) {
        JOptionPane.showMessageDialog(new JButton(""), error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void alertInfo( String info ) {
        JOptionPane.showMessageDialog(new JButton(""), info, "Error", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void alertError( Component component, String error ) {
        JOptionPane.showMessageDialog(component, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void alertInfo( Component component, String info ) {
        JOptionPane.showMessageDialog(component, info, "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
