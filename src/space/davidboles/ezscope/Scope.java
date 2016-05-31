package space.davidboles.ezscope;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTextField;

import space.davidboles.ezscope.window.ControlPanel;
import space.davidboles.ezscope.window.ViewPanel;
import space.davidboles.lib.program.Logger;

public class Scope {

	public JFrame frame;
	private JTextField txtEzscopeStarting;
	public ControlPanel control;
	public ViewPanel view;


	/**
	 * Create the application.
	 */
	public Scope() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		control = new ControlPanel();
		frame.getContentPane().add(control, BorderLayout.NORTH);
		
		view = new ViewPanel();
		frame.getContentPane().add(view, BorderLayout.CENTER);
		
		txtEzscopeStarting = new JTextField();
		txtEzscopeStarting.setText("EZScope starting...");
		txtEzscopeStarting.setEditable(false);
		frame.getContentPane().add(txtEzscopeStarting, BorderLayout.SOUTH);
		txtEzscopeStarting.setColumns(10);
		
		frame.pack();
		frame.setSize(frame.getSize().width, 400);
	}
	
	public void logInfo(String info) {
		Logger.uLogger.log(info);
		this.txtEzscopeStarting.setText(info);
	}
	
	public void logError(String message) {
		Logger.uLogger.error(message);
		this.txtEzscopeStarting.setText("Error: " + message);
	}

}
