package main;


import furuta.SimFurutaPendulum;
import se.lth.control.plot.PlotterPanel;

public class Main {

	public static void main(String[] args) {
		SimFurutaPendulum furuta = new SimFurutaPendulum(0, 0);
		RegulatorParameters parameters = new RegulatorParameters();
		parameters.state = FurutaRegulator.STATE.UPPER;
		parameters.H = 0.01;
		FurutaRegulator regul = new FurutaRegulator(furuta,parameters);
		//GUI gui = new GUI();



		regul.start();
		
	}
}
