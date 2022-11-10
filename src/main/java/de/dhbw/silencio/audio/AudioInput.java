package de.dhbw.silencio.audio;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;

public class AudioInput {
    private static final String MICROPHONE_1 = "Microphone 1";
    private static final String MICROPHONE_2 = "Microphone 2";

    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {
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
            m1.listen();
            m2.listen();

            while (true) {
                System.out.printf("Microphone 1: %s%n", Arrays.toString(m1.getNextChunk(BUFFER_SIZE)));
                System.out.printf("Microphone 2: %s%n", Arrays.toString(m2.getNextChunk(BUFFER_SIZE)));
                System.out.println();
            }
        }
    }
}
