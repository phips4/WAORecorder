package net.phips4.weorecorder.listeners;

import net.phips4.weorecorder.StreamType;
import net.phips4.weorecorder.frames.Alerts;
import net.phips4.weorecorder.frames.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by phips4
 */
public class RadioActionListener implements ActionListener {

    private JRadioButton[] buttons;
    private JLabel streamLabel;
    private StreamType type;
    private JRadioButton jRadioButton;
    private MainFrame frame;

    public RadioActionListener(MainFrame frame, JRadioButton jRadioButton) {
        this.buttons = frame.getRadioButtons();
        this.streamLabel = frame.getStreamLabel();
        this.type = frame.getStreamType();
        this.jRadioButton = jRadioButton;
        this.frame = frame;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {

        if ( frame.isRecording() ) {
            Alerts.alertInfo("You can't change the stream while recording!");
            return;
        }

        for( JRadioButton jrb : buttons ) {
            jRadioButton.setSelected(true);
            streamLabel.setText("Selected: " + jRadioButton.getText());

            if ( !jrb.getText().equals(jRadioButton.getText()) ) {
                jrb.setSelected(false);
            }
        }

        if( streamLabel.getText().contains("Selected: ") ) {
            frame.setStreamType(StreamType.parseFrom(streamLabel.getText().replace("Selected: ", "")));
        }
    }
}
