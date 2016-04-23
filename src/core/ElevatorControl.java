package core;

import java.util.List;

public class ElevatorControl {
    public static final int MAINTAIN_ROUND = 100;
    public static final int GROUND = 1;
    public static final int TOP = 10;

    private List<Elevator> elevatorList;
    private List<Request> requestList;

    public ElevatorControl(List<Elevator> elevators, List<Request> requests){
        this.elevatorList = elevators;
        this.requestList = requests;
    }

    public void startElevatorSystem(){
        for(Elevator elevator:elevatorList){
            Thread thread = new Thread(elevator);
            thread.start();
        }

        while(true){
            if(!requestList.isEmpty()){
                int shortestPath = Integer.MAX_VALUE;
                Elevator targetElevator = null;
                Request currentRequest = requestList.get(0);
                for(Elevator elevator:elevatorList){
                    if(elevator.canAddRequest() && elevator.getDistanceToTargetFloor(currentRequest.getTargetFloor())<shortestPath){
                        shortestPath = elevator.getDistanceToTargetFloor(currentRequest.getTargetFloor());
                        targetElevator = elevator;
                    }
                }

                if(targetElevator!=null)
                    targetElevator.addRequest(currentRequest);

                requestList.remove(0);

                try{
                    Thread.sleep(3000);
                }catch (Exception e) {
                    System.out.print(e);
                }
            }
        }
    }

    public void addElevator(Elevator elevator){
        elevatorList.add(elevator);
    }

    public void addRequest(Request request){
        requestList.add(request);
    }

}
