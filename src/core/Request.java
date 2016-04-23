package core;

public class Request {
    private int targetFloor;
    private int requestFloor;
    private ElevatorDirection direction;
    private boolean requestFloorReached;

    public Request(int targetFloor, int requestFloor, ElevatorDirection direction) {
        this.targetFloor = targetFloor;
        this.direction = direction;
        this.requestFloor = requestFloor;
        this.requestFloorReached = false;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public ElevatorDirection getDirection() {
        return direction;
    }

    public void setDirection(ElevatorDirection direction) {
        this.direction = direction;
    }

    public int getRequestFloor() {
        return requestFloor;
    }

    public void setRequestFloor(int requestFloor) {
        this.requestFloor = requestFloor;
    }


    public boolean isRequestFloorReached() {
        return requestFloorReached;
    }

    public void setRequestFloorReached(boolean requestFloorReached) {
        this.requestFloorReached = requestFloorReached;
    }

    public String toString() {
        return "requestfloor=" + requestFloor + ", targetfloor=" + targetFloor + ", direction=" + direction;
    }
}
