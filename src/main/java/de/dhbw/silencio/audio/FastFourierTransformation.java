package de.dhbw.silencio.audio;

/**
 * Implementation of the Fast Fourier Transformation (FFT).
 *
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public class FastFourierTransformation {
    /**
     * Performs a Fast Fourier Transformation on a given array of complex numbers.
     * <p>
     * The Cooley–Tukey algorithm, named after J.W. Cooley and John Tukey, is the most common fast Fourier transform
     * (FFT) algorithm. It re-expresses the discrete Fourier transform (DFT) of an arbitrary composite size N = N1N2 in
     * terms of N1 smaller DFTs of sizes N2, recursively, to reduce the computation time to O(N log N) for highly
     * composite N (smooth numbers).
     * <p>
     *
     * @param coefficients numbers to perform FFT on (size must be power of 2)
     * @see <a href="https://en.wikipedia.org/wiki/Cooley%E2%80%93Tukey_FFT_algorithm">Cooley–Tukey Algorithm
     * (Wikipedia)</a>
     */
    public static void transform(Complex[] coefficients) {
        int size = coefficients.length;
        if (size <= 1) {
            return;
        }

        Complex[] even = new Complex[size / 2];
        Complex[] odd = new Complex[size / 2];

        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                even[i / 2] = coefficients[i];
            } else {
                odd[(i - 1) / 2] = coefficients[i];
            }
        }

        transform(even);
        transform(odd);

        for (int k = 0; k < size / 2; k++) {
            Complex t = Complex.polar(1.0, -2 * Math.PI * k / size).multiply(odd[k]);
            coefficients[k] = even[k].add(t);
            coefficients[k + size / 2] = even[k].subtract(t);
        }
    }

    /**
     * Performs an Inverse Fast Fourier Transformation on a given array of complex numbers.
     * <p>
     * This uses {@link #transform(Complex[])} for performing the FFT. Before, it calculates the complex conjugate of
     * all values, performs the FFT and calculates the complex conjugate again.
     *
     * @param coefficients numbers to perform IFFT on (size must be power of 2)
     */
    public static void transformInverse(Complex[] coefficients) {
        transformAllToConjugate(coefficients);
        transform(coefficients);
        transformAllToConjugate(coefficients);
    }

    private static void transformAllToConjugate(Complex[] complexes) {
        for (int i = 0; i < complexes.length; i++) {
            complexes[i] = complexes[i].conjugate();
        }
    }
}
