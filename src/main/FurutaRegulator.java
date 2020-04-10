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

		try {
			while (!Thread.interrupted()) {
				long t = System.currentTimeMillis();
				t = t + h;

				if (state == STATE.UPPER) {

					if (normalizeToPiUpper(furuta.getThetaAngle()) < 0.15 && normalizeToPiUpper(furuta.getThetaAngle()) > -0.15) {
						//Stabiliseringsalgoritm övre
						//Vi använder styrlagen att u = K*(Xref-X)
						u = (0 - (normalizeToPiUpper(furuta.getThetaAngle()))) * -18.67 + (furuta.getThetaDot()) * 3.4 +
								(Math.PI - (normalizeToPiUpper(furuta.getPhiAngle()))) * -0.8 + furuta.getPhiDot() * 0.74;
					} else {
						//Swing-up algoritm
						//Taget från webbsidan
						u = 1 * Math.signum(((Math.cos(furuta.getThetaAngle()) + (Math.pow(furuta.getThetaDot(), 2) / (2 * Math.pow(6.7, 2)))) - 1)
								* furuta.getThetaDot() * Math.cos(furuta.getThetaAngle())) - 0.02 * furuta.getPhiDot();
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

				if (duration > 0) {
					sleep(duration);
				}

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
