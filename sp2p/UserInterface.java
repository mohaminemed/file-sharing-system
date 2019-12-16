//package sp2p;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

public class UserInterface extends JFrame  {

	private JPanel frame;
	private JTextField textField;
	private JTextArea text;
    protected static JTable  table;
    protected static  JProgressBar m_progress = new JProgressBar();
    public  String searchFile="";
    private Object[][] data;
    private FTPClient ftpClient= new FTPClient();

	
    protected  static void setProgressValue(final int value) {
        Runnable runner = new Runnable() {
          public void run() {
            m_progress.setValue(value);
          }
        };
        SwingUtilities.invokeLater(runner);
      }

      protected static void setProgressMaximum(final int value) {
        Runnable runner = new Runnable() {
          public void run() {
            m_progress.setMaximum(value);
          }
        };
        SwingUtilities.invokeLater(runner);
      }

      protected static void setProgressString(final String string) {
        Runnable runner = new Runnable() {
          public void run() {
            m_progress.setString(string);
          }
        };
        SwingUtilities.invokeLater(runner);
      }



	public String getSearchFile() {
		return searchFile;
	}




	public void setSearchFile(String searchFile) {
		this.searchFile = searchFile;
	}




	/**
	 * Create the application.
	 */
	public UserInterface(String user ) {
		super(user);
		frame = new JPanel();
		frame.setBounds(100, 100, 500, 462);
		frame.setLayout(null);
		data=new Object[100][100];
		this.setResizable(false);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 500, 124);
		panel.setBackground(Color.GRAY);
		frame.add(panel);
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 125, 500, 334);
		panel_1.setBackground(Color.WHITE);
		frame.add(panel_1);
		panel_1.setLayout(null);
		panel.setLayout(new BorderLayout(0, 0));
		JButton btnOk = new JButton("ok");
		btnOk.setBounds(240, 20, 53, 30);
		btnOk.setEnabled(false);
		textField = new JTextField();
		textField.setBounds(6, 20, 209, 30);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				btnOk.setEnabled(true);
			}
		});
		textField.setColumns(10);
	
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setBackground(Color.GRAY);
		panel_5.setBorder(new TitledBorder(null, "SEARCH", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0,128,128)));
		panel_5.setLayout(null);
		panel_5.add(textField);
		JButton btnAppler = new JButton("",new ImageIcon("/home/med/Bureau/call.png") );
		btnAppler.setBounds(310, 20, 30, 30);
		 btnAppler.addMouseListener(new MouseAdapter() {
		 	@Override
		 	public void mouseEntered(MouseEvent e) {
		 		btnAppler.setToolTipText("Appeler");
		 	}
		 });
		// panel_5.add(btnAppler);
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setSearchFile(textField.getText());
				 System.out.println(searchFile);
				 try {
					Client.searchFile(searchFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Get IT !!");
			    String  title[] = {"USER","HOST","PORT"};
              
		        table=new JTable(data,title);
		        table.setBackground(Color.WHITE);
		       JScrollPane ps = new JScrollPane(table);
		       ps.setForeground(Color.red);
		       ps.setBounds(0, 0,500, 500);
		       panel_1.add(ps);
		       table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				String path=(String) table.getValueAt(table.getSelectedRow(),1);
				int port=Integer.parseInt((String)table.getValueAt(table.getSelectedRow(),2));
				System.out.println(path);
				
				try {
					
					ftpClient.connect(path, port);
					if(ftpClient.retr(user,searchFile))
						{
					    text.append("!! Successfully transferred !!");}
					else {
						
						text.setForeground(new Color(205, 92, 92));
					    text.append("!! Erroneous transfer !!");
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

			}
		});
		
		btnOk.setForeground(Color.white);
		btnOk.setBackground(new Color(0,128,128));
		panel_5.add(btnOk);

		JButton btnLogout = new JButton("LogOut");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ftpClient.disconnect();
					setVisible(false);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLogout.setBounds(371, 20, 89, 30);
		panel_5.add(btnLogout);
		btnLogout.setBackground(new Color(205, 92, 92));
		btnLogout.setForeground(Color.white);
		text=new JTextArea();
		text.setBounds(150, 90, 190, 30);
		panel_5.add(text);
		text.setBackground(Color.white);
		text.setForeground(new Color(0,128,128));
		this.setBounds(10, 10, 500, 500);
		this.getContentPane().add(frame);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}




	public Object[][] getData() {
		return data;
	}




	public void setData(Object[][] data) {
		this.data = data;
	}
}

	
		

