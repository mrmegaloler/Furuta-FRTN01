package main;

import javafx.scene.control.RadioButton;
import se.lth.control.DoubleField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

   /* public FurutaGUI(){
        JFrame frame = new JFrame("FurutaGUI");
        frame.setContentPane(new FurutaGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,300);
        frame.setVisible(true);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }*/

    public static void main(String[] args) {
        //FurutaGUI gui = new FurutaGUI();

    }

    public FurutaGUI() {
        JFrame frame = new JFrame("furutaGUI");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,400);
        frame.setVisible(true);

    }

}
