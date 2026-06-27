/**
 * Represents the player character in the game.
 * The player has a position, velocity, facing direction, and can interact with the environment
 * such as standing on ground or respawning.
 * It also handles rendering the player using StdDraw.
 *
 * Name: Gülsüm Yazıcı
 * Student ID: 2023400072
 */

public class Player {
    private double x, y;
    private double width = 20;
    private double height = 20;
    private double velocityY;
    private boolean facingRight = true;
    private boolean OnGround = false;

    /**
     * Constructs a new Player object with a starting position.
     *
     * @param x The initial x-coordinate of the player.
     * @param y The initial y-coordinate of the player.
     */
    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the player's x-coordinate.
     *
     * @return The current x-coordinate.
     */
    public double getX() { return x; }

    /**
     * Gets the player's y-coordinate.
     *
     * @return The current y-coordinate.
     */
    public double getY() { return y; }

    /**
     * Sets the player's x-coordinate. Also updates facing direction.
     *
     * @param x The new x-coordinate.
     */
    public void setX(double x) {
        if (x > this.x) {
            facingRight = true;
        } else if (x < this.x) {
            facingRight = false;
        }
        this.x = x;
    }

    /**
     * Sets the player's y-coordinate.
     *
     * @param y The new y-coordinate.
     */
    public void setY(double y) { this.y = y; }

    /**
     * Sets whether the player is currently on the ground.
     *
     * @param OnGround True if the player is on the ground; otherwise, false.
     */
    public void setOnGround(boolean OnGround) {
        this.OnGround= OnGround;
    }

    /**
     * Returns whether the player is currently on the ground.
     *
     * @return True if on the ground; otherwise, false.
     */
    public boolean getOnGround() {
        return this.OnGround;
    }

    /**
     * Gets the player's width.
     *
     * @return The width of the player.
     */
    public double getWidth() { return this.width;}

    /**
     * Gets the player's height.
     *
     * @return The height of the player.
     */
    public double getHeight() { return this.height;}

    /**
     * Sets the vertical velocity of the player.
     *
     * @param velocityY The new vertical velocity.
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * Gets the current vertical velocity of the player.
     *
     * @return The vertical velocity.
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * Draws the player using StdDraw. Skips drawing if the player is invisible.
     * The image changes based on direction and current stage.
     *
     * @param isVisible Determines whether the player should be rendered.
     */
    public void draw(boolean isVisible) {
        if (!isVisible) {
            return;
        }
        if (Game.getStageIndex()== 1){
            if (facingRight) {
                StdDraw.picture(this.x, this.y, "misc/ElephantLeft.png", width, height);
            } else {
                StdDraw.picture(this.x, this.y, "misc/ElephantRight.png", width, height);
            }
        }else {
            if (facingRight) {
                StdDraw.picture(this.x, this.y, "misc/ElephantRight.png", width, height);
            } else {
                StdDraw.picture(this.x, this.y, "misc/ElephantLeft.png", width, height);
            }
        }
    }
}

