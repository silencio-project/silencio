package de.dhbw.silencio.ui.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.List;

import com.opencsv.bean.*;

// todo: exception handling
public class FileStorageCsv implements FileStorage {
    private final String filenameProtocol = "protocols.csv";
    private final String filenameRoom = "rooms.csv";
    private ColumnPositionMappingStrategy<Protocol> mappingStrategyProtocol;
    private ColumnPositionMappingStrategy<Room> mappingStrategyRoom;

    public FileStorageCsv(){
        CreateMappingStrategyProtocol();
        CreateMappingStrategyRoom();
    }

    private void CreateMappingStrategyProtocol(){
        mappingStrategyProtocol = new ColumnPositionMappingStrategy<Protocol>();
        mappingStrategyProtocol.setType(Protocol.class);

        String[] columns = new String[] {"roomUid", "data", "time"};
        mappingStrategyProtocol.setColumnMapping(columns);
    }

    private void CreateMappingStrategyRoom(){
        mappingStrategyRoom = new ColumnPositionMappingStrategy<Room>();
        mappingStrategyRoom.setType(Room.class);

        String[] columns = new String[] {"uid", "description", "length", "width", "deskDistanceToBoard", "deskDistanceToWallLeft", "distanceFirstRowToDesk", "tableLength", "tableWidth", "rows", "tablesPerRow", "betweenRows", "distanceToWallLeft"};
        mappingStrategyRoom.setColumnMapping(columns);
    }

    public List<Protocol> getAllProtocols(){
        Reader csvReader = null;
        try {
            csvReader = new FileReader(filenameProtocol);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new CsvToBeanBuilder<Protocol>(csvReader)
            .withMappingStrategy(mappingStrategyProtocol)
            .build()
            .parse();
    }

    public List<Room> getAllRooms(){
        Reader csvReader = null;
        try {
            csvReader = new FileReader(filenameRoom);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new CsvToBeanBuilder<Room>(csvReader)
            .withMappingStrategy(mappingStrategyRoom)
            .build()
            .parse();
    }


    public void storeProtocol(Protocol protocol){
        try {
            FileWriter writer = new FileWriter(filenameProtocol, true);
            StatefulBeanToCsvBuilder<Protocol> builder = new StatefulBeanToCsvBuilder<Protocol>(writer);
            StatefulBeanToCsv<Protocol> beanWriter = builder.withMappingStrategy(mappingStrategyProtocol).build();

            beanWriter.write(protocol);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void storeRoom(Room room){
        try {
            FileWriter writer = new FileWriter(filenameRoom, true);
            StatefulBeanToCsvBuilder<Room> builder = new StatefulBeanToCsvBuilder<Room>(writer);
            StatefulBeanToCsv<Room> beanWriter = builder.withMappingStrategy(mappingStrategyRoom).build();

            beanWriter.write(room);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
