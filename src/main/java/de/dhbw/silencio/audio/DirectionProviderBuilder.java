package de.dhbw.silencio.audio;

import javax.sound.sampled.AudioFormat;
import java.util.function.Consumer;

/**
 * Builder for a {@link DirectionProvider}.
 *
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public class DirectionProviderBuilder {
    private static final int DEFAULT_SAMPLE_RATE = 48_000;

    private AudioFormat format = new AudioFormat(
        AudioFormat.Encoding.PCM_SIGNED,
        DEFAULT_SAMPLE_RATE,
        16,
        1,
        2,
        48000,
        true
    );

    private String deviceName1 = "Microphone 1";
    private String deviceName2 = "Microphone 2";

    private int bufferSize = 32_768;
    private int rmsThreshold = 20;

    private double microphoneDistance = 1;
    private int sampleRate = DEFAULT_SAMPLE_RATE;

    private Consumer<Double> callback = d -> {
    };

    /**
     * Build the {@link DirectionProvider}.
     *
     * @return the direction provider
     */
    public DirectionProvider build() {
        return new DirectionProvider(
            format,
            deviceName1,
            deviceName2,
            bufferSize,
            rmsThreshold,
            microphoneDistance,
            sampleRate,
            callback
        );
    }

    /**
     * Specify an {@link AudioFormat}.
     * <p>
     * Default: 48,000 samples, 16 bit, 1 channel, 2 frames, 48,000 frame rate, big endian.
     *
     * @param format audio format to use
     * @return the builder
     */
    public DirectionProviderBuilder format(AudioFormat format) {
        this.format = format;
        return this;
    }

    /**
     * Specify the name of the first microphone.
     * <p>
     * Default: <code>Microphone 1</code>
     *
     * @param name name of the first microphone
     * @return the builder
     */
    public DirectionProviderBuilder deviceName1(String name) {
        this.deviceName1 = name;
        return this;
    }

    /**
     * Specify the name of the second microphone.
     * <p>
     * Default: <code>Microphone 2</code>
     *
     * @param name name of the second microphone
     * @return the builder
     */
    public DirectionProviderBuilder deviceName2(String name) {
        this.deviceName2 = name;
        return this;
    }

    /**
     * Specify the buffer size.
     * <p>
     * Default: <code>32,768</code>.
     *
     * @param size buffer size to use
     * @return the builder
     */
    public DirectionProviderBuilder bufferSize(int size) {
        this.bufferSize = size;
        return this;
    }

    /**
     * Specify the threshold to be used after calculating the {@link RootMeanSquare}. Values below this threshold will
     * be ignored.
     * <p>
     * Default: <code>20</code>
     *
     * @param threshold threshold to use for filtering data
     * @return the builder
     */
    public DirectionProviderBuilder rmsThreshold(int threshold) {
        this.rmsThreshold = threshold;
        return this;
    }

    /**
     * Specify the distance between both microphones in meters.
     * <p>
     * Default: <code>1</code>
     *
     * @param distance distance between both microphones
     * @return the builder
     */
    public DirectionProviderBuilder microphoneDistance(double distance) {
        this.microphoneDistance = distance;
        return this;
    }

    /**
     * Specify the sample rate.
     * <p>
     * Default: <code>48,000</code>
     *
     * @param rate sample rate
     * @return the builder
     */
    public DirectionProviderBuilder sampleRate(int rate) {
        this.sampleRate = rate;
        return this;
    }

    /**
     * Specify a callback to handle the calculated angle.
     * <p>
     * Default: noop
     *
     * @param callback callback to handle the angle
     * @return the builder
     */
    public DirectionProviderBuilder callback(Consumer<Double> callback) {
        this.callback = callback;
        return this;
    }
}
