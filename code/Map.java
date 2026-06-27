/**
 * The Map class represents the game's map, including all obstacles, pipes, doors, buttons, and spikes.
 * It handles collision detection, drawing of map elements, and interaction logic such as button presses and stage transitions.
 *
 * Name Surname: Gülsüm Yazıcı
 * Student ID: 2023400072
 */

import java.awt.*;

public class Map {
    private Stage stage;
    private Player player;

    // Array of rectangular obstacles defined by their [x1, y1, x2, y2] coordinates
    private int[][] obstacles = {
    new int[]{0, 120, 120, 270}, new int[]{0, 270, 168, 330},
    new int[]{0, 330, 30, 480}, new int[]{0, 480, 180, 600},
    new int[]{180, 570, 680, 600}, new int[]{270, 540, 300, 570},
    new int[]{590, 540, 620, 570}, new int[]{680, 510, 800, 600},
    new int[]{710, 450, 800, 510}, new int[]{740, 420, 800, 450},
    new int[]{770, 300, 800, 420}, new int[]{680, 240, 800, 300},
    new int[]{680, 300, 710, 330}, new int[]{770, 180, 800, 240},
    new int[]{0, 120, 800, 150}, new int[]{560, 150, 800, 180},
    new int[]{530, 180, 590, 210}, new int[]{530, 210, 560, 240},
    new int[]{320, 150, 440, 210}, new int[]{350, 210, 440, 270},
    new int[]{220, 270, 310, 300}, new int[]{360, 360, 480, 390},
    new int[]{530, 310, 590, 340}, new int[]{560, 400, 620, 430}
    };

    // Button Coordinates
    private int[] button = new int[]{400, 390, 470, 410};

    // Button Floor Coordinates
    private int[] buttonFloor = new int[]{400, 390, 470, 400};

    // Start Pipe Coordinates for Drawing
    private int[][] startPipe = {new int[]{115, 450, 145, 480},
            new int[]{110, 430, 150, 450}};

    // Exit Pipe Coordinates for Drawing
    private int[][] exitPipe = {new int[]{720, 175, 740, 215},
            new int[]{740, 180, 770, 210}};

    // Coordinates of spike areas
    private int[][] spikes = {
            new int[]{30, 333, 50, 423}, new int[]{121, 150, 207, 170},
            new int[]{441, 150, 557, 170}, new int[]{591, 180, 621, 200},
            new int[]{750, 301, 769, 419}, new int[]{680, 490, 710, 510},
            new int[]{401, 550, 521, 570}};

    // Door Coordinates
    private int[] door = new int[]{685, 180, 700, 240};

    private boolean isDoorOpen = false; // Whether the door is currently open
    private double doorCurrentHeight = door[3]- door[1]; // Height of the door
    private int buttonPressNum = 0; // Number of times the button has been pressed
    private boolean isButtonPressed = false; // Whether the button is currently being pressed
    private long lastPressTime = 0; // Last time the button was pressed
    private boolean isButtonTemporarilyWhite = false; // Whether button color is temporarily changed

    /**
     * Constructs a Map with a specific Stage and Player.
     *
     * @param stage  The current stage configuration.
     * @param player The player object in the map.
     */
    public Map(Stage stage, Player player) {
        this.stage = stage;
        this.player = player;
    }

    /**
     * Moves the player based on the given direction.
     *
     * @param direction 'R' for right, 'L' for left, 'M' for mouse teleportation.
     */
    public void movePlayer(char direction) {
        double newX = player.getX();
        double newY= player.getY();
        double speed = stage.getVelocityX();

        if (direction == 'R') {
            newX += speed;
        }
        if (direction == 'L') {
            newX -= speed;
        }
        if (direction == 'M') {
            double mouseX = StdDraw.mouseX();
            double mouseY = StdDraw.mouseY();

                newX= mouseX;
                newY= mouseY;

            StdDraw.pause(70); // Prevent rapid teleportation
        }

        for (int[] obstacle : obstacles) {
            if (checkCollision(newX, player.getY(), obstacle)) {
                return;
            }
        }

        if (newY<120)
            return;

        if (checkCollisionDoor(newX, player.getY(), door)) {
            return;
        }

        player.setX(newX);
        player.setY(newY);
    }

