package de.dhbw.silencio.audio;

/**
 * Calculates the root-mean-square.
 *
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public class RootMeanSquare {
    /**
     * Calculates the root-mean-square of an array of integers.
     *
     * @param array array to calculate the root-mean-square of
     * @return the root-mean-square
     * @see <a href="https://en.wikipedia.org/wiki/Root_mean_square">Root Mean Square (Wikipedia)</a>
     */
    public static double calculate(int[] array) {
        double square = 0;
        for (int element : array) {
            square += element * element;
        }

        return Math.sqrt((square / (double) (array.length)));
    }
}
