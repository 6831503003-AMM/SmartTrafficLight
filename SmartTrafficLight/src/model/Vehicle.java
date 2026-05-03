package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a vehicle in the Smart Traffic Light System.
 */
public class Vehicle {

    private static int idCounter = 1;

    private final int id;
    private final String lane;
    private final String type;
    private final LocalDateTime arrivalTime;

    public static final String[] VEHICLE_TYPES = {"Car", "Truck", "Motorcycle", "Bus", "Van"};

    public Vehicle(String lane, String type) {
        this.id        = idCounter++;
        this.lane      = lane.toUpperCase();
        this.type      = type;
        this.arrivalTime = LocalDateTime.now();
    }

    // ── Getters ─────────────────────────────────────────────────────────────
    public int    getId()          { return id; }
    public String getLane()        { return lane; }
    public String getType()        { return type; }
    public LocalDateTime getArrivalTime() { return arrivalTime; }

    /** Reset the global ID counter (useful for tests / fresh sessions). */
    public static void resetCounter() { idCounter = 1; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("[V%03d | %-10s | Lane %-1s | %s]",
                id, type, lane, arrivalTime.format(fmt));
    }
}
