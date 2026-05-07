package utils;

/**
 * ANSI terminal colour codes and banner helpers.
 * This class cannot be inherited(extended).
 */
public final class ConsoleColors {

    private ConsoleColors() {}      // Prevents object creation

    public static final String RESET   = "\u001B[0m";
    public static final String RED     = "\u001B[31m";
    public static final String GREEN   = "\u001B[32m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String BLUE    = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN    = "\u001B[36m";
    public static final String WHITE   = "\u001B[37m";
    public static final String BOLD    = "\u001B[1m";

    // Lane colour mapping
    public static String laneColor(String lane) {
        return switch (lane.toUpperCase()) {
            case "A" -> GREEN;
            case "B" -> BLUE;
            case "C" -> YELLOW;
            case "D" -> MAGENTA;
            default  -> WHITE;
        };
    }

    public static void printBanner() {
        System.out.println(CYAN + BOLD);
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║       🚦  SMART TRAFFIC LIGHT SYSTEM  🚦         ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println(RESET);
    }

    public static void printDivider() {
        System.out.println(CYAN + "─".repeat(52) + RESET);
    }

    public static void success(String msg) {
        System.out.println(GREEN + "  ✔  " + msg + RESET);
    }

    public static void warn(String msg) {
        System.out.println(YELLOW + "  ⚠  " + msg + RESET);
    }

    public static void error(String msg) {
        System.out.println(RED + "  ✘  " + msg + RESET);
    }

    public static void info(String msg) {
        System.out.println(CYAN + "  ℹ  " + msg + RESET);
    }
}
