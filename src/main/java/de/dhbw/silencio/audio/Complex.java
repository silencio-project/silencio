package de.dhbw.silencio.audio;

import lombok.EqualsAndHashCode;

/**
 * Value object for a complex number.
 *
 * @author Yannick Kirschen
 * @since 1.0.0
 */
@EqualsAndHashCode
public class Complex {
    public final double real;
    public final double imaginary;

    /**
     * Creates a complex number with both real and imaginary part 0.
     */
    public Complex() {
        this(0, 0);
    }

    /**
     * Creates a complex number.
     *
     * @param r real part
     * @param i imaginary part
     */
    public Complex(double r, double i) {
        real = r;
        imaginary = i;
    }

    /**
     * Creates a complex number from an integer.
     *
     * @param i real number to transform into a complex number
     * @return a complex number from an integer
     */
    public static Complex of(int i) {
        return new Complex(i, 0.0);
    }

    /**
     * Creates an array of complex numbers based on an array of integers.
     *
     * @param array array of integers to transform into an array of complex numbers
     * @return an array of complex numbers from an array of integers
     */
    public static Complex[] of(int[] array) {
        Complex[] complexArray = new Complex[array.length];

        for (int i = 0; i < array.length; i++) {
            complexArray[i] = Complex.of(i);
        }

        return complexArray;
    }

    /**
     * Creates the polar form of a complex number.
     *
     * @param rho   absolute value
     * @param theta angle
     * @return the complex number in polar form
     */
    public static Complex polar(double rho, double theta) {
        return (new Complex(rho * Math.cos(theta), rho * Math.sin(theta)));
    }

    /**
     * Adds a complex number to the present one.
     *
     * @param b complex number to add
     * @return a new complex number containing the result of the addition
     */
    public Complex add(Complex b) {
        return new Complex(real + b.real, imaginary + b.imaginary);
    }

    /**
     * Subtracts a complex number from the present one.
     *
     * @param b complex number to subtract
     * @return a new complex number containing the result of the subtraction
     */
    public Complex subtract(Complex b) {
        return new Complex(real - b.real, imaginary - b.imaginary);
    }

    /**
     * Multiplies a complex number to the present one.
     *
     * @param b complex number to multiply
     * @return a new complex number containing the result of the multiplication
     */
    public Complex multiply(Complex b) {
        return new Complex(real * b.real - imaginary * b.imaginary, real * b.imaginary + imaginary * b.real);
    }

    /**
     * @return the complex conjugate (negative imaginary)
     */
    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    @Override
    public String toString() {
        return String.format("(%.6f, %.6f i)", real, imaginary);
    }
}
