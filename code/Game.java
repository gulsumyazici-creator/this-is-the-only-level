/**
 * The Game class controls the flow of the entire game.
 * It manages stages, player movement, stage transitions, and end-game behavior.
 *
 * Name: Gülsüm Yazıcı
 * Student ID: 2023400072
 */

import java.awt.*;
import java.util.ArrayList;

public class Game {
    public static int stageIndex=0;
    private ArrayList<Stage> stages;
    public static int deathNumber = -1;
    public static long startTime = 0;
    public static String elapsedTime = "";
    public static boolean isGameOver = false;
    public static boolean showClue= true;

    /**
     * Constructs a Game object with the given list of stages.
     *
     * @param stages List of stages for the game.
     */
    public Game(ArrayList<Stage> stages) {
        this.stages = stages;

    }

    /**
     * Sets the formatted elapsed time string.
     *
     * @param elapsedTimeFormatted The formatted time string.
     */
    public static void setElapsedTime(String elapsedTimeFormatted){
        elapsedTime = elapsedTimeFormatted;
    }

    /**
     * Sets the total number of deaths.
     *
     * @param totalDeathNumber The number of player deaths.
     */
    public static void setDeathNumber(int totalDeathNumber) {
        deathNumber = totalDeathNumber;
    }

    /**
     * Sets the game over state.
     *
     * @param gameOver True if the game is over, false otherwise.
     */
    public static void setIsGameOver(boolean gameOver){
        isGameOver = gameOver ;
    }

    /**
     * Gets the current game over state.
     *
     * @return True if the game is over, false otherwise.
     */
    public static boolean getGameOver(){
        return isGameOver;
    }


    /**
     * Starts and runs the main game loop.
     * Initializes the player and map, manages stage transitions,
     * handles input, and displays game over screens.
     */
    public void play() {
        Player player = new Player(115, 450);

        player.setX(130);
        player.setY(140);

        Map map = new Map(stages.get(stageIndex), player);

        while (isGameOver == false) {
            map.updatePhysics();
            map.draw();
            handleInput(map);

            if (map.changeStage()) {
                if (stageIndex != 4) {
                    StdDraw.setPenColor(StdDraw.GREEN);
                    StdDraw.filledRectangle(400, 260, 400, 80);
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.setFont(new Font("Arial", Font.PLAIN, 30));
                    StdDraw.text(400, 285, "You passed the stage");
                    StdDraw.text(400, 240, "But is the level over?!");
                    StdDraw.show();
                    StdDraw.pause(3000);
                    startTime += 3000;
                }
                stageIndex++;
                showClue = true;

                if (stageIndex >= stages.size()) {
                    break;
                }
                // Reset player position and state for the next stage
                player.setX(115);
                player.setY(450);
                player.setVelocityY(0);
                player.setOnGround(false);
                map = new Map(stages.get(stageIndex), player);
            }

            StdDraw.pause(20);
        }

        // Game over screen and restart options
        while (true) {
            StdDraw.clear(StdDraw.WHITE);
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledRectangle(400, 260, 400, 80);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Arial", Font.PLAIN, 28));
            StdDraw.text(400, 300, "CONGRATULATIONS YOU FINISHED THE LEVEL");
            StdDraw.text(400, 260, "PRESS A TO PLAY AGAIN");
            StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
            StdDraw.text(400, 230, "You finished with " + deathNumber + " deaths in " + elapsedTime);
            StdDraw.show();

            if (StdDraw.isKeyPressed('A')) {
                stageIndex = 0;
                isGameOver = false;
                deathNumber = -1;
                startTime = System.currentTimeMillis();
                play();
                break;
            }
            if (StdDraw.isKeyPressed('Q')) {
                System.exit(0);
            }
            StdDraw.pause(20);
        }
    }

    /**
     * Handles player input for movement, jumping, and mouse interactions
     * with on-screen buttons such as help, restart, and reset game.
     *
     * @param map The current map instance.
     */
    private void handleInput(Map map) {
        int[] keyCodes = stages.get(stageIndex).getKeyCodes();
        if (StdDraw.isKeyPressed(keyCodes[0])) { // Sağ tuş
            map.movePlayer('R'); // Move right
        }
        if (StdDraw.isKeyPressed(keyCodes[1])) { // Sol tuş
            map.movePlayer('L'); // Move left
        }
        if (keyCodes[2] != -1 && StdDraw.isKeyPressed(keyCodes[2]) && map.getPlayer().getOnGround()) {
            map.jumpPlayer(); // Jump
        }
        if (Game.getStageIndex() == 4 && StdDraw.isMousePressed()) {
            map.movePlayer('M'); // Special stage mouse move
        }
        if (StdDraw.isMousePressed()) {
            double mx = StdDraw.mouseX();
            double my = StdDraw.mouseY();

            // Help button toggle
            if (mx >= 210 && mx <= 290 && my >= 70 && my <= 100) {
                showClue = !showClue;
                StdDraw.pause(200);
            }

            // Restart current stage
            if (mx >= 510 && mx <= 590 && my >= 70 && my <= 100) {
                map.restartStage();
                StdDraw.pause(200);
            }

            // Reset the entire game
            if (mx >= 320 && mx <= 480 && my >= 5 && my <= 35) {
                Game.stageIndex = 0;
                Game.setIsGameOver(false);
                Game.setDeathNumber(-1);
                Game.startTime = System.currentTimeMillis();
                play();
            }
        }
    }
   public static int getStageIndex() {return stageIndex;} // Return the current stage index
}

