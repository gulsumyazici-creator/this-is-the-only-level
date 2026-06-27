# This Is the Only Level - Java Game

This project is an object-oriented Java implementation of the nostalgic platform game **This Is the Only Level**.
It was developed for CmpE 160 Assignment 2 using the **StdDraw** graphics library.

## About the Game

The game looks like a simple platformer: the player controls an elephant and tries to reach the exit pipe.
However, the same level is played multiple times, and each stage introduces a different mechanic or control twist.

The player must avoid spikes, press buttons, open the door, and reach the exit pipe to pass each stage.

## Features

* Object-oriented Java implementation
* Keyboard and mouse-based player controls
* Collision detection with obstacles, spikes, door, and pipes
* Button-based door opening mechanic
* Stage-based gameplay with different rules
* Death counter and game timer
* Help and clue system
* Restart and reset buttons
* Custom elephant and spike images

## Stages

The game includes five stages:

1. **Arrow Keys Are Required**
   Standard movement with arrow keys.

2. **Not Always Straight Forward**
   Left and right controls are reversed.

3. **A Bit Bouncy Here**
   The elephant automatically jumps whenever it touches the ground.

4. **Never Gonna Give You Up**
   The button must be pressed five times before the door opens.

5. **Alternate Control Scheme**
   The player uses mouse clicks to teleport.

## Project Structure

```text
.
├── README.md
├── code
│   ├── Main.java
│   ├── Game.java
│   ├── Map.java
│   ├── Player.java
│   └── Stage.java
└── misc
    ├── ElephantLeft.png
    ├── ElephantRight.png
    └── Spikes.png
```

## Classes

### `Main.java`

The main class of the project. It initializes the stages, sets up the StdDraw canvas, and starts the game.

### `Game.java`

Controls the main game loop. It handles stage transitions, player input, timer, death counter, restart/reset actions, and the end-game screen.

### `Map.java`

Represents the game environment. It draws the map, obstacles, spikes, pipes, door, and button. It also handles collision detection, button presses, door opening, and stage restart logic.

### `Player.java`

Represents the elephant character. It stores the player’s position, velocity, direction, ground state, and draws the correct elephant image depending on movement direction.

### `Stage.java`

Stores the properties of each stage, such as gravity, movement speed, jump velocity, key bindings, clue text, help text, and obstacle color.

## How to Run

Make sure `StdDraw.java` is available and properly included while compiling the project.

From the root directory, compile the Java files:

```bash
javac code/*.java
```

Then run the main class:

```bash
java -cp code Main
```

## Controls

* **Left / Right Arrow Keys:** Move the elephant
* **Up Arrow Key:** Jump, except in special stages
* **Mouse Click:** Used for buttons and the final custom stage
* **A:** Play again after finishing the game
* **Q:** Quit after finishing the game

## Assets

The project uses image assets for the elephant and spikes. These files are stored in the `misc/` folder:

* `ElephantLeft.png`
* `ElephantRight.png`
* `Spikes.png`

## Notes

This project was implemented as a course assignment for CmpE 160.
The main goal was to practice object-oriented programming, basic game logic, keyboard/mouse interaction, collision detection, and simple graphics with StdDraw.
