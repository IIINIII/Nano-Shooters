import javax.swing.JFrame;

import com.alee.laf.WebLookAndFeel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4648172894076113183L;
	public static Main thisFrame;
	JTextField txtIP;

	public static void main(String[] args) {
		//ServerClient.tcp = new ServerClient(1234);
		//ServerClient.tcp = new ServerClient("localhost",1234);
		WebLookAndFeel.install();
		new Main();
	}
	
	public Main() {
		thisFrame = this;
		//getContentPane().setBackground(Color.decode("#323232"));
		setSize(400,386);
		setResizable(false);
		getContentPane().setLayout(null);
		
		JLabel lblNanoshooters = new JLabel("NanoShooters");
		lblNanoshooters.setFont(new Font("Montserrat Light", Font.PLAIN, 30));
		lblNanoshooters.setHorizontalAlignment(SwingConstants.CENTER);
		lblNanoshooters.setBounds(10, 11, 374, 71);
		lblNanoshooters.setForeground(new Color(0,122,204));
		getContentPane().add(lblNanoshooters);
		
		JButton btnHost = new JButton("Host");
		btnHost.setFont(new Font("Montserrat Light", Font.PLAIN, 20));
		btnHost.setBounds(77, 93, 240, 41);
		btnHost.addActionListener(Listeners.btnListener);
		getContentPane().add(btnHost);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setFont(new Font("Montserrat Light", Font.PLAIN, 20));
		btnConnect.setBounds(77, 194, 240, 41);
		btnConnect.addActionListener(Listeners.btnListener);
		getContentPane().add(btnConnect);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Montserrat Light", Font.PLAIN, 20));
		btnExit.setBounds(77, 246, 240, 41);
		btnExit.addActionListener(Listeners.btnListener);
		getContentPane().add(btnExit);
		
		txtIP = new JTextField();
		txtIP.setBounds(77, 142, 240, 41);
		getContentPane().add(txtIP);
		txtIP.setColumns(10);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
