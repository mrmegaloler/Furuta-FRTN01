package main;


public class RegulatorParameters implements Cloneable {
	public double K1;
	public double K2;
	public double angleThresholdUpper;
	public double angleThresholdLower;
	public double velocityThresholdUpper;
	public double velocityThresholdLower;
	public double phiReference;
	public double H;
	public STATE state;

	public enum STATE {UPPER, LOWER, OFF}; //Lokalt definierat för att hålla koll på hur vi ska reglera

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException x) {
			return null;
		}
	}
}
