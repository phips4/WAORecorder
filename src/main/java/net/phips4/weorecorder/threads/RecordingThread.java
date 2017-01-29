package net.phips4.weorecorder.threads;

import net.phips4.weorecorder.util.Debug;
import net.phips4.weorecorder.StreamType;
import net.phips4.weorecorder.util.Util;
import net.phips4.weorecorder.frames.Alerts;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by phips4
 */
public class RecordingThread implements WorkerThread {

    private StreamType stream;
    private AtomicBoolean running;
    private JProgressBar progressBar;
    private long startTime;

    private long minutes = -1;

    public RecordingThread(StreamType stream, File outputDir, JProgressBar progressBar) {
        this.stream = stream;
        this.progressBar = progressBar;
        running = new AtomicBoolean(false);

        progressBar.setMaximum(4096);
        progressBar.setMinimum(0);
        progressBar.setValue(0);
    }

    public void setTimeLimit(int minutes) {
        this.minutes = minutes*60000;
    }

    public void kill() {
        running.set(false);
    }

    public void stop() {
        running.set(false);
    }

    public void start() {
        startTime = System.currentTimeMillis();
        running.set(true);
        new Thread(new RecordingRunnable()).start();
    }

    /*
     * Runnable class
     */
    public class RecordingRunnable implements Runnable {
        private int counter = 0;

        public void run() {

            try {
                URLConnection conn = new URL(stream.getStreamUrl()).openConnection();
                InputStream inputStream = conn.getInputStream();
                OutputStream outputStream = new FileOutputStream(Util.getDefaultFile());

                byte[] buffer = new byte[4096];
                int len;

                //record with no timed limit.
                if( minutes == -1 ) {
                    while ((len = inputStream.read(buffer)) > 0 && running.get() ) {
                        outputStream.write(buffer, 0, len);

                        final int x = len;
                        handleCounter();

                        if( counter == 1 ) {
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    progressBar.setValue(x);
                                }
                            });
                        }
                        Debug.debug("stream: " + stream.getStreamUrl() + " time: " + minutes + " status: " + running + " bytes: " + len);
                    }
                    Alerts.alertInfo("Recording has stopped!");
                }
                else
                {
                    //record with a timed limit
                    while (((len = inputStream.read(buffer)) > 0) && ((System.currentTimeMillis() - startTime) < minutes) && running.get()) {
                        outputStream.write(buffer, 0, len);

                        final int x = len;
                        handleCounter();

                        if( counter == 1) {
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    progressBar.setValue(x);
                                }
                            });
                        }
                        Debug.debug("time: " + minutes + " status: " + running + " reaming " + (System.currentTimeMillis() - startTime) + " bytes: " + len);
                    }
                    Alerts.alertInfo("Time expired!");
                }
                outputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
                Alerts.alertError("An error while recording your stream has occurred!");
            }
        }

        private void handleCounter() {
            if ( counter >= 3) {
                counter = 0;
            } else {
                counter++;
            }
        }
    }
}
