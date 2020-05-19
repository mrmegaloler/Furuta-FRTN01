package main;


public class RegulatorParameters implements Cloneable {
	private double K1;
	private double K2;
	private double angleThresholdUpper;
	private double angleThresholdLower;
	private double velocityThresholdUpper;
	private double velocityThresholdLower;
	private double phiReference;
	private double H;
	private STATE state;
	public enum STATE {UPPER, LOWER, OFF}; //Lokalt definierat för att hålla koll på hur vi ska reglera


	public synchronized double getK1() {
		return K1;
	}

	public synchronized void setK1(double k1) {
		K1 = k1;
	}

	public synchronized double getK2() {
		return K2;
	}

	public synchronized void setK2(double k2) {
		K2 = k2;
	}

	public synchronized double getAngleThresholdUpper() {
		return angleThresholdUpper;
	}

	public synchronized void setAngleThresholdUpper(double angleThresholdUpper) {
		this.angleThresholdUpper = angleThresholdUpper;
	}

	public synchronized double getAngleThresholdLower() {
		return angleThresholdLower;
	}

	public synchronized void setAngleThresholdLower(double angleThresholdLower) {
		this.angleThresholdLower = angleThresholdLower;
	}

	public synchronized double getVelocityThresholdUpper() {
		return velocityThresholdUpper;
	}

	public synchronized void setVelocityThresholdUpper(double velocityThresholdUpper) {
		this.velocityThresholdUpper = velocityThresholdUpper;
	}

	public synchronized double getVelocityThresholdLower() {
		return velocityThresholdLower;
	}

	public synchronized void setVelocityThresholdLower(double velocityThresholdLower) {
		this.velocityThresholdLower = velocityThresholdLower;
	}

	public synchronized double getPhiReference() {
		return phiReference;
	}

	public synchronized void setPhiReference(double phiReference) {
		this.phiReference = phiReference;
	}

	public synchronized double getH() {
		return H;
	}

	public synchronized void setH(double h) {
		H = h;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}


	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException x) {
			return null;
		}
	}
}
