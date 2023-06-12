package Code;

import java.awt.EventQueue;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.Cursor;

public class ResidentLogin extends JFrame{

	private JFrame frame;
	private JTextField username;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResidentLogin window = new ResidentLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ResidentLogin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel popup = new JLabel("");
		popup.setHorizontalAlignment(SwingConstants.CENTER);
		popup.setBounds(638, 430, 230, 15);
		frame.getContentPane().add(popup);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginPanel.setOpaque(false);
		loginPanel.setBounds(700, 456, 100, 40);
		frame.getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		JLabel loginBtn = new JLabel("");
		loginBtn.setBounds(10, 0, 80, 40);
		loginPanel.add(loginBtn);
		loginBtn.setIcon(new ImageIcon(ResidentLogin.class.getResource("/IMAGES/loginbtn.png")));
		
		JLabel loginHover = new JLabel("");
		loginPanel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				Account res1 = new Account ("resident1", "res1", "Juan Tamad", "0922222222");
				if(username.getText().equals(res1.getUsername()) && password.getText().equals(res1.getPassword())) {
					frame.setVisible(false);
					ResidentChecker a = new ResidentChecker(res1.getName(),res1.getNumber());
					a.setVisible(true);
					dispose();
				}
				else {
					popup.setText("Credentials don't match");
				}
				
			}
			public void mouseEntered(MouseEvent e) {
				loginBtn.setVisible(false);
				loginHover.setVisible(true);
			}
			public void mouseExited(MouseEvent e) {
				loginBtn.setVisible(true);
				loginHover.setVisible(false);
			}
			public void mousePressed(MouseEvent e) {
				loginBtn.setVisible(true);
				loginHover.setVisible(false);
			}
			public void mouseReleased(MouseEvent e) {
				loginBtn.setVisible(false);
				loginHover.setVisible(true);
			}	
		});
		loginHover.setBounds(0, 0, 100, 40);
		loginPanel.add(loginHover);
		loginHover.setIcon(new ImageIcon(ResidentLogin.class.getResource("/IMAGES/loginBig.png")));
		loginHover.setVisible(false);
		
		username = new JTextField();
		username.addFocusListener(new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent e) {
			if (username.getText().equals("Username")) {
				username.setText("");
			}
			else {
				username.selectAll();
			}
		}
		@Override
		public void focusLost(FocusEvent e) {
			if (username.getText().equals("")){
				username.setText("Username");
				}
			}
		});
		username.setText("Username");
		username.setBounds(638, 363, 230, 20);
		frame.getContentPane().add(username);
		username.setColumns(10);
		
		password = new JPasswordField();
		password.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (password.getText().equals("Password")) {
					password.setEchoChar('*');
					password.setText("");
				}
				else {
					password.selectAll();
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (password.getText().equals("")){
					password.setText("Password");
					password.setEchoChar((char)0);
				}
			}
		});
		password.setText("Password");
		password.setEchoChar((char)0);
		password.setBounds(638, 400, 230, 20);
		frame.getContentPane().add(password);
		
		JLabel resiImg = new JLabel("");
		resiImg.setIcon(new ImageIcon(ResidentLogin.class.getResource("/IMAGES/RESIDENTLOGIN.png")));
		resiImg.setBounds(0, 0, 984, 561);
		frame.getContentPane().add(resiImg);
	}
}