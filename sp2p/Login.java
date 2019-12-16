package sp2p;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.BoxLayout;

public class Login {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField textField_1;
	Client client ;
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("LOGIN");
		frame.setBounds(100, 100, 400, 286);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 400, 254);
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setColumns(10);
		JPanel panel_5 = new JPanel();
		panel_5.setBounds(85, 0, 215, 59);
		panel.add(panel_5);
		panel_5.setBackground(Color.WHITE);
		panel_5.setBorder(new TitledBorder(null, "USERNAME", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0,128,128)));
		panel_5.setLayout(new BorderLayout(0, 0));
		panel_5.add(textField);
		
		JButton btnSignin = new JButton("SignIn");
		btnSignin.setBounds(143, 203, 90, 30);
		btnSignin.setBackground(new Color(205, 92, 92));
		btnSignin.setForeground(Color.white);
		panel.add(btnSignin);
		
		textField_1 = new JPasswordField();
		textField_1.setColumns(10);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBounds(85, 70, 215, 58);
		panel.add(panel_7);
		panel_7.setBackground(Color.WHITE);
		panel_7.setBorder(new TitledBorder(null, "PASSWORD", TitledBorder.LEADING, TitledBorder.TOP,null, new Color(0,128,128)));
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		panel_7.add(textField_1);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBounds(143, 139, 90, 30);
		btnNewButton.setForeground(Color.white);
		btnNewButton.setBackground(new Color(0,128,128));
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			    client = new Client(textField.getText(),textField_1.getText());	
				client.start();
				frame.setVisible(false);
          
			}
		});
		
	}
}
