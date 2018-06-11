package org.dominikbrandon.engineTester;

import org.dominikbrandon.entites.Camera;
import org.dominikbrandon.entites.Entity;
import org.dominikbrandon.entites.FocusPoint;
import org.dominikbrandon.entites.Light;
import org.dominikbrandon.models.TexturedModel;
import org.dominikbrandon.renderEngine.*;
import org.dominikbrandon.models.RawModel;
import org.dominikbrandon.textures.ModelTexture;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class MainGameLoop {

    public static void main(String[] args) throws LWJGLException, IOException {
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer();

        ModelTexture steelTexture = new ModelTexture(loader.loadTexture("steel"));
        steelTexture.setShineDamper(10);
        steelTexture.setReflectivity(1);

        RawModel crankshaftRawModel = OBJLoader.loadObjModel("crankshaft1", loader);
        RawModel pistonRodRawModel = OBJLoader.loadObjModel("pistonrod", loader);
        RawModel pistonHeadRawModel = OBJLoader.loadObjModel("pistonHead3", loader);
        RawModel valveRawModel = OBJLoader.loadObjModel("valve1", loader);
        RawModel camshaftRawModel = OBJLoader.loadObjModel("camshaft1", loader);
        TexturedModel crankshaftTexturedModel = new TexturedModel(crankshaftRawModel, steelTexture);
        TexturedModel pistonRodTexturedModel = new TexturedModel(pistonRodRawModel, steelTexture);
        TexturedModel pistonHeadTexturedModel = new TexturedModel(pistonHeadRawModel, steelTexture);
        TexturedModel valveTexturedModel = new TexturedModel(valveRawModel, steelTexture);
        TexturedModel camshaftTexturedModel = new TexturedModel(camshaftRawModel, steelTexture);

//        Entity crankshaftEntity = new Entity(crankshaftTexturedModel, new Vector3f(0,-3,-10),90,0,90,0.04f); // c2
        Entity crankshaftEntity = new Entity(crankshaftTexturedModel, new Vector3f(0,-3,-10),90,0,0,0.1f); // c1
        Entity pistonRodEntity = new Entity(pistonRodTexturedModel, new Vector3f(-3.25f,1,-10),0,90,180,1f);
        Entity pistonHeadEntity = new Entity(pistonHeadTexturedModel, new Vector3f(-3.25f,1,-10),0,90,0,0.05f);
        Entity valveEntity = new Entity(valveTexturedModel, new Vector3f(0,0,-10),0,0,0,0.04f);
        Entity camshaftEntity = new Entity(camshaftTexturedModel, new Vector3f(0,0,-10),0,0,0,0.053f);

        FocusPoint focusPoint = new FocusPoint(pistonHeadTexturedModel, new Vector3f(0,-2,-5),
                90,0,0,0.05f);
        Camera camera = new Camera(focusPoint);
        Light light = new Light(new Vector3f(0,100,100), new Vector3f(1,1,1));

        while(!Display.isCloseRequested()) {
            camera.move();
//            renderer.processEntity(focusPoint);   // ENABLE THIS TO SEE THE POINT

            renderer.processEntity(crankshaftEntity);
            renderer.processEntity(pistonRodEntity);
            renderer.processEntity(pistonHeadEntity);
            renderer.processEntity(valveEntity);
            renderer.processEntity(camshaftEntity);
            renderer.render(light, camera);

            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
