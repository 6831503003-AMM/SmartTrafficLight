package ui;

import java.util.Scanner;
import model.Vehicle;
import system.TrafficSystem;
import utils.ConsoleColors;

/**
 * Menu-driven console UI for the Smart Traffic Light System.
 * Separates all I/O concerns from business logic.
 */
public class MenuUI {

    private final TrafficSystem traffic;
    private final Scanner       scanner;

    public MenuUI(TrafficSystem traffic) {
        this.traffic = traffic;
        this.scanner = new Scanner(System.in);
    }

    // ── Main Loop ────────────────────────────────────────────────────────────
    public void start() {
        ConsoleColors.printBanner();
        ConsoleColors.info("Welcome! Use the menu below to manage traffic.");
        System.out.println();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("  Enter choice: ");
            System.out.println();

            switch (choice) {
                case 1  -> handleAddVehicle();
                case 2  -> traffic.processTraffic();
                case 3  -> handleProcessMultiple();
                case 4  -> traffic.undoLastProcessed();
                case 5  -> traffic.displayAllLanes();
                case 6  -> traffic.displayHistory();
                case 7  -> traffic.displayStats();
                case 8  -> handleSimulate();
                case 0  -> { running = false; handleExit(); }
                default -> ConsoleColors.error("Invalid option. Please choose 0–8.");
            }
            System.out.println();
        }
    }

    // ── Menu Rendering ───────────────────────────────────────────────────────
    private void printMenu() {
        System.out.println(ConsoleColors.BOLD + ConsoleColors.CYAN
                + "  ╔══════════════════════════════════════╗"
                + ConsoleColors.RESET);
        menuItem(" 1", "Add Vehicle to Lane");
        menuItem(" 2", "Process Traffic (busiest lane)");
        menuItem(" 3", "Process Multiple Vehicles");
        menuItem(" 4", "Undo Last Processed Vehicle");
        menuItem(" 5", "Display All Lanes");
        menuItem(" 6", "Display Processed History");
        menuItem(" 7", "Show Session Statistics");
        menuItem(" 8", "Simulate Random Traffic");
        menuItem(" 0", "Exit");
        System.out.println(ConsoleColors.BOLD + ConsoleColors.CYAN
                + "  ╚══════════════════════════════════════╝"
                + ConsoleColors.RESET);
        System.out.printf("%n  Vehicles waiting: %s%d%s   |   History size: %s%d%s%n%n",
                ConsoleColors.YELLOW, traffic.totalWaiting(),   ConsoleColors.RESET,
                ConsoleColors.BLUE,   traffic.historySize(), ConsoleColors.RESET);
    }

    private void menuItem(String num, String label) {
        System.out.printf(ConsoleColors.CYAN + "  ║ " + ConsoleColors.RESET
                + ConsoleColors.BOLD + "%s. " + ConsoleColors.RESET
                + "%-33s" + ConsoleColors.CYAN + "║%n" + ConsoleColors.RESET,
                num, label);
    }

    // ── Handlers ─────────────────────────────────────────────────────────────
    private void handleAddVehicle() {
        System.out.println(ConsoleColors.BOLD + "  Add Vehicle" + ConsoleColors.RESET);
        ConsoleColors.printDivider();

        String lane = readLane();
        String type = readVehicleType();
        traffic.addVehicle(lane, type);
    }

    private void handleProcessMultiple() {
        int n = readInt("  How many vehicles to process? ");
        if (n <= 0) { ConsoleColors.error("Must be a positive number."); return; }
        traffic.processMultiple(n);
    }

    private void handleSimulate() {
        int n = readInt("  How many random vehicles to add? (1–50): ");
        if (n < 1 || n > 50) { ConsoleColors.error("Enter a number between 1 and 50."); return; }
        traffic.simulateTraffic(n);
    }

    private void handleExit() {
        System.out.println();
        System.out.println(ConsoleColors.CYAN + ConsoleColors.BOLD
                + "  Thank you for using Smart Traffic Light System. Goodbye! 🚦"
                + ConsoleColors.RESET);
        scanner.close();
    }

    // ── Input Helpers ─────────────────────────────────────────────────────────
    private String readLane() {
        while (true) {
            System.out.print("  Enter lane (A / B / C / D): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.matches("[ABCD]")) return input;
            ConsoleColors.error("Invalid lane. Choose A, B, C, or D.");
        }
    }

    private String readVehicleType() {
        System.out.println("  Vehicle types:");
        String[] types = Vehicle.VEHICLE_TYPES;
        for (int i = 0; i < types.length; i++) {
            System.out.printf("    %d. %s%n", i + 1, types[i]);
        }
        while (true) {
            int choice = readInt("  Select type (1–" + types.length + "): ");
            if (choice >= 1 && choice <= types.length) return types[choice - 1];
            ConsoleColors.error("Invalid selection.");
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                ConsoleColors.error("Please enter a valid integer.");
            }
        }
    }
}
