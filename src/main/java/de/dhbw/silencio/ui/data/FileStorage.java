package de.dhbw.silencio.ui.data;

import java.util.List;

public interface FileStorage {
    List<Protocol> getAllProtocols();
    List<Room> getAllRooms();
    void storeProtocol(Protocol protocol);
    void storeRoom(Room room);

}
