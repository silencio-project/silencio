package ui.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class _TestData {

    public static ArrayList<Room> rooms() {
        return new ArrayList<>(List.of(
            new Room(1000, 700, 100, 100, 80, 100, 3, 6, 50, 300, 100, "Room 1", 1),
            new Room(1000, 700, 100, 100, 80, 100, 4, 6, 50, 300, 100, "Room 2", 2),
            new Room(1000, 700, 100, 100, 80, 100, 5, 6, 50, 300, 100, "Room 2", 3),
            new Room(1000, 700, 100, 100, 80, 100, 2, 6, 50, 300, 100, "Room 4", 4),
            new Room(1000, 700, 100, 100, 80, 100, 4, 6, 50, 300, 100, "Room 5", 5),
            new Room(1000, 700, 100, 100, 80, 100, 5, 6, 50, 300, 100, "Room 6", 6)
        ));
    }

    public static ArrayList<Protocol> protocols() {
        int[][] data = {{1, 20}, {2, 30}, {3, 40}, {4, 90}, {5, 70}, {6, 45}, {2, 50}, {3, 55}, {4, 60}, {5, 65}, {6, 70}, {2, 75}, {3, 80}, {4, 85}, {5, 90}, {6, 95}, {2, 100}, {3, 105}, {4, 110}, {5, 115}, {6, 120}, {2, 125}, {3, 130}, {4, 135}, {5, 140}, {6, 150}, {2, 160}, {3, 165}, {4, 170}, {5, 175}, {6, 45}, {2, 30}, {3, 40}, {4, 90}, {5, 70}, {6, 45}, {2, 30}, {3, 40}, {4, 90}, {5, 70}, {6, 45}, {2, 30}, {3, 40}, {4, 90}, {5, 70}, {6, 45}, {2, 30}, {3, 40}, {4, 90}, {5, 70}, {6, 45}, {2, 30}, {3, 40}, {4, 90}, {5, 70}, {6, 45}};

        return new ArrayList<>(List.of(

            new Protocol(2, data, LocalDateTime.now().minusDays(2)),
            new Protocol(3, data, LocalDateTime.now().minusDays(1)),
            new Protocol(4, data, LocalDateTime.now())
        ));
    }
}
