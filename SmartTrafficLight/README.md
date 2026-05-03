# 🚦 Smart Traffic Light System

A Java console application demonstrating core **Data Structures** concepts.

## Project Structure

```
SmartTrafficLight/
├── README.md
└── src/
    ├── Main.java                        ← Entry point
    ├── model/
    │   └── Vehicle.java                 ← Vehicle data model (id, lane, type, timestamp)
    ├── datastructures/
    │   ├── VehicleQueue.java            ← Custom generic Queue (FIFO) — one per lane
    │   ├── VehicleStack.java            ← Custom generic Stack (LIFO) — undo support
    │   └── HistoryList.java             ← Custom doubly-linked list — processed log
    ├── system/
    │   └── TrafficSystem.java           ← Core business logic
    ├── ui/
    │   └── MenuUI.java                  ← Console menu & all user I/O
    └── utils/
        ├── ConsoleColors.java           ← ANSI colours & banner helpers
        └── Statistics.java             ← Per-session stats tracker
```

## Data Structures Used

| Structure | Class | Purpose |
|-----------|-------|---------|
| Queue | `VehicleQueue<T>` | Holds waiting vehicles per lane (FIFO) |
| Stack | `VehicleStack<T>` | Enables undo of last processed vehicle (LIFO) |
| Doubly-Linked List | `HistoryList<T>` | Ordered log of all processed vehicles |

All three are **custom implementations** — no `java.util` collection is used for them.

## Features

1. **Add Vehicle** — choose lane (A/B/C/D) and vehicle type (Car/Truck/Bus/Van/Motorcycle)
2. **Process Traffic** — auto-selects the busiest lane and dequeues one vehicle
3. **Process Multiple** — process N vehicles in sequence
4. **Undo** — pop from stack and restore vehicle to front of its lane
5. **Display Lanes** — iterate over all queues with colour-coded output
6. **Display History** — **recursive** traversal of the linked list log
7. **Statistics** — per-lane and per-type breakdown with ASCII bar chart
8. **Simulate** — random vehicle generation for demo / testing
9. **Reset ID Counter** — restart vehicle numbering

## How to Compile & Run

```bash
# Compile
javac -d out -sourcepath src src/Main.java

# Run
java -cp out Main
```

Requires Java 17+ (uses switch expressions and text blocks).
