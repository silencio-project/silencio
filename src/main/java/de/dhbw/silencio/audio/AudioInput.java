package de.dhbw.silencio.audio;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;

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

        try (Microphone microphone1 = new Microphone(MICROPHONE_1, format);
             Microphone microphone2 = new Microphone(MICROPHONE_2, format)) {
            microphone1.prepare();
            microphone2.prepare();

            microphone1.run();
            microphone2.run();

            while (true) {
                byte[] raw1 = microphone1.getNextChunk(BUFFER_SIZE);
                byte[] raw2 = microphone2.getNextChunk(BUFFER_SIZE);

                int[] data1 = new MicrophoneDataConverter(raw1).convertTo16BitArray();
                int[] data2 = new MicrophoneDataConverter(raw2).convertTo16BitArray();

                Complex[] complexes1 = Complex.of(data1);
                Complex[] complexes2 = Complex.of(data1);

                FastFourierTransformation.transform(complexes1);
                FastFourierTransformation.transform(complexes2);

                System.out.printf("Microphone 1: %s%n", Arrays.toString(complexes1));
                System.out.printf("Microphone 2: %s%n", Arrays.toString(complexes2));
                System.out.println();
            }
        }
    }

    /**
     * Temporarily unused.
     */
    private int findMaxValue(int[] array) {
        byte peak = 0;
        int index = -1;

        for (int i = 0; i < array.length; i++) {
            byte sample = (byte) Math.abs(array[i]);

            if (sample > peak) {
                peak = sample;
                index = i;
            }
        }

        return index;
    }
}
