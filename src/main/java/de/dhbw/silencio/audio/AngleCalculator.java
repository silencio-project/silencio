package de.dhbw.silencio.audio;

import lombok.RequiredArgsConstructor;

/**
 * Calculates the angle between two microphones by using the GCC PHAT algorithm and triangulation..
 *
 * @author Yannick Kirschen
 * @see GCCPhat
 * @see Triangulation
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class AngleCalculator {
    private final double microphoneDistance;
    private final int signalsPerSecond;

    /**
     * Calculates the angle between two microphones.
     *
     * @param data1 data of the first microphone
     * @param data2 data of the second microphone
     * @return the angle between those microphones
     */
    public double calculate(int[] data1, int[] data2) {
        Complex[] complexes1 = Complex.of(data1);
        Complex[] complexes2 = Complex.of(data2);

        double timeDelta = GCCPhat.calculate(complexes1, complexes2) / (double) signalsPerSecond;
        return Triangulation.calculate(timeDelta, microphoneDistance);
    }
}
