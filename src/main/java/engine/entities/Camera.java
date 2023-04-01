package engine.entities;

import engine.display.DisplayManager;
import engine.tools.Keyboard;
import engine.tools.Mouse;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

    public class Camera extends Entity{

        private static final float MOVE_SPEED = 150;
        private static final float ROTATION_SPEED = 30;
        private static final float MIN_CAMERA_HEIGHT = 20;
        private static final float MAX_CAMERA_HEIGHT= 350;
        private float currentLongitudinalSpeed = 0;
        private float currentLateralSpeed = 0;
        private float currentRotationSpeed = 0;
        private float pitch = 30;
        private float yaw = 0;
        private float zoom = 50;

        public Camera(Vector3f position) {
            super(position);
            super.increaseRotation(0, 225, 0);
        }

        public void move() {
            checkInputs();
            calculateZoom();
            calculatePitch();
            calculateRotation();


            super.increaseRotation(0, currentRotationSpeed * DisplayManager.getFrameTimeSeconds(), 0);
            currentRotationSpeed = 0;
            yaw = -super.getRotY();

            float longitudinalDistance = currentLongitudinalSpeed * DisplayManager.getFrameTimeSeconds();
            float lateralDistance = currentLateralSpeed * DisplayManager.getFrameTimeSeconds();
            float dx = (float) (longitudinalDistance * Math.sin(Math.toRadians(super.getRotY()))) + (float) (lateralDistance * Math.cos(Math.toRadians(-super.getRotY())));
            float dz = (float) (longitudinalDistance * Math.cos(Math.toRadians(super.getRotY()))) + (float) (lateralDistance * Math.sin(Math.toRadians(-super.getRotY())));
            super.increasePosition(dx, 0, dz);

            float horizontalDistance = calculateHorizontalDistance();
            float verticalDistance = calculateVerticalDistance();
            calculateCameraPosition(horizontalDistance, verticalDistance);
            zoom = 0;
        }


        public Vector3f getPosition() {
            return super.getPosition();
        }

        public float getPitch() {
            return pitch;
        }

        public float getYaw() {
            return yaw;
        }

        private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
            float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(-yaw)));
            float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(-yaw)));
            super.increasePosition(offsetX,verticalDistance, offsetZ);
        }

        private void checkInputs() {
            if (Keyboard.isKeyDown(GLFW_KEY_W)) {
                this.currentLongitudinalSpeed = -MOVE_SPEED;
            } else if (Keyboard.isKeyDown(GLFW_KEY_S)) {
                this.currentLongitudinalSpeed = MOVE_SPEED;
            } else {
                this.currentLongitudinalSpeed = 0;
            }

            if (Keyboard.isKeyDown(GLFW_KEY_A)) {
                this.currentLateralSpeed = -MOVE_SPEED;
            } else if (Keyboard.isKeyDown(GLFW_KEY_D)) {
                this.currentLateralSpeed = MOVE_SPEED;
            } else {
                this.currentLateralSpeed = 0;
            }
        }

        private float calculateHorizontalDistance() {
            return (float) (zoom * Math.cos(Math.toRadians(pitch)));
        }

        private float calculateVerticalDistance() {
            return (float) (zoom * Math.sin(Math.toRadians(pitch)));
        }

        public void calculateZoom() {
            zoom -= Mouse.getDWheel() * 7;
            if (super.position.y <= MIN_CAMERA_HEIGHT) {
                if (zoom < 0) {
                    zoom = 0;
                    super.position.y = MIN_CAMERA_HEIGHT;
                }
            } else if (super.position.y >= MAX_CAMERA_HEIGHT) {
                if (zoom > 0) {
                    zoom = 0;
                    super.position.y = MAX_CAMERA_HEIGHT;
                }
            }
        }

        public void calculatePitch() {
            if (Mouse.isRightButtonPressed()) {
                pitch -= Mouse.getDY() * 0.1f;
                if (pitch > 90) {
                    pitch = 90;
                } else if (pitch < 0) {
                    pitch = 0;
                }
            }
        }

        public void calculateRotation() {
            if (Mouse.isRightButtonPressed()) {
                currentRotationSpeed = (Mouse.getDX() * ROTATION_SPEED);
            }
        }
    }

