package de.dhbw.silencio.audio;

/**
 * Provides a triangulation calculation.
 *
 * @author Luis Binzenhöfer
 * @author Daniel Eremeev
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public class Triangulation {
    private static final double SPEED_OF_SOUND = 343.2;

    /**
     * Calculates the angle pointing to the direction a sound comes from.
     * <p>
     * It uses the formula <code>arcsin(delta-t/d) * v</code>, where delta-t is the temporal difference of a signal
     * received by two microphones, d the distance between those microphones in meters and v the speed of sound.
     *
     * @param timeDelta          temporal difference of a signal received by two microphones in seconds
     * @param microphoneDistance distance between those microphones in meters
     * @return the angle pointing to the direction a sound comes from
     */
    public static double calculate(double timeDelta, double microphoneDistance) {
        return Math.asin(timeDelta / microphoneDistance) * SPEED_OF_SOUND;
    }
}
