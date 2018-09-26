import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class ServerClient {

	public static ServerClient tcp;
	public static boolean isConnected = false;
	
	public boolean isServer;
	public ServerSocket serverSocket = null;
	public Socket socket = null;
	public ObjectOutputStream os = null;
	public ObjectInputStream is = null;
	
	public ServerClient(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
			socket = serverSocket.accept();
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
			isServer = true;
			isConnected = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isConnected = false;
		}
	}
	
	public ServerClient(String serverIP,int portNumber) {
		try {
			socket = new Socket(serverIP, portNumber);
			os = new ObjectOutputStream(socket.getOutputStream());
			is = new ObjectInputStream(socket.getInputStream());
			isServer = false;
			isConnected = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isConnected = false;
		}
	}
	
}
