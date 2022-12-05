package ui.data;

import java.time.LocalDateTime;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class Protocol {
    private int roomUid;
    /**
     * The first array holds for each frame an array,which provides on
     * index 0 the timestamp and on
     * index 1 the angle of the pointer.
     */
    private int[][] data;
    private LocalDateTime time;

    public Protocol(int roomUid, int[][] data, LocalDateTime time) {
        this.roomUid = roomUid;
        this.data = data;
        this.time = time;
    }

    public int getRoomUid() {
        return roomUid;
    }

    public void setRoomUid(int roomUid) {
        this.roomUid = roomUid;
    }

    public int[][] getData() {
        return data;
    }

    public void setData(int[][] data) {
        this.data = data;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
