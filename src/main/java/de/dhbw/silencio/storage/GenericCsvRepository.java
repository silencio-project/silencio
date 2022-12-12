package de.dhbw.silencio.storage;

import com.opencsv.bean.*;
import com.opencsv.exceptions.*;

import java.io.*;
import java.util.List;

/**
 * Provides basic reading/writing access to a CSV file.
 *
 * @param <T> object to store as CSV
 * @author Paul Antoni
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public class GenericCsvRepository<T> implements GenericRepository<T> {
    private final String fileName;
    private final ColumnPositionMappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<>();

    /**
     * Constructs a new generic repository for working with data in CSV files.
     *
     * @param fileName file name
     * @param columns  names of the columns
     * @param clazz    class reference to (de)serialize objects
     */
    public GenericCsvRepository(String fileName, String[] columns, Class<T> clazz) {
        this.fileName = fileName;

        mappingStrategy.setType(clazz);
        mappingStrategy.setColumnMapping(columns);
    }

    public List<T> getAll() throws IOException {
        try (Reader reader = new FileReader(fileName)) {
            return new CsvToBeanBuilder<T>(reader)
                .withMappingStrategy(mappingStrategy)
                .build()
                .parse();
        }
    }

    public void save(T t) throws IOException {
        try (Writer writer = new FileWriter(fileName, true)) {
            new StatefulBeanToCsvBuilder<T>(writer)
                .withMappingStrategy(mappingStrategy)
                .build()
                .write(t);
        } catch (CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new IOException(e);
        }
    }
}
