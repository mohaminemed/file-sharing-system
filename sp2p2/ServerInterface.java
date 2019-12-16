package sp2p2;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.io.IOException;

public class ServerInterface extends JFrame{

	private JPanel panel;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	public JTextArea getText() {
		return text;
	}


	public void setText(JTextArea text) {
		this.text = text;
	}


	public JTextArea getTextArea_2() {
		return textArea_2;
	}


	public void setTextArea_2(JTextArea textArea_2) {
		this.textArea_2 = textArea_2;
	}


	public JTextArea getTextArea_1() {
		return textArea_1;
	}


	public void setTextArea_1(JTextArea textArea_1) {
		this.textArea_1 = textArea_1;
	}


	private JTextArea text;
	private JTextArea textArea_2;
	private JTextArea textArea_1;

	/**
	 * Create the application.
	 */
	public ServerInterface(String user ) {
		super(user);
		this.setBounds(280, 200, 810, 450);
		panel = new JPanel();
		panel.setBounds(280, 200, 810, 450);
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Servers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(4, 7, 262, 365);
		panel_2.setBackground(Color.WHITE);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		text = new JTextArea();
		text.setBounds(6, 16, 250, 341);
		panel_2.add(text);
		text.setBackground(Color.WHITE);
		text.setEditable(false);
	    
		panel_3 = new JPanel();
	    panel_3.setBorder(new TitledBorder(null, "Clients", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	    panel_3.setBounds(274, 7, 262, 365);
	    panel_3.setBackground(new Color(0,128,128));
	    panel.add(panel_3);
	    panel_3.setLayout(null);
	    textArea_1 = new JTextArea();
	    textArea_1.setBounds(6, 16, 250, 341);
	    panel_3.add(textArea_1);
		textArea_1.setBackground(new Color(0,128,128));
		textArea_1.setEditable(false);
	    
	    panel_4 = new JPanel();
	    panel_4.setBorder(new TitledBorder(null, "Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	    panel_4.setBounds(543, 7, 262, 365);
	    panel_4.setBackground(Color.WHITE);
	    panel.add(panel_4);
	    panel_4.setLayout(null);
		
	    textArea_2 = new JTextArea();
	    textArea_2.setBounds(6, 16, 250, 341);
	    panel_4.add(textArea_2);
		textArea_2.setBackground(Color.WHITE);
		textArea_2.setEditable(false);
		
		this.add(panel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		}
}

	
		


