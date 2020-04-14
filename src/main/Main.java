package main;


import furuta.SimFurutaPendulum;
import se.lth.control.plot.PlotterPanel;

public class Main {

	public static void main(String[] args) {
		SimFurutaPendulum furuta = new SimFurutaPendulum(0, 0);
		FurutaRegulator regul = new FurutaRegulator(furuta);
		//PlotterPanel panel = new PlotterPanel(2, 5);
		//panel.start();


		regul.start();
		
	}
}
