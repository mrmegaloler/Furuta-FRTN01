package main;

import furuta.SimFurutaPendulum;

public class FurutaRegulator extends Thread {

	private PID pid;
	private SimFurutaPendulum furuta;
	private double controlSignal = 0.8;



	enum STATE {UPPER, LOWER, OFF}; //Lokalt definierat för att hålla koll på hur vi ska reglera



	private STATE state = STATE.UPPER;

	public FurutaRegulator(SimFurutaPendulum furuta) {
		this.furuta = furuta;
		pid = new PID("Regulator for furuta");
	}

	public void run() {

		long h = pid.getHMillis();
		long duration;
		double u = 0;

		double pastTheta = furuta.getThetaAngle();
		double pastPhi = furuta.getPhiAngle();
		long timeExit = 0;
		try {
			while (!Thread.interrupted()) {
				long t = System.currentTimeMillis();
				t = t + h;
				//System.out.println(furuta.getPhiAngle());
				if (state == STATE.UPPER) {
					double thetaDot = (furuta.getThetaAngle() - pastTheta)/0.01;
					double phiDot = (furuta.getPhiAngle() - pastPhi)/0.01;
					if (normalizeToPiUpper(furuta.getThetaAngle()) < 0.2 && normalizeToPiUpper(furuta.getThetaAngle()) > -0.15) {
						//Stabiliseringsalgoritm övre
						//Vi använder styrlagen att u = K*(Xref-X)
						u = (0 - (normalizeToPiUpper(furuta.getThetaAngle()))) * -20.17 + (thetaDot) * 3.7153 +
								(Math.PI - ((furuta.getPhiAngle()))) * -0.5988 + phiDot * 0.7628;
					} else {
						//Swing-up algoritm
						//Taget från webbsidan
						u = 1 * Math.signum(((Math.cos(furuta.getThetaAngle()) + (Math.pow(thetaDot, 2) / (2 * Math.pow(6.7, 2)))) - 1)
								* thetaDot * Math.cos(furuta.getThetaAngle())) - 0.02 * phiDot;
					}

				} else if (state == STATE.LOWER) {
					//Stabiliseringsalgoritm undre
					//
					u = ((furuta.getThetaAngle())) * -3.3079 + (furuta.getThetaDot()) * 0.0788 +
							((furuta.getPhiAngle())) * -0.8561 + furuta.getPhiDot() * -0.5080;
				} else if (state == STATE.OFF) {
					u = 0;
				}


				if (u > 1) {
					u = 1;
				} else if (u < -1) {
					u = -1;
				}
				furuta.setControlSignal(u);
				duration = t - System.currentTimeMillis();
				pastTheta = furuta.getThetaAngle();
				pastPhi = furuta.getPhiAngle();
				if (duration > 0) {
					sleep(duration);
				}
				timeExit = System.currentTimeMillis();
			}
		} catch (Exception ignored) {

		}
	}

	public double normalizeToPiUpper(double angle) {
		if (angle > Math.PI) {
			angle = (angle % (2 * Math.PI));
			if (angle > Math.PI) {
				angle -= 2 * Math.PI;
			}
		}

		return angle;
	}
}
