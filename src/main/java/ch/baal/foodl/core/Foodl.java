package ch.baal.foodl.core;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * User: rolf
 * To change this template use File | Settings | File Templates.
 */
public class Foodl {
    private static Logger LOG = LoggerFactory.getLogger(Foodl.class);

    /** position of quad */
    float x = 400, y = 300;
    /** angle of quad rotation */
    float rotation = 0;

    /** time at last frame */
    long lastFrame;

    /** frames per second */
    int fps;
    /** last fps time */
    long lastFPS;

    public static void main(String[] args) {
        Foodl foodl = new Foodl();
        foodl.start();
    }

    private void start() {
        LOG.debug("Hello Foodl {}", new FoodlProperties().getProjectVersion());

        try {
            DisplayMode[] modes = Display.getAvailableDisplayModes();
            for(DisplayMode mode : modes) {
                LOG.info("Displaymode: {}", mode);
            }

            LOG.info("Displaymode selected: {}", Display.getDisplayMode());
        } catch (LWJGLException e) {
            LOG.error("Could not get Available Display Modes", e);
        }

        try {
            Display.setDisplayMode(new DisplayMode(800, 600));
            Display.create();
        } catch (LWJGLException e) {
            LOG.error("Could not create OpenGL Display", e);
        }

        initGL();
        getDelta();
        lastFPS = getTime();

        while( !Display.isCloseRequested()) {
            int delta = getDelta();

            update(delta);
            pollInput();
            renderGL();



            Display.update();
            Display.sync(60);
        }
        Display.destroy();

        LOG.debug("Good bye Foodl");
    }

    /**
     * Calculate how many milliseconds have passed
     * since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void update(int delta) {
        // rotate quad
        rotation += 0.15f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_UP)) y -= 0.35f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) y += 0.35f * delta;

        // keep quad on the screen
        if (x < 0) x = 0;
        if (x > 800) x = 800;
        if (y < 0) y = 0;
        if (y > 600) y = 600;

        updateFPS(); // update FPS Counter
    }

    private void renderGL() {
        // Clear The Screen And The Depth Buffer
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // R,G,B,A Set The Color To Blue One Time Only
        GL11.glColor3f(0.5f, 0.5f, 1.0f);

        // draw quad
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glRotatef(rotation, 0f, 0f, 1f);
        GL11.glTranslatef(-x, -y, 0);

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(x - 50, y - 50);
        GL11.glVertex2f(x + 50, y - 50);
        GL11.glVertex2f(x + 50, y + 50);
        GL11.glVertex2f(x - 50, y + 50);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    private void initGL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, 800, 0, 600, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    public void pollInput() {

        if (Mouse.isButtonDown(0)) {
            int x = Mouse.getX();
            int y = Mouse.getY();

            LOG.debug("MOUSE DOWN @ X: {} Y: {}", x, y);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            LOG.debug("SPACE KEY IS DOWN");
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    LOG.debug("A Key Pressed");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    LOG.debug("S Key Pressed");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    LOG.debug("D Key Pressed");
                }
            } else {
                if (Keyboard.getEventKey() == Keyboard.KEY_A) {
                    LOG.debug("A Key Released");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_S) {
                    LOG.debug("S Key Released");
                }
                if (Keyboard.getEventKey() == Keyboard.KEY_D) {
                    LOG.debug("D Key Released");
                }
            }
        }
    }
}
