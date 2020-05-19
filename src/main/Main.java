package main;


import furuta.SimFurutaPendulum;


public class Main {

	public static void main(String[] args) {
		SimFurutaPendulum furuta = new SimFurutaPendulum(Math.PI, 0);
		RegulatorParameters parameters = new RegulatorParameters();

		parameters.setState(RegulatorParameters.STATE.OFF);
		parameters.setH(0.01);
		parameters.setK1(1);
		parameters.setK2(0.02);
		parameters.setVelocityThresholdUpper(1);
		parameters.setVelocityThresholdLower(5);
		parameters.setAngleThresholdUpper(0.3);
		parameters.setAngleThresholdLower(0.4);

		FurutaGUI gui = new FurutaGUI(parameters);
		FurutaRegulator regul = new FurutaRegulator(furuta,parameters,gui);
		regul.setPriority(8);




		regul.start();
		
	}
}
