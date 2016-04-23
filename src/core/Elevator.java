package core;

import java.util.LinkedList;
import java.util.List;

public class Elevator implements Runnable{
    private int tripCount;
    private String name;
    private ElevatorStatus elevatorStatus;
    private int currentFloor = 1;
    private ElevatorDirection direction = ElevatorDirection.STOP;
    private List<Request> requestList;
    private Request currentRequest;

    public Elevator(String name) {
        this.tripCount = 0;
        this.elevatorStatus = ElevatorStatus.OPERATING;
        this.name = name;
        this.requestList = new LinkedList<>();
    }


    public ElevatorStatus getElevatorStatus() {
        return elevatorStatus;
    }

    public void setElevatorStatus(ElevatorStatus elevatorStatus) {
        this.elevatorStatus = elevatorStatus;
    }

    public boolean canAddRequest() {
        return elevatorStatus.equals(ElevatorStatus.OPERATING) && tripCount + requestList.size() < ElevatorControl.MAINTAIN_ROUND;
    }

    public int getDistanceToTargetFloor(int targetFloor) {
        if (direction.equals(ElevatorDirection.STOP))
            return Math.abs(targetFloor - currentFloor);
        else if (direction.equals(ElevatorDirection.UP))
            return (targetFloor > currentFloor) ? (targetFloor - currentFloor) : (2 * ElevatorControl.TOP - targetFloor - currentFloor);
        else
            return (targetFloor > currentFloor) ? (currentFloor + targetFloor) : (currentFloor - targetFloor);
    }

    public void processRequest() {
        // System.out.println(name + " has arrived floor " + currentFloor);
        if (requestList.isEmpty()) {
            this.direction = ElevatorDirection.STOP;
            currentRequest = null;
            return;
        } else {
            currentRequest = requestList.get(0);
            this.direction = currentFloor>currentRequest.getRequestFloor()?ElevatorDirection.DOWN:ElevatorDirection.UP;
            requestList.remove(0);
            tripCount++;
        }
        //  System.out.println(name + " is moving " + direction);
        if(currentRequest!=null)
            System.out.println(name + " is processing request "+currentRequest);
    }

    public void addRequest(Request request) {
        System.out.println(name + " has picked up request " + request);
        requestList.add(request);
    }

    @Override
    public void run() {
        System.out.println(name + " is up running");
        while (true) {
            try {

                if (tripCount == ElevatorControl.MAINTAIN_ROUND) {
                    System.out.println(name + "is under maintain");
                    elevatorStatus = ElevatorStatus.MAINTAINNING;
                    Thread.sleep(100000);
                    System.out.println(name + "is back to operation");
                    tripCount = 0;
                    elevatorStatus = ElevatorStatus.OPERATING;
                }

                if (!direction.equals(ElevatorDirection.STOP)) {
                    if (currentFloor == ElevatorControl.GROUND)
                        direction = ElevatorDirection.UP;
                    if (currentFloor == ElevatorControl.TOP)
                        direction = ElevatorDirection.DOWN;

                    if (currentFloor == currentRequest.getRequestFloor()) {
                        System.out.println(name + " open door at floor " + currentFloor);
                        System.out.println(name + " close door at floor " + currentFloor);
                        direction = currentRequest.getDirection();
                        currentRequest.setRequestFloorReached(true);
                        System.out.println(name + " got request floor and moving " + currentRequest.getDirection());

                    }

                    // thread to sleep for 1000 milliseconds
                    System.out.println(name + ": is now at floor " + currentFloor + " and is moving " + direction);
                    Thread.sleep(2000);
                    if (direction.equals(ElevatorDirection.UP))
                        currentFloor++;
                    else
                        currentFloor--;

                }

                if (currentRequest == null || (currentRequest.isRequestFloorReached() && currentFloor == currentRequest.getTargetFloor())) {
                    if(currentRequest!=null)
                        System.out.println(name + " has arrived the target floor "+currentFloor + " the trip count is " + tripCount);
                    processRequest();
                }


            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
