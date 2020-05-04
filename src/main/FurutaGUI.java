package main;

import se.lth.control.plot.PlotterPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class FurutaGUI extends JFrame {

    private JRadioButton upperRadioButton;
    private JRadioButton lowerRadioButton;
    private JRadioButton offRadioButton;
    private JButton quitButton;
    private JPanel mainPanel;
    private JPanel modePanel;
    private JFormattedTextField velocityLowerField;
    private JFormattedTextField angleLowerField;
    private JFormattedTextField velocityUpperField;
    private JFormattedTextField angleUpperField;
    private JSlider AngleSlider;
    private JFormattedTextField k2Field;
    private JFormattedTextField k1Field;
    private JButton setParameters;
    private JPanel parameterPanel;
    private JLabel parametersAndControlLabel;
    private JLabel k1Label;
    private JLabel k2Label;
    private JLabel angleThresholdUpperLabel;
    private JLabel velocityThresholdUpperLabel;
    private JLabel angleThresholdLowerLabel;
    private JLabel velocityThreshildLowerLabel;
    private JLabel angleReferenceLabel;
    private JButton resetK1;
    private JButton resetK2;
    private JButton resetATU;
    private JButton resetVTU;
    private JButton resetATL;
    private JButton resetVTL;
    private JButton ResetAR;
    private JLabel messageField;

    private RegulatorParameters parameters;
    private PlotterPanel plotterPanel;


    public static void main(String[] args) {
        FurutaGUI gui = new FurutaGUI(new RegulatorParameters());

    }

    public FurutaGUI(RegulatorParameters parameters) {

        this.parameters = parameters;

        setFieldParameters();

        plotter();

        JFrame frame = new JFrame("furutaGUI");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setVisible(true);

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        offRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (parameters) {
                    parameters.state = RegulatorParameters.STATE.OFF;
                }
            }
        });

        upperRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (parameters) {
                    parameters.state = RegulatorParameters.STATE.UPPER;
                }
            }
        });

        lowerRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (parameters) {
                    parameters.state = RegulatorParameters.STATE.LOWER;
                }
            }
        });


        resetK1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                k1Field.setText(Double.toString(parameters.K1));
            }
        });
        resetK2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                k2Field.setText(Double.toString(parameters.K2));
            }
        });

        resetATL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angleLowerField.setText(Double.toString(parameters.angleThresholdLower));
            }
        });

        resetATU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angleUpperField.setText(Double.toString(parameters.angleThresholdUpper));
            }
        });

        resetVTL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                velocityLowerField.setText(Double.toString(parameters.velocityThresholdLower));
            }
        });

        resetVTU.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                velocityUpperField.setText(Double.toString(parameters.velocityThresholdUpper));
            }
        });

        setParameters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double k1Temp = Double.parseDouble(k1Field.getText());
                    if(k1Temp>1 || k1Temp<0){
                        throw new NumberFormatException("k1 needs to be between 1 and 0");
                    }
                    double k2Temp = Double.parseDouble(k2Field.getText());
                    if(k2Temp>1 || k2Temp<0){
                        throw new NumberFormatException("k2 needs to be between 1 and 0");
                    }
                    parameters.K1 = k1Temp;
                    parameters.K2 = k2Temp;
                    parameters.angleReference = AngleSlider.getValue()/100;
                    parameters.angleThresholdLower = Double.parseDouble(angleLowerField.getText());
                    parameters.angleThresholdUpper = Double.parseDouble(angleUpperField.getText());
                    parameters.velocityThresholdLower = Double.parseDouble(velocityLowerField.getText());
                    parameters.velocityThresholdUpper = Double.parseDouble(velocityUpperField.getText());
                    messageField.setText("Parameters set successfully");
                    setFieldParameters();
                }catch (NumberFormatException error){
                    messageField.setText("Not allowed input: " + error.getMessage());
                }

            }
        });





    }

    private void setFieldParameters(){
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        df.setDecimalFormatSymbols(symbols);

        angleUpperField.setText(Double.toString(parameters.angleThresholdUpper));
        angleLowerField.setText(String.valueOf(parameters.angleThresholdLower));
        velocityLowerField.setText(String.valueOf(parameters.velocityThresholdLower));
        velocityUpperField.setText(String.valueOf(parameters.velocityThresholdUpper));
        k1Field.setText(String.valueOf(parameters.K1));
        k2Field.setText(String.valueOf(parameters.K2));
    }

    private void plotter(){
        JFrame plotter = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        plotterPanel = new PlotterPanel(2,4);
        plotterPanel.setBorder(BorderFactory.createEtchedBorder());
        plotterPanel.setYAxis(2,-1,2,2);
        plotterPanel.setXAxis(10,5,5);
        plotterPanel.setColor(1, Color.RED);
        panel.add(plotterPanel,BorderLayout.CENTER);
        plotter.setContentPane(panel);

        plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        plotter.setSize(700, 450);
        plotter.setVisible(true);

        plotterPanel.start();
    }




}
