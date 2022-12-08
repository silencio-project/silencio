package de.dhbw.silencio.ui.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;


public class FileStorage {

    final String filenameProtocol = "protocols.csv";
    final String filenameRoom = "rooms.csv";
    ColumnPositionMappingStrategy mappingStrategyProtocol;
    ColumnPositionMappingStrategy mappingStrategyRoom;


    public FileStorage(){
        mappingStrategyProtocol = new ColumnPositionMappingStrategy();
        mappingStrategyProtocol.setType(Protocol.class);

        mappingStrategyRoom = new ColumnPositionMappingStrategy();
        mappingStrategyRoom.setType(Room.class);
    }
//    public ArrayList<Protocol> getAllProtocols{
//
//    }
//
//    public ArrayList<Room> getAllRooms{
//
//    }
    //TODO exceptionhandling
    public void storeProtocol(Protocol protocol){
        try {
            FileWriter writer = new FileWriter(filenameProtocol);
            String[] columns = new String[] {"roomUid", "data", "time"};
            mappingStrategyProtocol.setColumnMapping(columns);
            StatefulBeanToCsvBuilder<Protocol> builder = new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategyProtocol).build();

            beanWriter.write(protocol);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void storeRoom(Room room){
        try {
            FileWriter writer = new FileWriter(filenameRoom);
            String[] columns = new String[] {"roomUid", "data", "time"};
            mappingStrategyRoom.setColumnMapping(columns);
            StatefulBeanToCsvBuilder<Room> builder = new StatefulBeanToCsvBuilder(writer);
            StatefulBeanToCsv beanWriter = builder.withMappingStrategy(mappingStrategyProtocol).build();

            beanWriter.write(room);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
