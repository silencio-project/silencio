package de.dhbw.silencio.audio;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;

public class AudioInput {
    private static final String MICROPHONE_1 = "Microphone 1";
    private static final String MICROPHONE_2 = "Microphone 2";

    private static final int BUFFER_SIZE = 48000;

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

        try (Microphone m1 = new Microphone(MICROPHONE_1, format);
             Microphone m2 = new Microphone(MICROPHONE_2, format)) {
            m1.prepare();
            m2.prepare();

            m1.run();
            m2.run();

            while (true) {
                System.out.printf("Microphone 1: %s%n", Arrays.toString(m1.getNextChunk(BUFFER_SIZE)));
                System.out.printf("Microphone 2: %s%n", Arrays.toString(m2.getNextChunk(BUFFER_SIZE)));
                System.out.println();
            }
        }
    }
}
