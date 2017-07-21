package com.harium.etyl.spriter;

import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Timeline;

import com.harium.etyl.commons.graphics.Color;
import com.harium.etyl.core.graphics.Graphics;
import com.harium.etyl.layer.ImageLayer;

public class SpriterDrawer extends Drawer<ImageLayer> {

    int width;
    int height;
    Graphics g;

    /**
     * Creates a new drawer based on the given loader.
     *
     * @param loader the loader containing resources
     */
    public SpriterDrawer(Loader<ImageLayer> loader) {
        super(loader);
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        this.g.setColor(new Color((int) (r * 255), (int) (g * 255), (int) (g * 255), (int) (a * 255)));
    }

    @Override
    public void line(float x1, float y1, float x2, float y2) {
        g.drawLine((int) x1, (int) (height - y1), (int) x2, (int) (height - y2));
    }

    @Override
    public void rectangle(float x, float y, float width, float height) {
        g.drawRect((int) x, (int) (this.height - y), (int) width, (int) height);
    }

    @Override
    public void circle(float x, float y, float radius) {
        g.drawCircle((int) x, (int) (height - y), (int) radius);
    }

    /**
     * Adapted from: https://github.com/Trixt0r/gdx-spriter/blob/master/src/main/java/com/brashmonkey/spriter/gdx/Drawer.java
     * @param object the object to draw.
     */
    @Override
    public void draw(Timeline.Key.Object object) {
        ImageLayer layer = loader.get(object.ref);

        float newPivotX = (layer.getW() * object.pivot.x);
        float newX = object.position.x - newPivotX;
        float newPivotY = (layer.getH() * object.pivot.y) - layer.getH();
        float newY = object.position.y - newPivotY;

        layer.setX((int) newX);
        //Inverted Y
        layer.setY((int) (height - newY));
        layer.setOrigin(newPivotX, -newPivotY);

        float angle = -object.angle;

        layer.setAngle(angle);

        layer.setOpacity((int) (255 * object.alpha));
        layer.setScaleX(object.scale.x);
        layer.setScaleY(object.scale.y);
        layer.draw(g);
    }

    public Graphics getGraphics() {
        return g;
    }

    public void setGraphics(Graphics g) {
        this.g = g;
        this.width = g.getWidth();
        this.height = g.getHeight();
    }
}
