import java.io.Serializable;

public class Motion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7890164377532607072L;

	public static Motion Gravity=new Motion(0,1);
	
	private double Vx,Vy;
	int theta;
	int TIA;
	public Motion(int Vx, int Vy) {
		this.setVx(Vx);
		this.setVy(Vy);
		this.theta = findTheta();
		setTIA(0);
	}
	public Motion(double force, int theta) {
		setVx((force*Math.cos(Math.toRadians(theta))));
		setVy((force*Math.sin(Math.toRadians(theta))));
		this.theta=theta;
		setTIA(0);
	}
	public Motion() {
		setVx(0);
		setVy(0);
		this.theta = 0;
		setTIA(0);
	}
	public void addSpeed(double force, int theta) {
		setVx(getVx()+(force*Math.cos(Math.toRadians(theta))));
		setVy(getVy()+(force*Math.sin(Math.toRadians(theta))));
		this.theta = findTheta();
	}
	public void setSpeed(double force, int theta) {
		setVx((force*Math.cos(Math.toRadians(theta))));
		setVy((force*Math.sin(Math.toRadians(theta))));
		this.theta = theta;
	}
	public int findTheta() {
		return (int) Math.toDegrees(Math.atan2(getVy(),getVx()));
	}
	public double getVx() {
		return Vx;
	}
	public void setVx(double d) {
		Vx = d;
	}
	public double getVy() {
		return Vy;
	}
	public void setVy(double d) {
		Vy = d;
	}
	public int getTIA() {
		return TIA;
	}
	public void setTIA(int tIA) {
		TIA = tIA;
	}
	public void refreshD() {
		addSpeed(-5, theta);
	}
	public void refreshG() {
		Vy += Gravity.getVy();
		if(Vy>10)Vy=10;
	}
	
}
