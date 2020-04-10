package main;

import furuta.SimFurutaPendulum;

public class FurutaRegulator extends Thread {

	private PID pid;
	private SimFurutaPendulum furuta;
	private double controlSignal = 0.8;

	public FurutaRegulator(SimFurutaPendulum furuta) {
		this.furuta = furuta;
		pid = new PID("Regulator for furuta");
	}

	public void run() {

		long h = pid.getHMillis();
		long duration;


		try {
			while (!Thread.interrupted()) {
				long t = System.currentTimeMillis();
				t = t + h;

				//System.out.println(furuta.getThetaAngle());
				double u = 0;
				if(normalizeToPi(furuta.getThetaAngle())< 0.05 && normalizeToPi(furuta.getThetaAngle()) >-0.05) {
					u = (normalizeToPi(furuta.getThetaAngle())) * 13.6884 + (furuta.getThetaDot()) * 2.4937+
							(normalizeToPi(furuta.getPhiAngle())) * 0.5757 + furuta.getPhiDot() * 0.5382;
					System.out.println("Stabilizing");
					System.out.println(normalizeToPi(furuta.getThetaAngle()));
				} else{
					u = 0.4 * Math.signum(((Math.cos(furuta.getThetaAngle()) + (Math.pow(furuta.getThetaDot(),2)/(2*Math.pow(6.7,2)) ))-1)
							*furuta.getThetaDot()*Math.cos(furuta.getThetaAngle())) - 0.05*furuta.getPhiDot();
				}
				/*
				else if (furuta.getThetaAngle()< 0.8+Math.PI && furuta.getThetaAngle() >-0.8+Math.PI) {
					u = (furuta.getThetaAngle() % (2 * Math.PI)) * 1.9230 + (furuta.getThetaDot()) * -0.0304+
							(furuta.getPhiAngle() % (2 * Math.PI)) * 0.4615 + furuta.getPhiDot() * 0.2813;
				}
				*/

				//System.out.println(u);

				//swing up algorithm
				/*
				u = 1 * Math.signum(((Math.cos(furuta.getThetaAngle()) + (Math.pow(furuta.getThetaDot(),2)/(2*Math.pow(6.7,2)) ))-1)
						*furuta.getThetaDot()*Math.cos(furuta.getThetaAngle())) - 0*furuta.getPhiAngle();

				*/
				if (u > 1) {
					u = 1;
				} else if (u < -1) {
					u = -1;
				}
				furuta.setControlSignal(u);
				duration = t - System.currentTimeMillis();

				if (duration > 0) {
					sleep(duration);
				}

			}
		} catch (Exception ignored) {

		}
	}

	public double normalizeToPi(double angle){
		if (angle > Math.PI){
			angle = (angle % (2 * Math.PI));
			if(angle > Math.PI){
				angle -= 2*Math.PI;
			}
		}

		return angle;
	}
}
