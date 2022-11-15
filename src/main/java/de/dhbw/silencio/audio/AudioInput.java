package de.dhbw.silencio.audio;

import javax.sound.sampled.AudioFormat;
import java.util.Arrays;
import java.util.concurrent.*;

public class AudioInput {
    private static final String MICROPHONE_1 = "Microphone 1";
    private static final String MICROPHONE_2 = "Microphone 2";

    private static final int BUFFER_SIZE = 4096;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

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
            m1.listen();
            m2.listen();

            while (true) {
                Future<byte[]> futureM1 = executor.submit(() -> m1.getNextChunk(BUFFER_SIZE));
                Future<byte[]> futureM2 = executor.submit(() -> m2.getNextChunk(BUFFER_SIZE));

                if (futureM1.isDone() && futureM2.isDone()) {
                    System.out.printf("Microphone 1: %s%n", Arrays.toString(futureM1.get()));
                    System.out.printf("Microphone 2: %s%n", Arrays.toString(futureM2.get()));
                    System.out.println();
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
