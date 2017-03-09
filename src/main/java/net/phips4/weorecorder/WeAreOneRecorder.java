package net.phips4.weorecorder;

import net.phips4.weorecorder.frames.Alerts;
import net.phips4.weorecorder.frames.MainFrame;
import net.phips4.weorecorder.threads.RecordingThread;
import net.phips4.weorecorder.util.Debug;
import net.phips4.weorecorder.util.Util;

import javax.swing.*;
import java.io.*;

/**
 * Created by phips4
 */
public class WeAreOneRecorder {

    public static void main (String[] args) {

        Debug.DEBUG = false;

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception exception) {
            exception.printStackTrace();
        }

        /* MainFrame mainFrame = */ new MainFrame() {

            RecordingThread thread;

            @Override
            protected void startRecording() {

                if ( getStreamType() != null ) {
                    Debug.debug("Selected: " + getStreamType().toString());

                    if ( getOutputDir() == null ) {
                        thread = new RecordingThread(getStreamType(), Util.getDefaultFile(), getRecordingBytesBar());
                        Debug.debug(Util.getDefaultFile().getAbsolutePath());
                    } else {
                        thread = new RecordingThread(getStreamType(), getOutputDir(), getRecordingBytesBar());
                        Debug.debug(getOutputDir().getAbsolutePath());
                    }

                    if( useTimeLimit() ) {
                        thread.setTimeLimit(getTimeLimit());
                    }

                    thread.start();
                    setRecordingStatusLabel(true);
                } else {
                    Alerts.alertInfo("No stream selected");
                }
            }

            @Override
            protected void stopRecording() {
                if( thread != null) {
                    thread.stop();
                }

                setRecordingStatusLabel(false);
                Debug.debug("stop");
            }

            @Override
            protected void outputSelected(File output) {
                setOutputDir(output);
            }
        };
    }
}
