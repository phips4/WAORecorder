package net.phips4.weorecorder.frames;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by phips4
 */
public abstract class PathSelectorFrame  {

    private JFileChooser chooser;

    public PathSelectorFrame() {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setVisible(true);
        chooser.setApproveButtonText("Select Directory");
        chooser.setMultiSelectionEnabled(false);

        int result = chooser.showOpenDialog(null);

        if ( result == JFileChooser.APPROVE_OPTION )
            onApproveOption(chooser.getSelectedFile());

        if ( result == JFileChooser.CANCEL_OPTION )
            onCancelOption();

    }

    protected abstract void onApproveOption(File selectedFile);
    protected abstract void onCancelOption();
}
