package main;

import furuta.SimFurutaPendulum;

public class FurutaRegulator extends Thread {

	private RegulatorParameters parameters;
	private SimFurutaPendulum furuta;
	private double controlSignal = 0.8;

	private double x1hat = 0;
	private double x2hat = 0;
	private double x3hat = 0;
	private double x4hat = 0;
	private double u = 0;
	private double realTime;

	private FurutaGUI plotterPanels;



	public FurutaRegulator(SimFurutaPendulum furuta, RegulatorParameters parameters, FurutaGUI plotterPanels) {
		this.furuta = furuta;
		this.parameters = parameters;
		this.plotterPanels = plotterPanels;
	}

	public void run() {

		long h = (long) (parameters.H * 1000);
		long duration;


		double pastTheta = furuta.getThetaAngle();
		double pastPhi = furuta.getPhiAngle();

		long timeExit = 0;
		try {
			while (!Thread.interrupted()) {
				long t = System.currentTimeMillis();
				t = t + h;
				//System.out.println(furuta.getPhiAngle());
				if (parameters.state == RegulatorParameters.STATE.UPPER) {
					double thetaDot = (furuta.getThetaAngle() - pastTheta)/0.01;
					double phiDot = (furuta.getPhiAngle() - pastPhi)/0.01;
					if (normalizeToPiUpper(furuta.getThetaAngle()) < parameters.angleThresholdUpper && normalizeToPiUpper(furuta.getThetaAngle()) > -parameters.angleThresholdUpper) {
						//Stabiliseringsalgoritm övre
						//Vi använder styrlagen att u = K*(Xref-X)
						u = (0 - (normalizeToPiUpper(furuta.getThetaAngle()))) * -20.17 + (thetaDot) * 3.7153 +
								(parameters.phiReference - ((furuta.getPhiAngle()))) * -0.5988 + phiDot * 0.7628;
					} else {
						//Swing-up algoritm
						//Taget från webbsidan
						u = parameters.K1 * Math.signum(((Math.cos(furuta.getThetaAngle()) + (Math.pow(thetaDot, 2) / (2 * Math.pow(6.7, 2)))) - 1)
								* thetaDot * Math.cos(furuta.getThetaAngle())) - parameters.K2 * phiDot;
					}

				} else if (parameters.state == RegulatorParameters.STATE.LOWER) {
					//Stabiliseringsalgoritm undre
					//
					u = ((furuta.getThetaAngle())) * -3.3079 + (furuta.getThetaDot()) * 0.0788 +
							((furuta.getPhiAngle())) * -0.8561 + furuta.getPhiDot() * -0.5080;

				} else if (parameters.state == RegulatorParameters.STATE.OFF) {

					u = 0;
				}


				if (u > 1) {
					u = 1;
				} else if (u < -1) {
					u = -1;
				}
				furuta.setControlSignal(u);
				realTime = realTime + ((double)h)/1000;
				plotterPanels.addDataPoints(realTime, u, furuta.getThetaAngle(), furuta.getPhiAngle());


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
