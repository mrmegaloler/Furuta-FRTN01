package main;

public class StateFeedbackParameters implements Cloneable {
	public double l1;
	public double l2;
	public double l3;
	public double l4;

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException x) {
			return null;
		}
	}

}
