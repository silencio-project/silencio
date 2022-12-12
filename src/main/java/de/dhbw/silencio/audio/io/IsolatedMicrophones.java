package de.dhbw.silencio.audio.io;

import de.dhbw.silencio.audio.Microphone;

import javax.sound.sampled.AudioFormat;

/**
 * Provides access to two isolated microphones.
 *
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public class IsolatedMicrophones implements StereoMicrophone {
    private final Microphone microphone1;
    private final Microphone microphone2;

    public IsolatedMicrophones(AudioFormat format, String name1, String name2) {
        microphone1 = new Microphone(name1, format);
        microphone2 = new Microphone(name2, format);

        microphone1.prepare();
        microphone2.prepare();
    }

    @Override
    public void listen() {
        new Thread(microphone1).start();
        new Thread(microphone2).start();
    }

    @Override
    public byte[] get(Channel channel, int buffer) {
        return (channel == Channel.ONE ? microphone1 : microphone2).getNextChunk(buffer);
    }

    @Override
    public void close() {
        microphone1.close();
        microphone2.close();
    }
}
