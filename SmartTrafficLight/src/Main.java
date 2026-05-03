import system.TrafficSystem;
import ui.MenuUI;

/**
 * Entry point for the Smart Traffic Light System.
 *
 * Project structure:
 *   src/
 *     Main.java                      ← entry point
 *     model/
 *       Vehicle.java                 ← data model
 *     datastructures/
 *       VehicleQueue.java            ← custom generic Queue
 *       VehicleStack.java            ← custom generic Stack (undo)
 *       HistoryList.java             ← custom doubly-linked list (history)
 *     system/
 *       TrafficSystem.java           ← core business logic
 *     ui/
 *       MenuUI.java                  ← console menu & I/O
 *     utils/
 *       ConsoleColors.java           ← ANSI colours & formatting
 *       Statistics.java              ← per-session stats tracker
 */
public class Main {
    public static void main(String[] args) {
        TrafficSystem system = new TrafficSystem();
        MenuUI        ui     = new MenuUI(system);
        ui.start();
    }
}
