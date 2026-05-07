package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks per-lane and per-type statistics for reporting.
 * Per-session stats tracker.
 */
public class Statistics {

    private final Map<String, Integer> processedPerLane = new HashMap<>();
    private final Map<String, Integer> processedPerType = new HashMap<>();
    private int totalProcessed = 0;
    private int totalUndone    = 0;

    public void recordProcessed(String lane, String type) {
        processedPerLane.merge(lane, 1, Integer::sum);
        processedPerType.merge(type, 1, Integer::sum);
        totalProcessed++;
    }

    public void recordUndo() { totalUndone++; }

    public void print() {
        System.out.println(ConsoleColors.BOLD + ConsoleColors.CYAN);
        System.out.println("  ┌─────────────────── SESSION STATISTICS ───────────────────┐");
        System.out.printf("  │  Total Processed : %-5d   Total Undone : %-5d          │%n",
                totalProcessed, totalUndone);
        System.out.println("  ├──────────────────────────────────────────────────────────┤");
        System.out.println("  │  Vehicles Processed Per Lane:                            │");
        for (String lane : new String[]{"A", "B", "C", "D"}) {
            int count = processedPerLane.getOrDefault(lane, 0);
            System.out.printf("  │    Lane %-1s : %3d  %s%n", lane, count,
                    "█".repeat(Math.min(count, 30)) + " ".repeat(Math.max(0, 30 - count)) + "│");
        }
        System.out.println("  ├──────────────────────────────────────────────────────────┤");
        System.out.println("  │  Vehicles Processed Per Type:                            │");
        processedPerType.forEach((type, cnt) ->
                System.out.printf("  │    %-12s : %3d                                  │%n", type, cnt));
        System.out.println("  └──────────────────────────────────────────────────────────┘");
        System.out.print(ConsoleColors.RESET);
    }
}
