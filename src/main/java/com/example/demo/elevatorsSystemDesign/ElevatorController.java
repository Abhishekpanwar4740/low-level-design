package com.example.demo.elevatorsSystemDesign;


import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class ElevatorController {
    Elevator elevator;
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    Queue<Integer> pendingRequests = new LinkedList<>();

    public ElevatorController(Elevator elevator) {
        this.elevator = elevator;
    }

    public synchronized void acceptNewRequest(int floor, Direction direction) {
        if (direction.equals(Direction.DOWN) && !elevator.direction.equals(direction)) {
            minHeap.add(floor);
        } else if (direction.equals(Direction.UP) && !elevator.direction.equals(direction)) {
            maxHeap.add(floor);
        } else if (direction.equals(Direction.DOWN) && floor <= elevator.getCurrentFloor()) {
            maxHeap.add(floor);
        } else if (direction.equals(Direction.DOWN)) {
            pendingRequests.add(floor);
        } else if (direction.equals(Direction.UP) && floor >= elevator.getCurrentFloor()) {
            minHeap.add(floor);
        } else if (direction.equals(Direction.UP)) {
            pendingRequests.add(floor);
        }
    }

    public synchronized void run() {
        if (!minHeap.isEmpty() || !maxHeap.isEmpty()) {
            elevator.setStatus(Status.MOVING);
            processRequests();
        } else {
            elevator.setStatus(Status.STATIC);
        }
    }

    public synchronized void processRequests() {
        if (elevator.direction.equals(Direction.UP)) {
            while (!minHeap.isEmpty()) {
                Integer floor = minHeap.peek();
                minHeap.remove();
                elevator.move(floor, elevator.direction);
            }
            while (!pendingRequests.isEmpty()) {
                Integer floor = pendingRequests.peek();
                pendingRequests.poll();
                minHeap.add(floor);
            }
            elevator.setDirection(Direction.DOWN);
        } else {
            while (!maxHeap.isEmpty()) {
                Integer floor = maxHeap.peek();
                maxHeap.remove();
                elevator.move(floor, elevator.direction);
            }
            while (!pendingRequests.isEmpty()) {
                Integer floor = pendingRequests.peek();
                pendingRequests.poll();
                maxHeap.add(floor);
            }
            elevator.setDirection(Direction.UP);
        }
    }

}
