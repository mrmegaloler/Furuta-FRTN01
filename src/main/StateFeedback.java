package main;

public class StateFeedback {

	StateFeedbackParameters p;

	private double thetaFeedback;
	private double thetaDotFeedback;
	private double phiFeedback;
	private double phiDotFeedback;

	public StateFeedback(StateFeedbackParameters p){
		this.p = p;
		p.l1 = 3.2691;
		p.l2 = -0.4024;
		p.l3 = 2.53;
		p.l4 = 0.5957;
	}

	public void calculateFeedback(double theta, double thetaDot, double phi, double phiDot){
		thetaFeedback = theta*p.l1;
		thetaDotFeedback = thetaDot*p.l2;
		phiFeedback = phi*p.l3;
		phiDotFeedback = phiDot*p.l3;
	}

	public double getThetaFeedback() {
		return thetaFeedback;
	}

	public double getThetaDotFeedback() {
		return thetaDotFeedback;
	}

	public double getPhiFeedback(){
		return phiFeedback;
	}

	public double getPhiDotFeedback() {
		return phiDotFeedback;
	}
}
