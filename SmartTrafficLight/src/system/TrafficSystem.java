package system;

import datastructures.HistoryList;
import datastructures.VehicleQueue;
import datastructures.VehicleStack;
import model.Vehicle;
import utils.ConsoleColors;
import utils.Statistics;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TrafficSystem encapsulates all traffic-management logic:
 *   • Queues  – one per lane (A, B, C, D)
 *   • Stack   – undo last processed vehicle
 *   • HistoryList – full ordered log of processed vehicles
 *   • Statistics  – per-session reporting
 */
public class TrafficSystem {

    // ── Data Structures ─────────────────────────────────────────────────────
    private final Map<String, VehicleQueue<Vehicle>> lanes = new LinkedHashMap<>();
    private final VehicleStack<Vehicle>              undoStack   = new VehicleStack<>();
    private final HistoryList<Vehicle>               history     = new HistoryList<>();
    private final Statistics                         stats       = new Statistics();

    private static final String[] LANE_NAMES = {"A", "B", "C", "D"};

    public TrafficSystem() {
        for (String lane : LANE_NAMES) {
            lanes.put(lane, new VehicleQueue<>());
        }
    }

    // ── 1. Add Vehicle ───────────────────────────────────────────────────────
    /**
     * Enqueues a new vehicle into the specified lane.
     */
    public void addVehicle(String lane, String type) {
        lane = lane.toUpperCase();
        if (!lanes.containsKey(lane)) {
            ConsoleColors.error("Invalid lane '" + lane + "'. Choose A, B, C, or D.");
            return;
        }
        Vehicle v = new Vehicle(lane, type);
        lanes.get(lane).enqueue(v);
        ConsoleColors.success("Vehicle added → " + v);
    }

    // ── 2. Process Traffic ───────────────────────────────────────────────────
    /**
     * Finds the lane with the most vehicles and dequeues one vehicle from it.
     * The processed vehicle is pushed onto the undo stack and appended to history.
     */
    public void processTraffic() {
        String busiestLane = findBusiestLane();
        if (busiestLane == null) {
            ConsoleColors.warn("All lanes are empty. No vehicle to process.");
            return;
        }

        VehicleQueue<Vehicle> queue = lanes.get(busiestLane);
        Vehicle v = queue.dequeue();

        undoStack.push(v);
        history.addLast(v);
        stats.recordProcessed(v.getLane(), v.getType());

        System.out.println(ConsoleColors.GREEN + ConsoleColors.BOLD
                + "  🟢 PROCESSED from Lane " + busiestLane + " (busiest, "
                + (queue.size() + 1) + " vehicles): " + ConsoleColors.RESET);
        System.out.println("     " + v);
    }

    /**
     * Processes N vehicles (one per call through the busiest-lane logic).
     */
    public void processMultiple(int n) {
        for (int i = 0; i < n; i++) {
            System.out.printf("%n  -- Round %d --%n", i + 1);
            processTraffic();
            if (allLanesEmpty()) break;
        }
    }

    // ── 3. Undo Last Processed ───────────────────────────────────────────────
    /**
     * Pops the most-recently-processed vehicle from the undo stack and
     * re-enqueues it at the front of its original lane (via re-build).
     */
    public void undoLastProcessed() {
        if (undoStack.isEmpty()) {
            ConsoleColors.warn("Nothing to undo — undo stack is empty.");
            return;
        }

        Vehicle v = undoStack.pop();
        history.removeLast();              // keep history in sync
        stats.recordUndo();

        // Re-enqueue at front by rebuilding the queue
        VehicleQueue<Vehicle> oldQueue = lanes.get(v.getLane());
        VehicleQueue<Vehicle> newQueue = new VehicleQueue<>();
        newQueue.enqueue(v);              // vehicle goes to front (priority restore)
        for (Vehicle existing : oldQueue) newQueue.enqueue(existing);
        lanes.put(v.getLane(), newQueue);

        ConsoleColors.warn("UNDO successful → " + v + " returned to Lane " + v.getLane());
    }

