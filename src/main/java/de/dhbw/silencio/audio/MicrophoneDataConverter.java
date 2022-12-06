package de.dhbw.silencio.audio;

import lombok.RequiredArgsConstructor;

/**
 * Converter for a byte array read from a microphone.
 * <p>
 * This class implies, that the data in the given byte array is stored as big endian with 16 bit. Considering the array
 * <p>
 * <code>[a, b, c, d]</code>
 * <p>
 * the actual data calculates as follows:
 * <p>
 * <code>[a*256+b, c*256+d]</code>.
 *
 * @author Yannick Kirschen
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class MicrophoneDataConverter {
    private final byte[] data;

    /**
     * Converts a big endian byte array into an int array.
     *
     * @return the converted array
     */
    public int[] convertTo16BitArray() {
        int[] data16Bit = new int[data.length / 2];

        for (int i = 0, j = 0; i < data.length - 1; i += 2, j++) {
            data16Bit[j] = (data[i] * 256) | (data[i + 1] & 255);
        }

        return data16Bit;
    }
}
