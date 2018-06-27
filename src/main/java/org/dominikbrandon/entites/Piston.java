package org.dominikbrandon.entites;

import org.dominikbrandon.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Piston {
    private static final float VERTICAL_SPEED = 0.01f;
    private static final float ROTATION_SPEED = 0.07f;
    private static final float HIGHEST_Y_POSITION = 4.9f;
    private static final float LOWEST_Y_POSITION = 1.6f;
    private static final float MIDDLE_Y_POSITION = LOWEST_Y_POSITION + (HIGHEST_Y_POSITION - LOWEST_Y_POSITION)/2;
    private static final float VALVE_MOVE_SPEED = 0.002f;

    private Entity rod;
    private Entity head;
    private Entity valveIn;
    private Entity valveOut;

    private boolean isMovingUp;
    private boolean isInUpperHalf;
    private boolean firstStrokeCompleted = false;
    private int stroke;

    public Piston(TexturedModel rodModel, TexturedModel headModel, Vector3f position, float rotation, int stroke) {
        this.rod = new Entity(rodModel, position, rotation, 90, 180, 1f);
        this.head = new Entity(headModel, new Vector3f(position.x, position.y+1f, position.z-0.5f),0,0,-90,0.2f);
        this.stroke = stroke;
        this.isMovingUp = (stroke % 2) == 0;
        this.isInUpperHalf = position.y > MIDDLE_Y_POSITION;
    }

    public void move() {
        checkYPosition();

        rod.increasePosition(0, (isMovingUp ? 1 : -1) * VERTICAL_SPEED, 0);
        head.increasePosition(0, (isMovingUp ? 1 : -1) * VERTICAL_SPEED, 0);
        rod.increaseRotation((isInUpperHalf ? -1 : 1) * ROTATION_SPEED, 0, 0);
        if (firstStrokeCompleted) {
            moveValves();
        }
    }

    private void moveValves() {
        if (stroke == 1) {
            float moveValue = (isInUpperHalf ? -1 : 1) * VALVE_MOVE_SPEED;
            valveIn.increasePosition(0, moveValue, moveValue);
        } else if (stroke == 4) {
            float moveValue = (isInUpperHalf ? 1 : -1) * VALVE_MOVE_SPEED;
            valveOut.increasePosition(0, moveValue, - moveValue);
        }
    }

    private void checkYPosition() {
        isInUpperHalf = rod.getPosition().y > MIDDLE_Y_POSITION;
        if (rod.getPosition().y >= HIGHEST_Y_POSITION || rod.getPosition().y <= LOWEST_Y_POSITION) {
            isMovingUp = !isMovingUp;
            stroke = stroke % 4 + 1;
            firstStrokeCompleted = true;
        }
    }

    public void addValves(TexturedModel valveModel) {
        Vector3f rodPosition = rod.getPosition();
        Vector3f valveInPosition = new Vector3f(rodPosition.x, HIGHEST_Y_POSITION + 3f, rodPosition.z + 1f);
        Vector3f valveOutPosition = new Vector3f(rodPosition.x, HIGHEST_Y_POSITION + 3f, rodPosition.z - 1f);
        valveIn = new Entity(valveModel, valveInPosition, 45, 0, 0, 0.03f);
        valveOut = new Entity(valveModel, valveOutPosition, -45, 0, 0, 0.03f);
    }

    public Entity getRod() {
        return rod;
    }

    public Entity getHead() {
        return head;
    }

    public Entity getValveIn() {
        return valveIn;
    }

    public Entity getValveOut() {
        return valveOut;
    }
}
