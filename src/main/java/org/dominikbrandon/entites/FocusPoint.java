package org.dominikbrandon.entites;

import org.dominikbrandon.models.TexturedModel;
import org.dominikbrandon.renderEngine.DisplayManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class FocusPoint extends Entity {
    private static final float HORIZONTAL_SPEED = 20;
    private static final float VERTICAL_SPEED = 10;
    private static final float TURN_SPEED = 160;
    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float currentUpwardsSpeed = 0;

    public FocusPoint(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move() {
        checkInputs();
        super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float rotation = (180 - super.getRotY()) % 360;
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(rotation)));
        float dy = currentUpwardsSpeed * DisplayManager.getFrameTimeSeconds();
        float dz = (float) (distance * Math.cos(Math.toRadians(rotation)));
        super.increasePosition(dx, dy, dz);
    }

    private void checkInputs() {
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            this.currentSpeed = HORIZONTAL_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            this.currentSpeed = -HORIZONTAL_SPEED;
        } else {
            this.currentSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            this.currentTurnSpeed = TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            this.currentUpwardsSpeed = VERTICAL_SPEED;
        } else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
            this.currentUpwardsSpeed = -VERTICAL_SPEED;
        } else {
            this.currentUpwardsSpeed = 0;
        }
    }

    public float getRotation() {
        return super.getRotY();
    }
}
