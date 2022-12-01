package de.dhbw.silencio.audio;

import de.dhbw.silencio.audio.io.*;

import javax.sound.sampled.AudioFormat;
import java.io.IOException;

/**
 * (Hopefully) temporary class to test the audio processing.
 *
 * @author Yannick Kirschen
 * @author Moritz Thoma
 * @since 1.0.0
 */
public class AudioInput {
    private static final String MICROPHONE_1 = "Microphone 1";
    private static final String MICROPHONE_2 = "Microphone 2";

    private static final int BUFFER_SIZE = 65_536;
    private static final int MICROPHONE_DISTANCE = 1;

    public static void main(String[] args) {
        new AudioInput().run();
    }

    private void run() {
        AudioFormat format = new AudioFormat(
            AudioFormat.Encoding.PCM_SIGNED,
            48000.0f,
            16,
            1,
            2,
            48000,
            true
        );

        Microphone.printAvailableDevices();

        try (StereoMicrophone microphone = new IsolatedMicrophones(format, MICROPHONE_1, MICROPHONE_2)) {
            microphone.listen();

            while (true) {
                byte[] raw1 = microphone.get(Channel.ONE, BUFFER_SIZE);
                byte[] raw2 = microphone.get(Channel.ONE, BUFFER_SIZE);

                int[] data1 = new MicrophoneDataConverter(raw1).convertTo16BitArray();
                int[] data2 = new MicrophoneDataConverter(raw2).convertTo16BitArray();

                double angle = new AngleCalculator(MICROPHONE_DISTANCE, BUFFER_SIZE).calculate(data1, data2);
                System.out.printf("Angle: %s%n", angle);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
