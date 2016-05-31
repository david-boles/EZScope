package space.davidboles.ezscope.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import space.davidboles.ezscope.Start;
import space.davidboles.lib.program.Logger;
import space.davidboles.lib.program.ProgramFs;

public class ControlPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -114904481296321247L;
	private JTextField captureInfo;
	private JTextField aF;
	private JTextField sPS;
	private JCheckBox consLog;
	private JTextField numSamp;

	public boolean consLogVal = true;
	public int sPSVal = 44100;
	public int aFVal = 65;
	public int numSampVal = 4410;

	/**
	 * Create the panel.
	 */
	public ControlPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel ConfigPanel = new JPanel();
		add(ConfigPanel, BorderLayout.CENTER);
		ConfigPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblConsoleLogging = new JLabel("Console Logging");
		ConfigPanel.add(lblConsoleLogging);
		
		consLog = new JCheckBox("");
		consLog.setSelected(consLogVal);
		ConfigPanel.add(consLog);
		
		JSeparator separator_2 = new JSeparator();
		ConfigPanel.add(separator_2);
		
		JLabel lblAmplitudeFactor = new JLabel("Amplitude Factor");
		ConfigPanel.add(lblAmplitudeFactor);
		
		aF = new JTextField();
		aF.setText(Integer.toString(aFVal));
		ConfigPanel.add(aF);
		aF.setColumns(4);
		
		JSeparator separator = new JSeparator();
		ConfigPanel.add(separator);
		
		JLabel lblSamplesPerSecond = new JLabel("Samples per Second");
		ConfigPanel.add(lblSamplesPerSecond);
		
		sPS = new JTextField();
		sPS.setText(Integer.toString(sPSVal));
		ConfigPanel.add(sPS);
		sPS.setColumns(4);
		
		JSeparator separator_1 = new JSeparator();
		ConfigPanel.add(separator_1);
		
		JButton set = new JButton("Set Config");
		set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateSettings();
			}
		});
		
		JLabel lblSamples = new JLabel("# Samples");
		ConfigPanel.add(lblSamples);
		
		numSamp = new JTextField();
		numSamp.setText(Integer.toString(numSampVal));
		ConfigPanel.add(numSamp);
		numSamp.setColumns(4);
		
		JSeparator separator_3 = new JSeparator();
		ConfigPanel.add(separator_3);
		ConfigPanel.add(set);
		
		JPanel CapturePanel = new JPanel();
		add(CapturePanel, BorderLayout.SOUTH);
		CapturePanel.setLayout(new BorderLayout(0, 0));
		
		captureInfo = new JTextField();
		captureInfo.setEditable(false);
		CapturePanel.add(captureInfo, BorderLayout.CENTER);
		captureInfo.setColumns(10);
		
		JButton capture = new JButton("Capture!");
		capture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start.window.view.capture();
			}
		});
		CapturePanel.add(capture, BorderLayout.EAST);

	}
	
	public void updateSettings() {
		try {
			//read
			int readAF = Integer.decode(this.aF.getText());
			int readSPS = Integer.decode(this.sPS.getText());
			boolean readConsLog = this.consLog.isSelected();
			int readNumSamps = Integer.decode(this.numSamp.getText());
			
			//set
			this.aFVal = readAF;
			this.sPSVal = readSPS;
			this.numSampVal = readNumSamps;
			if(this.consLogVal != readConsLog) {
				this.consLogVal = readConsLog;
				if(readConsLog) Logger.uLogger = new Logger(System.out, System.err);
				else Logger.uLogger = new Logger(ProgramFs.getProgramFile("log"), ProgramFs.getProgramFile("error"));
			}
			
			//update
			updateCaptureInfo();
			
			Start.window.logInfo("Settings updated!");
		}catch(Exception e) {
			Start.window.logError("Failed to update Settings, please check values.");
			Logger.uLogger.exception("Updating settings", e);
		}
	}
	
	public void updateCaptureInfo() {
		Start.capture.setSampleRate(this.sPSVal);
		this.captureInfo.setText("Capturing " + this.numSampVal + " samples at " + this.sPSVal + "sps, with x" + this.aFVal + " amplitude scale.");
	}

}
