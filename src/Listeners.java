import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;

public class Listeners {
	public static KeyListener keyboardListener = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			//Game.thisFrame.setTitle(e.getKeyCode()+"");
			if(!GamePanel.myShip.isShown)
				return;
			if(e.getKeyCode()==37||e.getKeyCode()==65) {
				// Left
				GamePanel.myShip.gunAngle-=5;
				TcpMessage msg = new TcpMessage(GamePanel.myShip.clone());
				try {
					ServerClient.tcp.os.writeObject(msg);
					ServerClient.tcp.os.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getKeyCode()==38||e.getKeyCode()==87) {
				// Top
				GamePanel.myShip.motion.setSpeed(10, GamePanel.myShip.gunAngle);
				TcpMessage msg = new TcpMessage(GamePanel.myShip.clone());
				try {
					ServerClient.tcp.os.writeObject(msg);
					ServerClient.tcp.os.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getKeyCode()==39||e.getKeyCode()==68) {
				// Right
				GamePanel.myShip.gunAngle+=5;
				TcpMessage msg = new TcpMessage(GamePanel.myShip.clone());
				try {
					ServerClient.tcp.os.writeObject(msg);
					ServerClient.tcp.os.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getKeyCode()==40||e.getKeyCode()==83) {
				// Bottom
				GamePanel.myShip.motion.setSpeed(10, (180+GamePanel.myShip.gunAngle)%360);
				TcpMessage msg = new TcpMessage(GamePanel.myShip.clone());
				try {
					ServerClient.tcp.os.writeObject(msg);
					ServerClient.tcp.os.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(e.getKeyCode()==32) {
				// SpaceBar
				if(GamePanel.myShip.shootAllowed) {
					Bullet b=new Bullet(GamePanel.myShip.getNoseX(), GamePanel.myShip.getNoseY(),30,GamePanel.myShip.gunAngle);
					TcpMessage msg = new TcpMessage(b);
					try {
						ServerClient.tcp.os.writeObject(msg);
						ServerClient.tcp.os.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					GamePanel.bullets.add(b);
					GamePanel.myShip.shootAllowed=false;
				}
			} else if(e.getKeyCode()==10) {
				// Enter
			}
		}
	};
	public static MouseMotionListener mouseMotionListener = new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if(!GamePanel.myShip.isShown)
				return;
			try {
				int q = (int) Math.toDegrees(Math.atan2((e.getY()-GamePanel.myShip.y),(e.getX()-GamePanel.myShip.x)));
				GamePanel.myShip.gunAngle = q;
				TcpMessage msg = new TcpMessage(GamePanel.myShip.clone());
				try {
					ServerClient.tcp.os.writeObject(msg);
					ServerClient.tcp.os.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Game.thisFrame.setTitle(q+"");
				//GamePanel.bullets.add(new Bullet(GamePanel.ship.getNoseX(), GamePanel.ship.getNoseY(),30,GamePanel.ship.gunAngle));
			} catch (Exception easd) {
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(!GamePanel.myShip.isShown)
				return;
			try {
				int q = (int) Math.toDegrees(Math.atan2((e.getY()-GamePanel.myShip.y),(e.getX()-GamePanel.myShip.x)));
				GamePanel.myShip.gunAngle = q;
				TcpMessage msg = new TcpMessage(GamePanel.myShip.clone());
				try {
					ServerClient.tcp.os.writeObject(msg);
					//ServerClient.tcp.os.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//Game.thisFrame.setTitle(q+"");
				if(GamePanel.myShip.shootAllowed) {
					Bullet b=new Bullet(GamePanel.myShip.getNoseX(), GamePanel.myShip.getNoseY(),30,GamePanel.myShip.gunAngle);
					msg = new TcpMessage(b);
					try {
						ServerClient.tcp.os.writeObject(msg);
						//ServerClient.tcp.os.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					GamePanel.bullets.add(b);
					GamePanel.myShip.shootAllowed=false;
				}
			} catch (Exception easd) {
			}
		}
	};
	public static ActionListener btnListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			switch (b.getText()) {
			case "Exit":
				System.exit(0);
				break;
			case "Host":
				ServerClient.tcp = new ServerClient(1234);
				Main.thisFrame.setVisible(false);
				new Game();
				break;
			case "Connect":
				ServerClient.tcp = new ServerClient(Main.thisFrame.txtIP.getText(),1234);
				Main.thisFrame.setVisible(false);
				new Game();
				break;
			default:
				break;
			}
		}
	};
}
