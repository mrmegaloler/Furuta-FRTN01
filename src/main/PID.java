package main;

public class PID {
	// Current PID parameters
	private PIDParameters p;

	private double v;
	private double e;
	private double i;
	private double d;
	private double y;


	// Constructor
	public PID(String name) {
		p = new PIDParameters();
		p.Beta = 1;
		p.H = 0.001;
		p.integratorOn = false;
		p.K = -0.1;
		p.Ti = 0;
		p.Tr = 10;
		p.N = 5;
		p.Td = 1;
		//PIDGUI pigui = new PIDGUI(this, p, name);
		setParameters(p);
	}

	// Calculates the control signal v.
	// Called from BallAndBeamRegul.
	public synchronized double calculateOutput(double y, double yRef) {
		e = yRef - y;
		double pVal = p.K * (p.Beta * e);
		d = (p.Td / (p.Td + p.N * p.H)) * d - (p.K * p.Td * p.N / (p.Td + p.N * p.H)) * (y - this.y);
		this.y = y;
		v = pVal + i + d;
		return v;
	}

	// Updates the controller state.
	// Should use tracking-based anti-windup
	// Called from BallAndBeamRegul.
	public synchronized void updateState(double u) {
		if (p.integratorOn) {
			i = i + ((p.K * p.H) / p.Ti) * e + (p.H / p.Tr) * (u - v);
		} else {
			i = 0;
		}
	}

	// Returns the sampling interval expressed as a long.
	// Explicit type casting needed.
	public synchronized long getHMillis() {
		return (long) (p.H * 1000);
	}

	// Sets the PIDParameters.
	// Called from PIDGUI.
	// Must clone newParameters.
	public synchronized void setParameters(PIDParameters newParameters) {
		p = (PIDParameters) newParameters.clone();
		if (!p.integratorOn) {
			i = 0;
		}
	}

	public synchronized PIDParameters getParameters() {
		return p;
	}

	public void reset() {
		i = 0;
		d = 0;
		p.integratorOn = false;
	}
}

