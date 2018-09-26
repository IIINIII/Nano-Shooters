import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.Serializable;

public class SpaceShip implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4338890201671150483L;
	int x,y,R,r,gunAngle,nx,ny,ia;
	int[] xPoints = {0, 14, 15, 19, 20, 25, 28, 28, 27, 16, 14, 0};
	int[] yPoints = {-16, -16, -15, -15, -16, -16, -13, -7, -6, -6, -4, -4};
	int size,health;
	boolean isShown;
	Motion motion;
	boolean shootAllowed;
	int coolingTime;
	int ID;
	
	public SpaceShip() {
		ID=(int) (Math.random()*10000);
		motion=new Motion(0,0);
		shootAllowed=true;
		coolingTime = 15;
		isShown=true;
		health=100;
		size=2;
		nx=30;
		ny=-10;
		ia=(int) Math.toDegrees(Math.asin(ny/Math.sqrt(Math.pow(nx, 2)+Math.pow(ny, 2))));
		this.R=16;
		this.r=14;
		this.gunAngle=(int) (Math.random()*360);
		this.x=(int) (Math.random()*(Game.width-this.R*2))+this.R;
		this.y=(int) (Math.random()*(Game.height-this.R*2))+this.R;
	}
	
	public int getNoseX() {
		return (int) (x+(Math.cos(Math.toRadians(ia+gunAngle))*Math.sqrt(Math.pow(nx, 2)+Math.pow(ny, 2))*size));
	}
	
	public int getNoseY() {
		return (int) (y+(Math.sin(Math.toRadians(ia+gunAngle))*Math.sqrt(Math.pow(nx, 2)+Math.pow(ny, 2))*size));
	}
	
	public void paint(Graphics2D graph, AffineTransform affineTransform) {
		if(!isShown) return;
		gunAngle%=360;
		graph.setTransform(affineTransform);
		graph.translate(x, y);
		graph.scale(size, size);
		if(ServerClient.tcp.isServer&&this==GamePanel.myShip)
			graph.setColor(Color.decode("#007acc"));
		else if(!ServerClient.tcp.isServer&&this!=GamePanel.myShip)
			graph.setColor(Color.decode("#007acc"));
		else
			graph.setColor(Color.red);
		graph.rotate(Math.toRadians(gunAngle));
		graph.fill(new Polygon(xPoints, yPoints, xPoints.length));
		graph.fillOval(-this.R, -this.R, this.R*2, this.R*2);
		graph.setColor(Color.decode("#323232"));
		graph.fillOval(-this.r, -this.r, this.r*2, this.r*2);
		paintHealth(graph, affineTransform);
	}

	public void move() {
		if(isShown==false) return;
		//motion.refresh();
		motion.setTIA(motion.getTIA()+1);
		x += motion.getVx();
		y += motion.getVy();
		if((x<R*size&&motion.getVx()<0)||(x>Game.width-R*size&&motion.getVx()>0)) motion.setVx(-motion.getVx());
		if((y<R*size&&motion.getVy()<0)||(y>Game.height-R*size&&motion.getVy()>0)) motion.setVy(-motion.getVy());
		x=Math.min(x, Game.width-R*size);
		x=Math.max(x, R*size);
		y=Math.min(y, Game.height-R*size);
		y=Math.max(y, R*size);
		TcpMessage msg = new TcpMessage(clone());
		try {
			ServerClient.tcp.os.writeObject(msg);
			ServerClient.tcp.os.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void paintHealth(Graphics2D graph, AffineTransform affineTransform) {
		int w=2;
		int q=r-w*2;
		graph.setColor(Color.decode("#fb0260"));
		graph.fillArc(-q, -q, q*2, q*2, 0, (int) (health*3.6));
		graph.setColor(Color.decode("#323232"));
		graph.fillOval(-q+w, -q+w, q*2-w*2, q*2-w*2);
	}
	
	public SpaceShip clone() {
		/*
		int x,y,R,r,gunAngle,nx,ny,ia;
		int[] xPoints = {0, 14, 15, 19, 20, 25, 28, 28, 27, 16, 14, 0};
		int[] yPoints = {-16, -16, -15, -15, -16, -16, -13, -7, -6, -6, -4, -4};
		int size,health;
		boolean isShown;
		Motion motion;
		boolean shootAllowed;
		int coolingTime;
		*/
		SpaceShip ship = new SpaceShip();
		
		ship.x = this.x;
		ship.y = this.y;
		ship.R = this.R;
		ship.r = this.r;
		ship.gunAngle = this.gunAngle;
		ship.nx = this.nx;
		ship.ny = this.ny;
		ship.ia = this.ia;
		ship.xPoints = this.xPoints;
		ship.yPoints = this.yPoints;
		ship.size = this.size;
		ship.health = this.health;
		ship.isShown = this.isShown;
		ship.motion = this.motion;
		ship.shootAllowed = this.shootAllowed;
		ship.coolingTime = this.coolingTime;
		
		return ship;
	}
	
}
