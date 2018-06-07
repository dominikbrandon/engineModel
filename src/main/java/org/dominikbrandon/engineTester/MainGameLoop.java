package org.dominikbrandon.engineTester;

import org.dominikbrandon.entites.Camera;
import org.dominikbrandon.entites.Entity;
import org.dominikbrandon.entites.Light;
import org.dominikbrandon.models.TexturedModel;
import org.dominikbrandon.renderEngine.DisplayManager;
import org.dominikbrandon.renderEngine.Loader;
import org.dominikbrandon.models.RawModel;
import org.dominikbrandon.renderEngine.OBJLoader;
import org.dominikbrandon.renderEngine.Renderer;
import org.dominikbrandon.shaders.StaticShader;
import org.dominikbrandon.textures.ModelTexture;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

        RawModel crankshaftRawModel = OBJLoader.loadObjModel("crankshaft1", loader);
        RawModel pistonRodRawModel = OBJLoader.loadObjModel("pistonrod", loader);
        ModelTexture steelTexture = new ModelTexture(loader.loadTexture("stal"));
        TexturedModel crankshaftTexturedModel = new TexturedModel(crankshaftRawModel, steelTexture);
        TexturedModel pistonRodTexturedModel = new TexturedModel(pistonRodRawModel, steelTexture);
        Entity crankshaftEntity = new Entity(crankshaftTexturedModel, new Vector3f(0,0,0),0,0,0,0.01f);
        Entity pistonRodEntity = new Entity(pistonRodTexturedModel, new Vector3f(0,0,0),0,0,0,0.1f);

        Camera camera = new Camera();
        Light light = new Light(new Vector3f(0,0,10), new Vector3f(1,1,1));

        while(!Display.isCloseRequested()) {
            pistonRodEntity.increaseRotation(0,1,0);
            crankshaftEntity.increaseRotation(1,0,0);
            camera.move();

            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(crankshaftEntity, shader);
            renderer.render(pistonRodEntity, shader);
            shader.stop();
            DisplayManager.updateDisplay();
        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
