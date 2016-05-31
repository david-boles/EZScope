package space.davidboles.ezscope;

import java.awt.EventQueue;

import javax.sound.sampled.LineUnavailableException;

import space.davidboles.lib.program.Logger;

public class Start {
	
	public static final String version = "v0.0A";
	
	public static Scope window;
	public static DefaultCapture capture = new DefaultCapture();
	
	public static void main(String[] args) {
		/*try {
			capture.initLine();
		} catch (LineUnavailableException e1) {
			Logger.uLogger.exception("Initial capture init", e1);
		}*/
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Scope();
					window.frame.setVisible(true);
					
					window.control.updateCaptureInfo();
					window.logInfo("EZScope Started - " + version);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
