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



	public FurutaRegulator(SimFurutaPendulum furuta, RegulatorParameters parameters) {
		this.furuta = furuta;
		this.parameters = parameters;
	}

	public void run() {

		long h = (long) (parameters.H * 1000);
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
				if (parameters.state == RegulatorParameters.STATE.UPPER) {
					double thetaDot = (furuta.getThetaAngle() - pastTheta)/0.01;
					double phiDot = (furuta.getPhiAngle() - pastPhi)/0.01;
					if (normalizeToPiUpper(furuta.getThetaAngle()) < 0.2 && normalizeToPiUpper(furuta.getThetaAngle()) > -0.15) {
						//Stabiliseringsalgoritm övre
						//Vi använder styrlagen att u = K*(Xref-X)
						u = (0 - (normalizeToPiUpper(furuta.getThetaAngle()))) * -20.17 + (thetaDot) * 3.7153 +
								(Math.PI/2 - ((furuta.getPhiAngle()))) * -0.5988 + phiDot * 0.7628;
					} else {
						//Swing-up algoritm
						//Taget från webbsidan
						u = 1 * Math.signum(((Math.cos(furuta.getThetaAngle()) + (Math.pow(thetaDot, 2) / (2 * Math.pow(6.7, 2)))) - 1)
								* thetaDot * Math.cos(furuta.getThetaAngle())) - 0.02 * phiDot;
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
				/*
				double x1hatNew = -0.036*u+0.1376*furuta.getThetaAngle()+0.1376*furuta.getPhiAngle()+0.862*x1hat+0.01*x2hat-0.1376*x3hat;
				double x2hatNew = -0.7127*u+0.7822*furuta.getThetaAngle()+0.7822*furuta.getPhiAngle()-0.4692*x1hat+1.0016*x2hat-0.7822*x3hat;
				double x3hatNew = 0.0096*u-0.0194*furuta.getThetaAngle()-0.0194*furuta.getPhiAngle()+0.019371*x1hat+1.0174*x3hat+0.01*x4hat;
				double x4hatNew = 1.9125*u-0.0241*furuta.getThetaAngle()-0.0241*furuta.getPhiAngle()+0.0182*x1hat+0.0241*x3hat+1*x4hat;
				x1hat = x1hatNew;
				x2hat = x2hatNew;
				x3hat = x3hatNew;
				x4hat = x4hatNew;
				*/

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

	public void setParameters(RegulatorParameters parameters){
		this.parameters = parameters;
	}

}
