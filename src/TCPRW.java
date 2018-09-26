import java.io.IOException;

public class TCPRW implements Runnable {

	@Override
	public void run() {
		if(!ServerClient.isConnected) return;
		try {
			TcpMessage msg = (TcpMessage) ServerClient.tcp.is.readObject();
			if(msg.player!=null&&msg.player.ID!=GamePanel.myShip.ID) {
				GamePanel.enemy = msg.player;
				if(!msg.player.isShown) {
					GamePanel.myShip.coolingTime--;
					GamePanel.myShip.coolingTime = Math.max(GamePanel.myShip.coolingTime, 0);
				}
			}
			if(msg.bullet!=null)
				GamePanel.bullets.add(msg.bullet);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
