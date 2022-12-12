package de.dhbw.silencio.storage;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class Room {

    private int uid;
    private String description;
    private int length;
    private int width;
    private int deskDistanceToBoard;
    private int deskDistanceToWallLeft;
    private int distanceFirstRowToDesk;
    private int tableLength;
    private int tableWidth;
    private int rows;
    private int tablesPerRow;
    private int betweenRows;
    private int distanceToWallLeft;

    public Room(){}

    public Room(int length, int width, int deskDistanceToBoard, int distanceFirstRowToDesk, int tableLength, int tableWidth, int rows, int tablesPerRow, int betweenRows, int deskDistanceToWallLeft, int distanceToWallLeft, String description) {
        this.length = length;
        this.width = width;
        this.deskDistanceToBoard = deskDistanceToBoard;
        this.distanceFirstRowToDesk = distanceFirstRowToDesk;
        this.tableLength = tableLength;
        this.tableWidth = tableWidth;
        this.rows = rows;
        this.tablesPerRow = tablesPerRow;
        this.betweenRows = betweenRows;
        this.deskDistanceToWallLeft = deskDistanceToWallLeft;
        this.distanceToWallLeft = distanceToWallLeft;
        this.description = description;
    }

    public Room(int length, int width, int deskDistanceToBoard, int distanceFirstRowToDesk, int tableLength, int tableWidth, int rows, int tablesPerRow, int betweenRows, int deskDistanceToWallLeft, int distanceToWallLeft, String description, int uid) {
        this.length = length;
        this.width = width;
        this.deskDistanceToBoard = deskDistanceToBoard;
        this.distanceFirstRowToDesk = distanceFirstRowToDesk;
        this.tableLength = tableLength;
        this.tableWidth = tableWidth;
        this.rows = rows;
        this.tablesPerRow = tablesPerRow;
        this.betweenRows = betweenRows;
        this.deskDistanceToWallLeft = deskDistanceToWallLeft;
        this.distanceToWallLeft = distanceToWallLeft;
        this.description = description;
        this.uid = uid;
    }

    public static Room emptyRoom() {
        return new Room(1000, 700, 0, 0, 0, 0, 0, 0, 0, 0, 0, "empty");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDistanceDeskToBoard() {
        return deskDistanceToBoard;
    }

    public void setDistanceDeskToBoard(int distanceToBoard) {
        this.deskDistanceToBoard = distanceToBoard;
    }

    public int getTableLength() {
        return tableLength;
    }

    public void setTableLength(int tableLength) {
        this.tableLength = tableLength;
    }

    public int getTableWidth() {
        return tableWidth;
    }

    public void setTableWidth(int tableWidth) {
        this.tableWidth = tableWidth;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTablesPerRow() {
        return tablesPerRow;
    }

    public void setTablesPerRow(int tablesPerRow) {
        this.tablesPerRow = tablesPerRow;
    }

    public int getBetweenRows() {
        return betweenRows;
    }

    public void setBetweenRows(int betweenRows) {
        this.betweenRows = betweenRows;
    }

    public int getDistanceToWallLeft() {
        return distanceToWallLeft;
    }

    public void setDistanceToWallLeft(int distanceToWallLeft) {
        this.distanceToWallLeft = distanceToWallLeft;
    }

    public int getDistanceFirstRowToDesk() {
        return distanceFirstRowToDesk;
    }

    public void setDistanceFirstRowToDesk(int distanceDeskToBoard) {
        this.distanceFirstRowToDesk = distanceDeskToBoard;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getDeskDistanceToBoard() {
        return deskDistanceToBoard;
    }

    public void setDeskDistanceToBoard(int deskDistanceToBoard) {
        this.deskDistanceToBoard = deskDistanceToBoard;
    }

    public int getDeskDistanceToWallLeft() {
        return deskDistanceToWallLeft;
    }

    public void setDeskDistanceToWallLeft(int deskDistanceToWallLeft) {
        this.deskDistanceToWallLeft = deskDistanceToWallLeft;
    }
}
