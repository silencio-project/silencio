package de.dhbw.silencio.storage;

/**
 * A convenience class to store {@link Room}s.
 *
 * @author Paul Antoni
 * @author Yannick Kirschen
 * @since 1.0.0
 */
public class RoomRepositoryCsv extends GenericCsvRepository<Room> {
    private static final String FILE_NAME = "rooms.csv";

    /**
     * Constructs a new repository for working with {@link Room}s.
     * <p>
     * This uses <code>rooms.csv</code> as a default file and defines all required columns.
     */
    public RoomRepositoryCsv() {
        super(FILE_NAME, new String[]{
                "uid", "description", "length", "width", "deskDistanceToBoard",
                "deskDistanceToWallLeft", "distanceFirstRowToDesk", "tableLength",
                "tableWidth", "rows", "tablesPerRow", "betweenRows", "distanceToWallLeft"},
            Room.class);
    }
}
