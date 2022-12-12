package de.dhbw.silencio.storage;

import java.io.IOException;
import java.util.List;

/**
 * This is a generic repository for accessing data.
 * <p>
 * Data handled by this interface must be expressible as a list of objects, such as a list of <code>Person</code>
 * objects. Implementations decide how to store the data.
 *
 * @param <T> object to store
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public interface GenericRepository<T> {
    /**
     * Retrieves all data sets.
     *
     * @return all data sets
     * @throws IOException if an error occurs while reading the data
     */
    List<T> getAll() throws IOException;

    /**
     * Saves a data set.
     * <p>
     * It is up to the implementation, whether the data set is appended to existing data sets or overrides existing
     * ones.
     *
     * @param t data to save
     * @throws IOException if an error occurs while writing the data
     */
    void save(T t) throws IOException;
}
