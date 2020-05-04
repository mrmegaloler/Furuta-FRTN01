package main;


import furuta.SimFurutaPendulum;


public class Main {

	public static void main(String[] args) {
		SimFurutaPendulum furuta = new SimFurutaPendulum(Math.PI, 0);
		RegulatorParameters parameters = new RegulatorParameters();
		parameters.state = RegulatorParameters.STATE.OFF;
		parameters.H = 0.01;
		FurutaRegulator regul = new FurutaRegulator(furuta,parameters);
		regul.setPriority(8);
		FurutaGUI gui = new FurutaGUI(parameters);



		regul.start();
		
	}
}
