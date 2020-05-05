package main;


import furuta.SimFurutaPendulum;


public class Main {

	public static void main(String[] args) {
		SimFurutaPendulum furuta = new SimFurutaPendulum(Math.PI, 0);
		RegulatorParameters parameters = new RegulatorParameters();

		parameters.state = RegulatorParameters.STATE.OFF;
		parameters.H = 0.01;
		parameters.K1 = 1;
		parameters.K2 = 0.02;
		parameters.velocityThresholdUpper = 1;
		parameters.velocityThresholdLower = 8;
		parameters.angleThresholdUpper = 0.3;
		parameters.angleThresholdLower = 0.5;

		FurutaGUI gui = new FurutaGUI(parameters);
		FurutaRegulator regul = new FurutaRegulator(furuta,parameters,gui);
		regul.setPriority(8);




		regul.start();
		
	}
}
