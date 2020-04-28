package main;


public class RegulatorParameters implements Cloneable {
	public double K;
	public double Ti;
	public double Tr;
	public double Td;
	public double N;
	public double Beta;
	public double H;
	public FurutaRegulator.STATE state;

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException x) {
			return null;
		}
	}
}
