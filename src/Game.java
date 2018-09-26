import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Game extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8524282612777961979L;
	
	public static int width=1360, height=768;
	public static Game thisFrame;
	
	public Game() {
		thisFrame = this;
		setSize(width+6, height+31);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(null);
		setTitle("NanoShooters");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		GamePanel gamePanel = new GamePanel();
		gamePanel.addMouseMotionListener(Listeners.mouseMotionListener);
		gamePanel.setBounds(0, 0, getWidth(), getHeight());
		getContentPane().add(gamePanel);
		
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		executor.scheduleAtFixedRate(new TCPRW(), 0, 10L, TimeUnit.MILLISECONDS);
		executor.scheduleAtFixedRate(new Repainter(this), 0, 20L, TimeUnit.MILLISECONDS);
		this.addKeyListener(Listeners.keyboardListener);
		
		setVisible(true);
	}
	
}

class Repainter implements Runnable {

	Game game;
	public static int counter = 0;
	
	public Repainter(Game game) {
		this.game = game;
	}
	
	@Override
	public void run() {
		counter++;
		if(counter==GamePanel.myShip.coolingTime) {
			GamePanel.myShip.shootAllowed=true;
			counter=0;
		}
		game.repaint();
	}
	
}

class GamePanel extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 237612673840942602L;
	
	public static SpaceShip myShip = new SpaceShip();
	public static SpaceShip enemy;
	public static Color bgColor = Color.decode("#323232");
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graph = (Graphics2D) g;
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform affineTransform = new AffineTransform();
		graph.setColor(Color.decode("#323232"));
		graph.fillRect(0, 0, getWidth(), getHeight());
		paintBullets(graph, affineTransform);
		myShip.move();
		myShip.paint(graph, affineTransform);
		if(enemy!=null)
			enemy.paint(graph, affineTransform);
		//ship.gunAngle+=10;
		super.paint(g);
	}
	
	private void paintBullets(Graphics2D graph, AffineTransform affineTransform) {
		for (Bullet b: bullets) {
			b.move();
			if(b.isShown&&myShip.isShown&&b.isIntersecting(myShip.x-myShip.R*myShip.size, myShip.y-myShip.R*myShip.size, myShip.x+myShip.R*myShip.size, myShip.y+myShip.R*myShip.size)) {
				myShip.health-=10;
				if(myShip.health<=0) {
					myShip.isShown=false;
				}
				TcpMessage msg = new TcpMessage(myShip);
				try {
					ServerClient.tcp.os.writeObject(msg);
					ServerClient.tcp.os.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(!myShip.isShown)
					myShip=new SpaceShip();
				b.isShown=false;
			}
			if(b.isShown&&enemy.isShown&&b.isIntersecting(enemy.x-enemy.R*enemy.size, enemy.y-enemy.R*enemy.size, enemy.x+enemy.R*enemy.size, enemy.y+enemy.R*enemy.size)) {
				b.isShown=false;
			}
			b.paint(graph, affineTransform);
		}
	}
}
