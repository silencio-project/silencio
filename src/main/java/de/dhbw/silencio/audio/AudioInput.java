package de.dhbw.silencio.audio;

import javax.sound.sampled.AudioFormat;

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
                byte[] data1 = microphone1.getNextChunk(BUFFER_SIZE);
                byte[] data2 = microphone2.getNextChunk(BUFFER_SIZE);

                Complex[] cinput = new Complex[data1.length];
                for (int i = 0; i < data1.length; i++) {
                    cinput[i] = new Complex(data1[i], 0.0);
                }

                FastFourierTransformation.fft(cinput);

                System.out.println("Results:");
                for (Complex c : cinput) {
                    System.out.println(c);
                }

//                System.out.printf("Microphone 1: %s%n", findMaxValue(data1));
//                System.out.printf("Microphone 2: %s%n", findMaxValue(data2));
                System.out.println();
            }
        }
    }

    private int findMaxValue(byte[] array) {
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
