package de.dhbw.silencio.audio;

import lombok.*;

import javax.sound.sampled.*;
import java.io.Closeable;

/**
 * Provides access to a microphone.
 * <p>
 * This class uses the Java Sound API ({@link TargetDataLine}, {@link AudioFormat}, {@link Mixer}) to read data from a
 * microphone. When creating a {@link Microphone}, you have to provide a {@link #deviceName}. This is the name of the
 * microphone in your computer's system settings. How to get this string depends on your operating system. On macOS, for
 * example, you can find the device name in the <i>Audio MIDI Setup</i> application. You also have to provide an
 * {@link AudioFormat} that contains configuration on how the data should be read and structured.
 * <p>
 * To make sure the {@link TargetDataLine} is closed, when not needed anymore, this class implements {@link Closeable}.
 * Make sure using try-with resources to probably close all resources. In case you want to work with multiple
 * microphones, this class implements {@link Runnable}, reading the data in the background.
 *
 * @author Yannick Kirschen
 * @author Moritz Thoma
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class Microphone implements Closeable, Runnable {
    @Getter
    private final String deviceName;

    @Getter
    private final AudioFormat format;

    private TargetDataLine line;

    /**
     * Prints all available audio devices.
     * <p>
     * This is a convenience method, that uses Java's {@link Mixer.Info} to get all devices connected to the computer.
     * By using this method, you can get the {@link #deviceName} needed for instantiating a {@link Microphone}.
     */
    public static void printAvailableDevices() {
        Line.Info line = new Line.Info(TargetDataLine.class);
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            Mixer mixer = AudioSystem.getMixer(info);

            if (mixer.isLineSupported(line)) {
                System.out.println(info.getName());
            }
        }
    }

    /**
     * Prepares the audio device to be read.
     * <p>
     * To be precise, a {@link TargetDataLine} is created based on the {@link AudioFormat} and {@link #deviceName}. The
     * line is opened, which does not mean, that data is processed.
     */
    public void prepare() {
        Line.Info info = new DataLine.Info(TargetDataLine.class, format);
        Mixer mixer = getMixer(deviceName);

        try {
            line = (TargetDataLine) mixer.getLine(info);
            line.open(format);
        } catch (LineUnavailableException e) {
            throw new IllegalArgumentException("Line is not available (%s)".formatted(e.getMessage()));
        }
    }

    /**
     * Copies data from the internal buffer into a byte array.
     *
     * @param bufferSize length of the byte array to copy the data into
     * @return a byte array containing data from the buffer
     */
    public byte[] getNextChunk(int bufferSize) {
        byte[] data = new byte[bufferSize];
        line.read(data, 0, bufferSize);
        return data;
    }

    /**
     * Stops listening on the {@link TargetDataLine}.
     */
    public void stopListen() {
        line.stop();
    }

    @Override
    public void close() {
        line.close();
    }

    @Override
    public void run() {
        line.start();
    }

    private Mixer getMixer(String deviceName) throws IllegalArgumentException {
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
        Line.Info lineInfo = new Line.Info(TargetDataLine.class);

        for (Mixer.Info info : mixerInfo) {
            Mixer mixer = AudioSystem.getMixer(info);
            if (mixer.isLineSupported(lineInfo)) {
                if (info.getName().equals(deviceName)) {
                    return mixer;
                }
            }
        }

        throw new IllegalArgumentException("Device %s not found.".formatted(deviceName));
    }
}