    // ── 4. Display All Lanes ─────────────────────────────────────────────────
    /**
     * Iterates over every lane and prints queued vehicles.
     */
    public void displayAllLanes() {
        System.out.println();
        ConsoleColors.printDivider();
        System.out.println(ConsoleColors.BOLD + "  🚗  CURRENT LANE STATUS" + ConsoleColors.RESET);
        ConsoleColors.printDivider();

        for (Map.Entry<String, VehicleQueue<Vehicle>> entry : lanes.entrySet()) {
            String lane = entry.getKey();
            VehicleQueue<Vehicle> queue = entry.getValue();
            String color = ConsoleColors.laneColor(lane);

            System.out.printf("%s  Lane %s [%d vehicle(s)]%s%n",
                    color + ConsoleColors.BOLD, lane, queue.size(), ConsoleColors.RESET);

            if (queue.isEmpty()) {
                System.out.println("         (empty)");
            } else {
                int pos = 1;
                for (Vehicle v : queue) {
                    System.out.printf("    %2d. %s%n", pos++, v);
                }
            }
        }
        ConsoleColors.printDivider();
    }

    // ── 5. Display History (recursive) ──────────────────────────────────────
    /**
     * Public entry-point — delegates to the private recursive method.
     */
    public void displayHistory() {
        System.out.println();
        ConsoleColors.printDivider();
        System.out.println(ConsoleColors.BOLD + "  📋  PROCESSED VEHICLE HISTORY" + ConsoleColors.RESET);
        ConsoleColors.printDivider();

        if (history.isEmpty()) {
            ConsoleColors.info("No vehicles have been processed yet.");
        } else {
            displayHistoryRecursive(history.getHead(), 1);
        }
        ConsoleColors.printDivider();
    }

    /**
     * Recursively traverses the HistoryList and prints each entry.
     */
    private void displayHistoryRecursive(HistoryList.Node<Vehicle> node, int index) {
        if (node == null) return;                           // base case
        System.out.printf("    %2d. %s%n", index, node.data);
        displayHistoryRecursive(node.next, index + 1);     // recursive step
    }

    // ── 6. Statistics ────────────────────────────────────────────────────────
    public void displayStats() {
        System.out.println();
        stats.print();
    }

    // ── 7. Simulate Random Traffic ───────────────────────────────────────────
    /**
     * Randomly generates vehicles across all lanes for demo purposes.
     */
    public void simulateTraffic(int count) {
        String[] types = Vehicle.VEHICLE_TYPES;
        java.util.Random rng = new java.util.Random();

        System.out.println();
        ConsoleColors.info("Simulating " + count + " random vehicle arrivals...");
        for (int i = 0; i < count; i++) {
            String lane = LANE_NAMES[rng.nextInt(LANE_NAMES.length)];
            String type = types[rng.nextInt(types.length)];
            addVehicle(lane, type);
        }
        ConsoleColors.success("Simulation complete.");
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private String findBusiestLane() {
        String busiest = null;
        int maxSize = 0;
        for (Map.Entry<String, VehicleQueue<Vehicle>> e : lanes.entrySet()) {
            if (e.getValue().size() > maxSize) {
                maxSize = e.getValue().size();
                busiest = e.getKey();
            }
        }
        return maxSize == 0 ? null : busiest;
    }

    private boolean allLanesEmpty() {
        for (VehicleQueue<Vehicle> q : lanes.values()) {
            if (!q.isEmpty()) return false;
        }
        return true;
    }

    public int totalWaiting() {
        int total = 0;
        for (VehicleQueue<Vehicle> q : lanes.values()) total += q.size();
        return total;
    }

    public boolean hasUndoHistory() { return !undoStack.isEmpty(); }
    public int     historySize()    { return history.size(); }
}
