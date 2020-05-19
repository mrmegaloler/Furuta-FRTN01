package main;

import se.lth.control.plot.PlotterPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
	private JLabel messageField;

	private RegulatorParameters parameters;
	private PlotterPanel uPlotter;
	private PlotterPanel thetaPlotter;
	private PlotterPanel phiPlotter;


	public static void main(String[] args) {
		FurutaGUI gui = new FurutaGUI(new RegulatorParameters());

	}

	public FurutaGUI(RegulatorParameters parameters) {

		this.parameters = parameters;

		setFieldParameters();

		plotter();

		JFrame frame = new JFrame("Parameters and Control");
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
					parameters.setState(RegulatorParameters.STATE.OFF);
				}
			}
		});

		upperRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (parameters) {
					parameters.setState(RegulatorParameters.STATE.UPPER);
				}
			}
		});

		lowerRadioButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (parameters) {
					parameters.setState(RegulatorParameters.STATE.LOWER);
				}
			}
		});


		resetK1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				k1Field.setText(Double.toString(parameters.getK1()));
			}
		});
		resetK2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				k2Field.setText(Double.toString(parameters.getK2()));
			}
		});

		resetATL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				angleLowerField.setText(Double.toString(parameters.getAngleThresholdLower()));
			}
		});

		resetATU.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				angleUpperField.setText(Double.toString(parameters.getAngleThresholdUpper()));
			}
		});

		resetVTL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				velocityLowerField.setText(Double.toString(parameters.getAngleThresholdLower()));
			}
		});

		resetVTU.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				velocityUpperField.setText(Double.toString(parameters.getVelocityThresholdUpper()));
			}
		});

		setParameters.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					double k1Temp = Double.parseDouble(k1Field.getText());
					if (k1Temp > 1 || k1Temp < 0) {
						throw new NumberFormatException("k1 needs to be between 1 and 0");
					}
					double k2Temp = Double.parseDouble(k2Field.getText());
					if (k2Temp > 1 || k2Temp < 0) {
						throw new NumberFormatException("k2 needs to be between 1 and 0");
					}
					parameters.setK1(k1Temp);
					parameters.setK2(k2Temp);
					synchronized (parameters) {
                        parameters.setPhiReference(((double) AngleSlider.getValue()) / 100);
                        parameters.setAngleThresholdLower(Double.parseDouble(angleLowerField.getText()));
                        parameters.setAngleThresholdLower(Double.parseDouble(angleUpperField.getText()));
                        parameters.setVelocityThresholdLower(Double.parseDouble(velocityLowerField.getText()));
                        parameters.setVelocityThresholdUpper(Double.parseDouble(velocityUpperField.getText()));
                        messageField.setText("Parameters set successfully");
                        setFieldParameters();
                    }
				} catch (NumberFormatException error) {
					messageField.setText("Not allowed input: " + error.getMessage());
				}


			}
		});


	}

	private void setFieldParameters() {
		DecimalFormat df = new DecimalFormat();
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		symbols.setGroupingSeparator(',');
		df.setDecimalFormatSymbols(symbols);

		angleUpperField.setText(Double.toString(parameters.getVelocityThresholdUpper()));
		angleLowerField.setText(String.valueOf(parameters.getAngleThresholdLower()));
		velocityLowerField.setText(String.valueOf(parameters.getVelocityThresholdLower()));
		velocityUpperField.setText(String.valueOf(parameters.getVelocityThresholdUpper()));
		k1Field.setText(String.valueOf(parameters.getK1()));
		k2Field.setText(String.valueOf(parameters.getK2()));
	}

	private void plotter() {
		JFrame plotter = new JFrame("Signal Plots");
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		uPlotter = new PlotterPanel(1, 4);
		uPlotter.setBorder(BorderFactory.createEtchedBorder());
		uPlotter.setYAxis(2, -1, 2, 2);
		uPlotter.setXAxis(10, 5, 5);
		uPlotter.setColor(1, Color.RED);
		uPlotter.setTitle("Control Signal");
		panel.add(uPlotter, BorderLayout.NORTH);

		thetaPlotter = new PlotterPanel(1, 4);
		thetaPlotter.setBorder(BorderFactory.createEtchedBorder());
		thetaPlotter.setYAxis(4 * Math.PI, -2 * Math.PI, 2, 2);
		thetaPlotter.setXAxis(10, 5, 5);
		thetaPlotter.setColor(1, Color.BLUE);
		thetaPlotter.setTitle("Theta angle");
		panel.add(thetaPlotter, BorderLayout.CENTER);

		phiPlotter = new PlotterPanel(2, 4);
		phiPlotter.setBorder(BorderFactory.createEtchedBorder());
		phiPlotter.setYAxis(4 * Math.PI, -2 * Math.PI, 2, 2);
		phiPlotter.setXAxis(10, 5, 5);
		phiPlotter.setColor(1, Color.GREEN);
		phiPlotter.setColor(2, Color.BLACK);
		phiPlotter.setTitle("Phi angle with reference");
		panel.add(phiPlotter, BorderLayout.SOUTH);

		plotter.setContentPane(panel);
		plotter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		plotter.pack();
		plotter.setVisible(true);

		uPlotter.start();
		thetaPlotter.start();
		phiPlotter.start();
	}

	public void addDataPoints(double time, double u, double theta, double phi) {
		uPlotter.putData(time, u);
		thetaPlotter.putData(time, theta);
		phiPlotter.putData(time, phi, parameters.getPhiReference());
	}


}
