import system.TrafficSystem;
import ui.MenuUI;

/**
 * Entry point for the Smart Traffic Light System.
 */
public class Main {
    public static void main(String[] args) {
        TrafficSystem system = new TrafficSystem();
        MenuUI        ui     = new MenuUI(system);
        ui.start();
    }
}
