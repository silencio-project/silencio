package de.dhbw.silencio.storage;

/**
 * A convenience class to store {@link Protocol}s.
 *
 * @author Paul Antoni
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public class ProtocolRepositoryCsv extends GenericCsvRepository<Protocol> {
    private static final String FILE_NAME = "protocols.csv";

    /**
     * Constructs a new repository for working with {@link Protocol}s.
     * <p>
     * This uses <code>protocols.csv</code> as a default file and defines all required columns.
     */
    public ProtocolRepositoryCsv() {
        super(FILE_NAME, new String[]{"roomUid", "data", "time"}, Protocol.class);
    }
}
