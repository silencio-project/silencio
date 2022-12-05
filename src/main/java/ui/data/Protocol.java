package ui.data;

import java.time.LocalDateTime;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class Protocol {
    private int roomUid;
    // [1 frame][timestamp , angle]
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
