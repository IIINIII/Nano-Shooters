import java.io.Serializable;

public class TcpMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7246805088465858425L;
	SpaceShip player;
	Bullet bullet;
	public TcpMessage(SpaceShip player) {
		this.player = player;
	}
	public TcpMessage(Bullet bullet) {
		this.bullet = bullet;
	}
}
