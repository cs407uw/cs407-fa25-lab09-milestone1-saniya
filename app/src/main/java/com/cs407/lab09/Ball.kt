package com.cs407.lab09

/**
 * Represents a ball that can move. (No Android UI imports!)
 *
 * Constructor parameters:
 * - backgroundWidth: the width of the background, of type Float
 * - backgroundHeight: the height of the background, of type Float
 * - ballSize: the width/height of the ball, of type Float
 */
class Ball(
    private val backgroundWidth: Float,
    private val backgroundHeight: Float,
    private val ballSize: Float
) {
    var posX = 0f
    var posY = 0f
    var velocityX = 0f
    var velocityY = 0f
    private var accX = 0f
    private var accY = 0f

    private var isFirstUpdate = true

    init {
        reset()
    }

    /**
     * Updates the ball's position and velocity based on the given acceleration and time step.
     * (See lab handout for physics equations)
     */
    fun updatePositionAndVelocity(xAcc: Float, yAcc: Float, dT: Float) {
        if(isFirstUpdate) {
            isFirstUpdate = false
            accX = xAcc
            accY = yAcc
            return
        }
        if (dT <= 0f) return

        // Previous state
        val v0x = velocityX
        val v0y = velocityY
        val a0x = accX
        val a0y = accY

        // New acceleration (a1)
        val a1x = xAcc
        val a1y = yAcc

        // velocity 1  @ т1
        val v1x = v0x + 0.5f * (a0x + a1x) * dT
        val v1y = v0y + 0.5f * (a0y + a1y) * dT

        //Distance traveled
        val dt2 = dT * dT
        val dx = v0x * dT + (1f / 6f) * (3f * a0x + a1x) * dt2
        val dy = v0y * dT + (1f / 6f) * (3f * a0y + a1y) * dt2

        // Update position
        posX += dx
        posY += dy

        velocityX = v1x
        velocityY = v1y
        accX = a1x
        accY = a1y

        checkBoundaries()

    }

    /**
     * Ensures the ball does not move outside the boundaries.
     * When it collides, velocity and acceleration perpendicular to the
     * boundary should be set to 0.
     */
    fun checkBoundaries() {
        val maxX = backgroundWidth - ballSize
        val maxY = backgroundHeight - ballSize

        // Left wall
        if (posX < 0f) {
            posX = 0f
            velocityX = 0f
            accX = 0f
        }


        // Right wall
        if (posX > maxX) {
            posX = maxX
            velocityX = 0f
            accX = 0f
        }

        // Top wall
        if (posY < 0f) {
            posY = 0f
            velocityY = 0f
            accY = 0f
        }

        if (posY > maxY) {
            posY = maxY
            velocityY = 0f
            accY = 0f
        }

    }

    /**
     * Resets the ball to the center of the screen with zero
     * velocity and acceleration.
     */
    fun reset() {
        // Center of the field
        posX = (backgroundWidth - ballSize) / 2f
        posY = (backgroundHeight - ballSize) / 2f

        velocityX = 0f
        velocityY = 0f
        accX = 0f
        accY = 0f

        isFirstUpdate = true

    }
}