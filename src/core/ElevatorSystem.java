package core;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ElevatorSystem {
    private final static int MAX_ELEVATOR_NUM = 2;
    private final static int MAX_REQUEST_NUM = 17;

    public static void main(String[] args) {
        List<Elevator> elevators = new LinkedList<>();
        List<Request> requests = new LinkedList<>();
        buildSimulation(elevators, requests);
        for (int i = 0; i < requests.size(); i++)
            System.out.println(requests.get(i));
        ElevatorControl elevatorControl = new ElevatorControl(elevators, requests);

        elevatorControl.startElevatorSystem();


    }

    private static void buildSimulation(List<Elevator> elevators, List<Request> requests) {
        Random randomGenerator = new Random();
        for (int i = 0; i < MAX_ELEVATOR_NUM; i++)
            elevators.add(new Elevator("elevator" + (i + 1)));
        for (int i = 0; i < MAX_REQUEST_NUM; i++) {
            int requestFloor = generateFloor(randomGenerator);
            int targetFloor = generateFloor(randomGenerator);
            Request request = new Request(targetFloor, requestFloor, requestFloor > targetFloor ? ElevatorDirection.DOWN : ElevatorDirection.UP);
            if (isValidateRequst(request))
                requests.add(request);
        }
    }

    private static int generateFloor(Random randomGenerator) {
        int randomInt = randomGenerator.nextInt((ElevatorControl.TOP - ElevatorControl.GROUND) + 1);
        return randomInt + 1;

    }

    private static boolean isValidateRequst(Request request) {
        if (request.getTargetFloor() == ElevatorControl.TOP)
            return request.getDirection().equals(ElevatorDirection.DOWN);
        else if (request.getTargetFloor() == ElevatorControl.GROUND)
            return request.getDirection().equals(ElevatorDirection.UP);
        else if (request.getRequestFloor() == request.getTargetFloor())
            return false;
        else
            return request.getTargetFloor() >= 1 && request.getTargetFloor() <= 9;
    }
}
