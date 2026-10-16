# Smart Traffic Light System

A Java console application that simulates vehicles waiting and moving through
four traffic lanes. The project demonstrates custom data structures and basic
traffic-processing logic.

## Features

- Add vehicles to lanes **A**, **B**, **C**, or **D**
- Choose vehicle types: Car, Truck, Motorcycle, Bus, or Van
- Process vehicles from the busiest lane
- Process multiple vehicles at once
- Undo the last processed vehicle
- View current lanes and processed history
- View session statistics by lane and vehicle type
- Simulate 1–50 random vehicle arrivals

## How It Works

Each lane uses a custom FIFO queue. When a vehicle is processed, it is:

1. Removed from the busiest lane
2. Added to the undo stack
3. Added to the processed history
4. Counted in the session statistics

Undo restores the most recently processed vehicle to the front of its original
lane. If multiple lanes have the same largest queue, the first lane in the
order A, B, C, D is selected.

## Project Structure

```text
src/
├── Main.java                  # Application entry point
├── model/Vehicle.java         # Vehicle data model
├── system/TrafficSystem.java  # Traffic-processing logic
├── ui/MenuUI.java             # Console menu and input handling
├── datastructures/
│   ├── VehicleQueue.java      # FIFO queue for lanes
│   ├── VehicleStack.java      # LIFO stack for undo
│   └── HistoryList.java       # Doubly linked processed-history list
└── utils/
    ├── ConsoleColors.java     # Terminal formatting
    └── Statistics.java        # Session statistics
```

## Technology

- Java 17 or later
- Standard Java library only
- No database, web API, authentication, or external services
- All data is stored in memory and is lost when the program exits

## Compile and Run

From the directory containing `src/`:

```bash
mkdir -p out
javac -d out -sourcepath src src/Main.java
java -cp out Main
```

Choose an option from the menu and follow the prompts. Select `0` to exit.

## Notes

- Vehicle IDs start at 1 each time the application starts.
- Vehicle arrival times are recorded when vehicles are created.
- The generated `out/` directory is ignored by Git.
