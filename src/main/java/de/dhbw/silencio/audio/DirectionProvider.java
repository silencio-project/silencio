package de.dhbw.silencio.audio;

import de.dhbw.silencio.audio.io.*;
import lombok.RequiredArgsConstructor;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Uses two microphones to calculate an angle of a sound source between those microphones.
 *
 * @author Yannick Kirschen
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class DirectionProvider implements Runnable {
    private final AudioFormat format;

    private final String deviceName1;
    private final String deviceName2;

    private final int bufferSize;
    private final int rmsThreshold;

    private final double microphoneDistance;
    private final int sampleRate;

    private final Consumer<Double> callback;

    @Override
    public void run() {
        try (StereoMicrophone microphone = new IsolatedMicrophones(format, deviceName1, deviceName2)) {
            microphone.listen();

            AngleCalculator angleCalculator = new AngleCalculator(microphoneDistance, sampleRate);
            while (true) {
                byte[] raw1 = microphone.get(Channel.ONE, bufferSize);
                byte[] raw2 = microphone.get(Channel.TWO, bufferSize);

                int[] data1 = new MicrophoneDataConverter(raw1).convertTo16BitArray();
                int[] data2 = new MicrophoneDataConverter(raw2).convertTo16BitArray();

                double rms1 = RootMeanSquare.calculate(data1);
                double rms2 = RootMeanSquare.calculate(data2);

                if (rms1 > rmsThreshold && rms2 > rmsThreshold) {
                    double angle = angleCalculator.calculate(data1, data2);
                    callback.accept(angle);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
