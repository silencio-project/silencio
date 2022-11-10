package de.dhbw.silencio.audio;

import lombok.*;

import javax.sound.sampled.*;
import java.io.Closeable;

@RequiredArgsConstructor
public class Microphone implements Closeable {
    @Getter
    private final String deviceName;

    @Getter
    private final AudioFormat format;

    private TargetDataLine line;

    public static void printAvailableDevices() {
        Line.Info line = new Line.Info(TargetDataLine.class);
        for (Mixer.Info info : AudioSystem.getMixerInfo()) {
            Mixer mixer = AudioSystem.getMixer(info);

            if (mixer.isLineSupported(line)) {
                System.out.println(info.getName());
            }
        }
    }

    public static Mixer getMixer(String deviceName) throws IllegalArgumentException {
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

    public void listen() {
        Line.Info info = new DataLine.Info(TargetDataLine.class, format);
        Mixer mixer = getMixer(deviceName);

        try {
            line = (TargetDataLine) mixer.getLine(info);
            line.open(format);
            line.start();
        } catch (LineUnavailableException e) {
            throw new IllegalArgumentException("Line is not available (%s)".formatted(e.getMessage()));
        }
    }

    public byte[] getNextChunk(int bufferSize) {
        byte[] data = new byte[bufferSize];
        line.read(data, 0, data.length);
        return data;
    }

    public void stopListen() {
        line.stop();
    }

    @Override
    public void close() {
        line.close();
    }
}

