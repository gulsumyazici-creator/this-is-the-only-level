/**
 * Represents a game stage with specific properties such as gravity, movement speeds,
 * key bindings, hints, and a unique background color.
 *
 * Name: Gülsüm Yazıcı
 * Student ID: 2023400072
 */

import java.awt.Color;

public class Stage {
    private int stageNumber;
    private double gravity;
    private double velocityX;
    private double velocityY;
    private int rightCode;
    private int leftCode;
    private int upCode;
    private String clue;
    private String help;
    private Color color;

    /**
     * Constructs a new Stage object with given gameplay and UI attributes.
     *
     * @param gravity     The gravity value applied in this stage.
     * @param velocityX   The horizontal movement speed.
     * @param velocityY   The vertical movement speed (e.g. jump strength).
     * @param stageNumber The identifier for this stage.
     * @param rightCode   Key code for moving right.
     * @param leftCode    Key code for moving left.
     * @param upCode      Key code for jumping.
     * @param clue        A clue to assist the player.
     * @param help        Help text or hint for the stage.
     */
    public Stage(double gravity, double velocityX, double velocityY, int stageNumber,
                 int rightCode, int leftCode, int upCode, String clue, String help) {
        this.gravity = gravity;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.stageNumber = stageNumber;
        this.rightCode = rightCode;
        this.leftCode = leftCode;
        this.upCode = upCode;
        this.clue = clue;
        this.help = help;
        this.color = new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256));
    }

    /**
     * Gets the gravity value of the stage.
     *
     * @return The gravity.
     */
    public double getGravity() { return gravity; }

    /**
     * Gets the horizontal movement speed in this stage.
     *
     * @return The horizontal velocity.
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * Gets the vertical movement speed (e.g., jump strength).
     *
     * @return The vertical velocity.
     */
    public double getVelocityY() { return this.velocityY; }

    /**
     * Gets the key codes used for movement in this stage.
     *
     * @return An array containing right, left, and up key codes.
     */
    public int[] getKeyCodes() { return new int[]{rightCode, leftCode, upCode}; }

    /**
     * Gets the clue provided for this stage.
     *
     * @return The clue string.
     */
    public String getClue() { return this.clue; }

    /**
     * Gets the help or hint text for this stage.
     *
     * @return The help string.
     */
    public String getHelp() { return this.help; }

    /**
     * Gets the background color of the stage.
     *
     * @return A randomly generated color unique to this stage.
     */
    public Color getColor() { return this.color; }
}
