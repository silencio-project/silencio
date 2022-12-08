package de.dhbw.silencio.ui.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
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

    public ArrayList<Protocol> getAllProtocols(){
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(filenameProtocol));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String[] columns = new String[] {"roomUid", "data", "time"};
        mappingStrategyProtocol.setColumnMapping(columns);

        CsvToBean csvToBean = new CsvToBean();

        ArrayList<Protocol> protocolList = (ArrayList<Protocol>) csvToBean.parse(mappingStrategyProtocol, csvReader);
        return protocolList;
    }

    public ArrayList<Room> getAllRooms(){
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(filenameRoom));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String[] columns = new String[] {"uid", "description", "length", "width", "deskDistanceToBoard", "deskDistanceToWallLeft", "distanceFirstRowToDesk", "tableLength", "tableWidth", "rows", "tablesPerRow", "betweenRows", "distanceToWallLeft"};
        mappingStrategyRoom.setColumnMapping(columns);

        CsvToBean csvToBean = new CsvToBean();

        ArrayList<Room> roomList = (ArrayList<Room>) csvToBean.parse(mappingStrategyRoom, csvReader);
        return roomList;
    }


    //TODO exceptionhandling
    public void storeProtocol(Protocol protocol){
        try {
            FileWriter writer = new FileWriter(filenameProtocol, true);
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
            FileWriter writer = new FileWriter(filenameRoom, true);
            String[] columns = new String[] {"uid", "description", "length", "width", "deskDistanceToBoard", "deskDistanceToWallLeft", "distanceFirstRowToDesk", "tableLength", "tableWidth", "rows", "tablesPerRow", "betweenRows", "distanceToWallLeft"};
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
