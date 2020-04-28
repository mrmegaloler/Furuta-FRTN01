package main;

import javax.swing.*;

public class GUI {
	private JRadioButton upperRadioButton;
	private JRadioButton lowerRadioButton;
	private JRadioButton offRadioButton;
	private JButton setParameterButton;
	private JSlider AngleReference;
	private JButton quitButton;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField textField5;
	private JTextField textField6;
	private JPanel rootPanel;
	private JPanel parameterPanel;
	private JPanel kPanel;
	private JPanel threshHoldPanel;

	public static void main(String[] args) {
		JFrame frame = new JFrame("GUI");
		frame.setContentPane(new GUI().rootPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
