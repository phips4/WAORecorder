package net.phips4.waorecorder.frames;

import lombok.Getter;
import lombok.Setter;
import net.phips4.waorecorder.StreamType;
import net.phips4.waorecorder.listeners.RadioActionListener;
import net.phips4.waorecorder.util.ColorScheme;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

/**
 * Created by phips4
 */
public abstract class MainFrame extends JFrame {

    private JRadioButton houseTimeRadioButton;
    private JRadioButton technoBaseRadioButton;
    private JRadioButton hardBaseRadioButton;
    private JRadioButton coreTimeRadioButton;
    private JRadioButton teaTimeRadioButton;
    private JRadioButton tranceBaseRadioButton;
    private JPanel rootPanel;
    private JButton exitButton;
    private JButton stopButton;
    private JButton startButton;
    private JLabel settingsLabel;
    private JLabel recordingStatusLabel;
    private JSpinner timeSpinner;
    private JCheckBox useTime;

    @Getter
    private JProgressBar recordingBytesBar;

    @Getter
    private JLabel streamLabel;

    @Getter
    private JLabel timeLimitLabel;
    @Getter
    private JCheckBox logTracksCheckBox;

    @Getter
    private JRadioButton[] radioButtons = { houseTimeRadioButton, technoBaseRadioButton, hardBaseRadioButton,
            coreTimeRadioButton, teaTimeRadioButton, tranceBaseRadioButton };

    @Getter @Setter
    private StreamType streamType;

    @Getter @Setter
    private File outputDir;

    @Getter @Setter
    private boolean recording = false;

    /*
     * abstract methods
     */
    protected abstract void startRecording();

    protected abstract void stopRecording();

    protected abstract void outputSelected(File output);

    protected abstract void exitApp();
    /*
     * class constructor
     */
    public MainFrame() {
        super("We Are One Recorder");

        initComponents();
        registerListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     * class methods
     */
    private void initComponents() {
        exitButton.setBorder(BorderFactory.createLineBorder(ColorScheme.DANGER));
        stopButton.setBorder(BorderFactory.createLineBorder(ColorScheme.WARNING));
        startButton.setBorder(BorderFactory.createLineBorder(ColorScheme.GOOD));

        useTime.setBorder(BorderFactory.createLineBorder(ColorScheme.MAIN_BLUE,1));
        useTime.setBorderPaintedFlat(true);

        timeSpinner.setForeground(ColorScheme.MAIN_BLUE);
        timeSpinner.setBackground(ColorScheme.DEFAULT);
        timeSpinner.setBorder(BorderFactory.createLineBorder(ColorScheme.MAIN_BLUE, 1));
        timeSpinner.setValue(30);

        JFormattedTextField txt = ((JSpinner.NumberEditor) timeSpinner.getEditor()).getTextField();
        ((NumberFormatter) txt.getFormatter()).setAllowsInvalid(false);

        JTextField tf = ((JSpinner.DefaultEditor) timeSpinner.getEditor()).getTextField();
        tf.setBackground(ColorScheme.DEFAULT);
        tf.setForeground(ColorScheme.MAIN_BLUE);

        recordingBytesBar.setValue(0);
        recordingBytesBar.setStringPainted(true);
        recordingBytesBar.setString("");
        recordingBytesBar.setBorderPainted(true);
        recordingBytesBar.setBorder(BorderFactory.createLineBorder(ColorScheme.DEFAULT));
        recordingBytesBar.setForeground(ColorScheme.MAIN_BLUE);
        recordingBytesBar.setBackground(ColorScheme.DEFAULT);

        //experimental
        recordingBytesBar.setIgnoreRepaint(true);

        setContentPane(rootPanel);
        pack();
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    private void registerListeners() {

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitApp();
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              startRecording();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopRecording();
            }
        });

        settingsLabel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                new PathSelectorFrame() {

                    @Override
                    public void onApproveOption(File selectedFile) {
                        outputDir = selectedFile;
                        outputSelected(selectedFile);
                    }

                    @Override
                    public void onCancelOption() {
                    }
                };
            }

            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}

            public void mouseEntered(MouseEvent e) {
                ((JLabel) e.getSource()).setForeground(ColorScheme.MAIN_BLUE);
            }

            public void mouseExited(MouseEvent e) {
                ((JLabel) e.getSource()).setForeground(Color.WHITE);
            }
        });

        useTime.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int spinnerValue = (Integer) timeSpinner.getValue();

                if( spinnerValue < 1 ) {
                    timeSpinner.setValue(30);
                    Alerts.alertInfo(useTime, "The number is to small. Use a number > 0.");
                }
            }
        });

        for ( JRadioButton jrb : radioButtons ) {
            jrb.addActionListener(new RadioActionListener(this, jrb));
        }
    }

    /*
     * not lombok valid methods
     */
    public void setRecordingStatusLabel(boolean recording) {

        if ( recording ) {
            recordingStatusLabel.setText("REC");
            recordingStatusLabel.setForeground(ColorScheme.GOOD);
        } else {
            recordingStatusLabel.setText(" ");
            recordingStatusLabel.setForeground(ColorScheme.WARNING);
        }
    }

    public int getTimeLimit() {
        return (Integer) timeSpinner.getValue();
    }

    public boolean useTimeLimit() {
        return useTime.isSelected();
    }

    public void resetTimeLabel() {
        timeLimitLabel.setText("Use a timed limit. Time in minutes.");
    }

}