    /**
     * Checks collision with an obstacle.
     *
     * @param nextX    Next X position.
     * @param nextY    Next Y position.
     * @param obstacle Obstacle coordinates.
     * @return True if there's a collision, otherwise false.
     */
    private boolean checkCollision(double nextX, double nextY, int[] obstacle) {
        boolean collision = (nextX + player.getWidth() / 2 > obstacle[0] &&
                nextX - player.getWidth() / 2 < obstacle[2] &&
                nextY - player.getHeight() / 2 < obstacle[3] &&
                nextY + player.getHeight() / 2 > obstacle[1]);

        return collision;
    }
    private boolean collision= true; // For door collision status

    /**
     * Checks collision with the door, unless the door is open.
     *
     * @param nextX Next X position.
     * @param nextY Next Y position.
     * @param door  Door coordinates.
     * @return True if there's a collision, otherwise false.
     */
    private boolean checkCollisionDoor(double nextX, double nextY, int[] door) {
        if (isDoorOpen == false) {
            collision = (nextX + player.getWidth() / 2 > door[0] &&
                    nextX - player.getWidth() / 2 < door[2] &&
                    nextY - player.getHeight() / 2 < door[3] &&
                    nextY + player.getHeight() / 2 > door[1]);
        } else if(isDoorOpen == true) {
            collision = false;
        }
        return collision;
    }

    /**
     * Checks collision with the exit pipe.
     *
     * @param nextX    Next X position.
     * @param nextY    Next Y position.
     * @param exitPipe Exit pipe coordinates.
     * @return True if there's a collision, otherwise false.
     */
    private boolean checkCollisionExitPipe(double nextX, double nextY, int[] exitPipe) {
        boolean collision = (nextX + player.getWidth() / 2 > exitPipe[0] &&
                nextX - player.getWidth() / 2 < exitPipe[2] &&
                nextY - player.getHeight() / 2 < exitPipe[3] &&
                nextY + player.getHeight() / 2 > exitPipe[1]);

        return collision;
    }

    /**
     * Checks if the player has reached the exit pipe and can proceed to the next stage.
     *
     * @return True if player is in the exit pipe area, otherwise false.
     */
    public boolean changeStage() {
        return (player.getX() >= 720 && player.getX() <= 770 &&
                player.getY() >= 175 && player.getY() <= 215);
    }

    /**
     * Increments the button press count, stage-dependent.
     */
    public void pressButton() {
        if (Game.stageIndex != 3 && buttonPressNum<2) {
            buttonPressNum++;
        }else if (Game.stageIndex == 3 && buttonPressNum<6){
            buttonPressNum++;
        }
    }

    /**
     * Resets the current stage, player position, and all interactive elements like buttons and door.
     */
    public void restartStage() {
        int deathNumber= Game.deathNumber+1;
        Game.setDeathNumber(deathNumber);// Game içindeki deathNumber'ı artır

        double centerX = 130;
        double centerY = 440;

        player.setX(centerX);
        player.setY(centerY);
        player.setVelocityY(0);
        player.setOnGround(false);
        buttonPressNum = 0;
        isDoorOpen = false;
    }

    /**
     * Gets the player instance in the map.
     *
     * @return The Player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Renders the current game frame, including UI elements, obstacles, player,
     * pipes, buttons, doors, spikes, and handles visual logic for special interactions.
     */
    public void draw() {
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.WHITE);

        // Check if the player is inside the start pipe (for visibility control)
        boolean isVisible = !isPlayerInsideStartPipe();

