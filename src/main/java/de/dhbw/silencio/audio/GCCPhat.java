package de.dhbw.silencio.audio;

/**
 * Implementation of the Generalized Cross Correlation Algorithm with Phase Transform (GCC PHAT).
 *
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public class GCCPhat {
    /**
     * Performs the GCC PHAT algorithm on two complex arrays of sound signals.
     *
     * @param data1 first array of sound signals
     * @param data2 second array of sound signals
     * @return the index of the time delay of arrival (TDOA)
     */
    public static double calculate(Complex[] data1, Complex[] data2) {
        if (data1.length != data2.length) {
            throw new IllegalArgumentException("Both arrays must have the same length.");
        }

        FastFourierTransformation.transform(data1);
        FastFourierTransformation.transform(data2);

        Complex[] result = new Complex[data1.length];
        for (int i = 0; i < data1.length; i++) {
            Complex c = data1[i].multiply(data2[i].conjugate());
            result[i] = c.divide(new Complex(c.modulus(), 0));
        }

        FastFourierTransformation.transformInverse(result);
        return argmax(result);
    }

    private static int argmax(Complex[] complexes) {
        int index = -1;
        Complex value = new Complex();

        for (int i = 0; i < complexes.length; i++) {
            if (complexes[i].real > value.real) {
                value = complexes[i];
                index = i;
            }
        }

        return index;
    }
}
