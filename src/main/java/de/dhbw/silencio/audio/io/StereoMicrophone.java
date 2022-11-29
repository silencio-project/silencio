package de.dhbw.silencio.audio.io;

import java.io.Closeable;

/**
 * Allows access to a stereo microphone.
 *
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public interface StereoMicrophone extends Closeable {
    /**
     * Start listening on a microphone.
     */
    void listen();

    /**
     * Get data from the microphone.
     *
     * @param channel channel to use
     * @param buffer  amount of bytes to read
     * @return the data
     */
    byte[] get(Channel channel, int buffer);
}
