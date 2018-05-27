package org.dominikbrandon.shaders;

import org.dominikbrandon.entites.Camera;
import org.dominikbrandon.toolbox.Maths;
import org.lwjgl.util.vector.Matrix4f;

import java.io.IOException;

public class StaticShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/main/java/org/dominikbrandon/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/org/dominikbrandon/shaders/fragmentShader.txt";
    private int locationTransformationMatrix;
    private int locationProjectionMatrix;
    private int locationViewMatrix;

    public StaticShader() throws IOException {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
        locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
        locationViewMatrix = super.getUniformLocation("viewMatrix");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(locationTransformationMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(locationViewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection) {
        super.loadMatrix(locationProjectionMatrix, projection);
    }
}
