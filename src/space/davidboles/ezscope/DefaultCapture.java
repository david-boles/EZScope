package space.davidboles.ezscope;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import space.davidboles.lib.audio.AudioFs;

public class DefaultCapture {
	
	protected TargetDataLine inputLine;
	protected float sampleRate = 44100;
	
	public double[] captureSamples(int samples) throws LineUnavailableException {
		if(inputLine != null) inputLine.close();
		initLine();
		if(samples > 0 && samples < (Integer.MAX_VALUE)) {//TODO VALUE/2 if 16bit
			if(inputLine != null && inputLine.isOpen()) {
				
				byte[] inputBytes = new byte[samples];//TODO samples*2 if 16bit
				inputLine.read(inputBytes, 0, inputBytes.length);
				double[] inputDoubles = AudioFs.byte_Signed_BEndianToDouble(inputBytes);
				
				return inputDoubles;
				
			}else throw new NullPointerException("Input line was not successfully initialized, try initLine()...");
		}else throw new IllegalArgumentException("# of samples is less than or equal to 0 or greater than maximum");
	}
	
	private void initLine() throws LineUnavailableException {
		AudioFormat format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("Line not supported");
            System.exit(0);
        }
        inputLine = (TargetDataLine) AudioSystem.getLine(info);
        inputLine.open(format);
        inputLine.start();
	}
	
	public void setSampleRate(float samplesPerSecond) {
		if(samplesPerSecond > 0 && samplesPerSecond < Float.POSITIVE_INFINITY) this.sampleRate = samplesPerSecond;
		else throw new IllegalArgumentException("Unable to set sPS: not within range 0 < sPS < inf");
	}
	
	public float getSampleRate() {
		return this.sampleRate;
	}
	
	protected AudioFormat getAudioFormat() {
        float sampleRate = this.sampleRate;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }

}