        // Draw the bottom UI bar
        StdDraw.setPenColor(new Color(56, 93, 172)); // Color of the area
        StdDraw.filledRectangle(400, 60, 400, 60); // Drawing bottom part
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 15));
        StdDraw.text(250, 85, "Help");
        StdDraw.rectangle(250, 85, 40, 15); // Help button
        StdDraw.text(550, 85, "Restart");
        StdDraw.rectangle(550, 85, 40, 15); // Restart button
        StdDraw.rectangle(400, 20, 80, 15); // Reset button
        StdDraw.text(700, 75, "Deaths: " + Game.deathNumber);
        StdDraw.text(700, 50, "Stage: " + (Game.stageIndex+1));
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 17));
        StdDraw.text(400, 20, "RESET THE GAME");
        StdDraw.text(100, 75, "Level: 1");

        // Show help or clue depending on flag
        if (Game.showClue) {
            StdDraw.text(400, 55, stage.getClue());
            StdDraw.text(400, 85, "Clue:");
        } else{
            StdDraw.text(400, 55,  stage.getHelp());
            StdDraw.text(400, 85, "Help:");
        }

        // Calculate and display elapsed time
        long now = System.currentTimeMillis();
        long elapsed = now - Game.startTime;
        long minutes = (elapsed / 60000) % 60;
        long seconds = (elapsed / 1000) % 60;
        long milliseconds = elapsed % 1000 / 10;

        String elapsedTime = String.format("%02d : %02d : %02d", minutes, seconds, milliseconds);
        Game.setElapsedTime(elapsedTime);
        StdDraw.textLeft(55, 50,  "" + elapsedTime);

        // Draw all obstacles
        for (int[] eachObstacle : obstacles) {
            double xLeftDown = eachObstacle[0];
            double yLeftDown = eachObstacle[1];
            double xRightUp = eachObstacle[2];
            double yRightUp = eachObstacle[3];

            double width = (xRightUp - xLeftDown) / 2;
            double height = (yRightUp - yLeftDown) / 2;
            double centerX = xLeftDown + width;
            double centerY = yLeftDown + height;

            StdDraw.setPenColor(stage.getColor());
            StdDraw.filledRectangle(centerX, centerY, width, height);
        }

        // Press button logic for stage-specific interaction
        if (Game.stageIndex != 3)
            if (player.getX() >= button[0] && player.getX() <= button[2] &&
                    player.getY() >= button[1] && player.getY() <= button[3]) {
                pressButton();
            }

        // Draw and update interaction logic for red button (Stage 3 logic)
        if (buttonPressNum < 5 && Game.stageIndex == 3) {
            boolean isPlayerTouchingButton =
                    player.getX() >= button[0] && player.getX() <= button[2] &&
                            player.getY() >= button[1] && player.getY() <= button[3];

            double xLeftDownButton = button[0];
            double yLeftDownButton = button[1];
            double xRightUpButton = button[2];
            double yRightUpButton = button[3];

            double widthButton = (xRightUpButton - xLeftDownButton) / 2;
            double heightButton = (yRightUpButton - yLeftDownButton) / 2;
            double centerXButton = xLeftDownButton + widthButton;
            double centerYButton = yLeftDownButton + heightButton;

            long currentTime = System.currentTimeMillis();

            if (isPlayerTouchingButton && !isButtonPressed && !isButtonTemporarilyWhite) {
                pressButton(); // pressNum burada artar
                isButtonPressed = true;
                isButtonTemporarilyWhite = true;
                lastPressTime = currentTime;
            }

            if (buttonPressNum >= 5) {
                // Button disappears
            } else if (isButtonTemporarilyWhite && currentTime - lastPressTime < 500) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.filledRectangle(centerXButton, centerYButton, widthButton, heightButton);
            } else {
                isButtonTemporarilyWhite = false;
                isButtonPressed = false;
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.filledRectangle(centerXButton, centerYButton, widthButton, heightButton);
            }
        } else if (buttonPressNum < 1 && Game.stageIndex != 3) {

            double xLeftDownButton = button[0];
            double yLeftDownButton = button[1];
            double xRightUpButton = button[2];
            double yRightUpButton = button[3];

            double widthButton = (xRightUpButton - xLeftDownButton) / 2;
            double heightButton = (yRightUpButton - yLeftDownButton) / 2;
            double centerXButton = xLeftDownButton + widthButton;
            double centerYButton = yLeftDownButton + heightButton;

            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledRectangle(centerXButton, centerYButton, widthButton, heightButton);
        }

        // Draw floor button
        double xLeftDownButtonFloor = buttonFloor[0];
        double yLeftDownButtonFloor = buttonFloor[1];
        double xRightUpButtonFloor = buttonFloor[2];
        double yRightUpButtonFloor = buttonFloor[3];

        double widthButtonFloor = (xRightUpButtonFloor - xLeftDownButtonFloor) / 2;
        double heightButtonFloor = (yRightUpButtonFloor - yLeftDownButtonFloor) / 2;
        double centerXButtonFloor = xLeftDownButtonFloor + widthButtonFloor;
        double centerYButtonFloor = yLeftDownButtonFloor + heightButtonFloor;

        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.filledRectangle(centerXButtonFloor, centerYButtonFloor, widthButtonFloor, heightButtonFloor);

        // Draw the player
        player.draw(isVisible);

        // Draw start and exit pipes
        for (int[] eachStartPipe : startPipe) {

            double xLeftDown = eachStartPipe[0];
            double yLeftDown = eachStartPipe[1];
            double xRightUp = eachStartPipe[2];
            double yRightUp = eachStartPipe[3];

            double width = (xRightUp - xLeftDown) / 2;
            double height = (yRightUp - yLeftDown) / 2;
            double centerX = xLeftDown + width;
            double centerY = yLeftDown + height;

            StdDraw.setPenColor(StdDraw.ORANGE);
            StdDraw.filledRectangle(centerX, centerY, width, height);
        }
        for (int[] eachExitPipe : exitPipe) {

            double xLeftDown = eachExitPipe[0];
            double yLeftDown = eachExitPipe[1];
            double xRightUp = eachExitPipe[2];
            double yRightUp = eachExitPipe[3];

            double width = (xRightUp - xLeftDown) / 2;
            double height = (yRightUp - yLeftDown) / 2;
            double centerX = xLeftDown + width;
            double centerY = yLeftDown + height;

            StdDraw.setPenColor(StdDraw.ORANGE);
            StdDraw.filledRectangle(centerX, centerY, width, height);
        }

        // Draw spikes with directional image
        for (int[] eachSpike : spikes) {

            double xLeftDown = eachSpike[0];
            double yLeftDown = eachSpike[1];
            double xRightUp = eachSpike[2];
            double yRightUp = eachSpike[3];

            double width = xRightUp - xLeftDown;
            double height = yRightUp - yLeftDown;
            double centerX = xLeftDown + width / 2.0;
            double centerY = yLeftDown + height / 2.0;

            if (yRightUp > 500) {
                StdDraw.picture(centerX, centerY, "misc/Spikes.png", width, height + 4, 180);
                // Yan spike (yatay)
            } else if (yRightUp <= 200) {
                StdDraw.picture(centerX, centerY, "misc/Spikes.png", width, height + 4);  // Ters spike (baş aşağı)
            } else if (xLeftDown <= 30) {
                StdDraw.picture(centerX, centerY, "misc/Spikes.png", height + 4, width, 270);
            } else if (xRightUp >= 769) {
                StdDraw.picture(centerX, centerY, "misc/Spikes.png", height + 5, width, 90);
            }
        }

        // Draw the door based on current state
        if (buttonPressNum < 1 && Game.stageIndex != 3) {
            double xLeftDownDoor = door[0];
            double yLeftDownDoor = door[1];
            double xRightUpDoor = door[2];
            double yRightUpDoor = door[3];

            double widthDoor = (xRightUpDoor - xLeftDownDoor) / 2.0;
            double heightDoor = (yRightUpDoor - yLeftDownDoor) / 2.0;
            double centerXDoor = xLeftDownDoor + widthDoor;
            double centerYDoor = yLeftDownDoor + heightDoor;

            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledRectangle(centerXDoor, centerYDoor, widthDoor, heightDoor);
        } else if (buttonPressNum < 5 && Game.stageIndex == 3) {

            double xLeftDownDoor = door[0];
            double yLeftDownDoor = door[1];
            double xRightUpDoor = door[2];
            double yRightUpDoor = door[3];

            double widthDoor = (xRightUpDoor - xLeftDownDoor) / 2.0;
            double heightDoor = (yRightUpDoor - yLeftDownDoor) / 2.0;
            double centerXDoor = xLeftDownDoor + widthDoor;
            double centerYDoor = yLeftDownDoor + heightDoor;

            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledRectangle(centerXDoor, centerYDoor, widthDoor, heightDoor);

        } else if (isDoorOpen == false) {

            if ( doorCurrentHeight<=0)
                isDoorOpen= true;

            double xLeftDownDoor = door[0];
            double xRightUpDoor = door[2];

            double widthDoor = (xRightUpDoor - xLeftDownDoor) / 2.0;
            double centerXDoor = xLeftDownDoor + widthDoor;

            if (buttonPressNum >= (Game.stageIndex == 3 ? 5 : 1)) {

                if (doorCurrentHeight > 0) {
                    doorCurrentHeight -= 1;
                }
            }
            double centerYDoor = door[1] + doorCurrentHeight / 2.0;

            if (doorCurrentHeight >0 )
                StdDraw.setPenColor(StdDraw.GREEN);
                StdDraw.filledRectangle(centerXDoor, centerYDoor, widthDoor, doorCurrentHeight / 2.0);
            }
        // Check win condition (Stage 4)
        if (Game.getStageIndex() == 4 )
            for (int[] exitP : exitPipe) {
                if (checkCollisionExitPipe(player.getX(), player.getY(), exitP)) {
                    Game.setIsGameOver(true);
                }
            }
        StdDraw.show();

    }

    /**
     * Updates the physics of the player including gravity, obstacle collisions,
     * and interaction with spikes.
     */
    public void updatePhysics() {
        double velocityY = player.getVelocityY();
        double newY = player.getY() + velocityY;
        boolean onGround = false;

        // Collision with obstacles
        for (int[] obstacle : obstacles) {
            if (checkCollision(player.getX(), newY, obstacle)) {
                if (velocityY < 0) { // Düşerken engelle çarpıştıysa
                    newY = obstacle[3] + player.getHeight() / 2; // Engelin tam üstüne koy
                    velocityY = 0;
                    onGround = true;
                    break;
                } else if (velocityY > 0) { // Tavana çarptıysa
                    newY = obstacle[1] - player.getHeight() / 2; // Tavana yapışmasını engelle
                    velocityY = 0;
                }
            }
        }

        // Apply gravity based on whether the player is grounded
        if (onGround && Game.stageIndex != 2) {
            velocityY = 0;
        } else if (onGround && Game.stageIndex == 2) {
            velocityY = stage.getVelocityY();
        }else {
            velocityY += stage.getGravity(); // Sadece havadaysa gravity uygula
        }

        // Spike collision check
        for (int[] spike : spikes) {
            if (checkCollision(player.getX(), newY, spike)) {
                restartStage();
                return;
            }
        }

        player.setY(newY);
        player.setVelocityY(velocityY);
        player.setOnGround(onGround);

    }

    /**
     * Causes the player to jump if on the ground.
     */
    public void jumpPlayer() {
        if (player.getOnGround()) {
            player.setVelocityY(stage.getVelocityY()); // Sabit zıplama kuvveti uygula
            player.setOnGround(false);
        }
    }

    /**
     * Checks if the player is currently inside the start pipe.
     *
     * @return true if the player is inside the start pipe, false otherwise.
     */
    private boolean isPlayerInsideStartPipe() {
        for (int[] pipe : startPipe) {
            if (player.getX() >= pipe[0] && player.getX() <= pipe[2] &&
                    player.getY() >= pipe[1] && player.getY() <= pipe[3]) {
                return true;
            }
        }
        return false;
    }
}
