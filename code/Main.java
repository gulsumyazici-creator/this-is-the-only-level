/**
 * Main class for running the platformer-style game developed by Gülsüm Yazıcı.
 * This class initializes different game stages with unique controls and mechanics,
 * sets up the canvas using StdDraw, and starts the game.
 *
 * Name Surname: Gülsüm Yazıcı
 */

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GulsumYazici {
    public static void main(String[] args) {

        // Represents a non-functional button (used when no key is needed)
        int nullButton = -1;

        // Initialize game stages with different control schemes and gameplay mechanics

        /**
         * Stage 1: Standard stage using arrow keys
         * Controls: Right, Left, Up
         * Description: Arrow keys are required to move, press the button, and enter the pipe
         */
        Stage s1 = new Stage(-0.45, 3.65, 10, 1, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, "Arrow keys are required", "Arrow keys move player, press button and enter the second pipe");

        /**
         * Stage 2: Reversed controls
         * Controls: Left and Right keys are swapped
         * Description: Right and left buttons are reversed
         */
        Stage s2 = new Stage(-0.45, 3.65, 10, 2, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, "Not always straight forward", "Right and left buttons reversed");

        /**
         * Stage 3: Auto-jumping stage
         * Controls: No jump key; jump is automatic
         * Description: Player jumps constantly
         */
        Stage s3 = new Stage(-2, 3.65, 22, 3, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, nullButton, "A bit bouncy here", "You jump constantly");

        /**
         * Stage 4: Button pressing challenge
         * Controls: Standard arrow keys
         * Description: Requires pressing the button 5 times
         */
        Stage s4 = new Stage(-0.45, 3.65, 10, 4, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, "Never gonna give you up", "Press button 5 times");

        /**
         * Stage 5: Mouse-controlled teleportation
         * Controls: No keyboard input; uses mouse clicks
         * Description: Teleport with mouse click, door doesn't need to open
         */
        Stage s5 = new Stage(-0.45, 3.65, 10, 5, nullButton, nullButton, nullButton, "Alternate control scheme", "You teleport by clicking with the mouse, no need to open the door");

        // Add the stages to the arraylist
        ArrayList<Stage> stages = new ArrayList<Stage>();
        stages.add(s1);
        stages.add(s2);
        stages.add(s3);
        stages.add(s4);
        stages.add(s5);

        // Draw the game area
        StdDraw.setCanvasSize(800, 600);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 600);

        // Initialize and start the game
        Game game = new Game(stages);
        Game.startTime = System.currentTimeMillis();  // Record the start time
        game.play();  // Begin the game loop
    }
}



