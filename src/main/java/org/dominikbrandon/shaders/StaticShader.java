package org.dominikbrandon.shaders;

import java.io.IOException;

public class StaticShader extends ShaderProgram {
    private static final String VERTEX_FILE = "src/main/java/org/dominikbrandon/shaders/vertexShader.txt";
    private static final String FRAGMENT_FILE = "src/main/java/org/dominikbrandon/shaders/fragmentShader.txt";

    public StaticShader() throws IOException {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
