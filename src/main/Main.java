package main;


import furuta.SimFurutaPendulum;

public class Main {

	public static void main(String[] args) {
		SimFurutaPendulum furuta = new SimFurutaPendulum(0, 0);
		FurutaRegulator regul = new FurutaRegulator(furuta);


		regul.start();
		
	}
}
