package ru.lokincompany.lokutils.ui;

import org.lwjgl.util.vector.Vector2f;
import ru.lokincompany.lokutils.input.Inputs;
import ru.lokincompany.lokutils.ui.animation.Animations;
import ru.lokincompany.lokutils.ui.eventsystem.EventHandler;
import ru.lokincompany.lokutils.ui.objects.UICanvas;
import ru.lokincompany.lokutils.ui.positioning.PositioningSetter;

public class UIObject {

    protected Vector2f position = new Vector2f();
    protected Vector2f size = new Vector2f();
    protected UIStyle style = UIStyle.getDefault();
    protected String name = "UIObject";

    protected Animations animations = new Animations(this);
    protected EventHandler eventHandler = new EventHandler();

    protected PositioningSetter positionSetter;
    protected PositioningSetter sizeSetter;

    protected UIObject lastParent;

    public UIObject getLastParent() {
        return lastParent;
    }

    public UICanvas getCanvasParent() {
        return lastParent.getCanvasParent();
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public PositioningSetter getPositionSetter() {
        return positionSetter;
    }

    public PositioningSetter getSizeSetter() {
        return sizeSetter;
    }

    public Animations getAnimations() {
        return animations;
    }

    public String getName() {
        return name;
    }

    public UIObject setName(String name) {
        this.name = name;

        return this;
    }

    public UIStyle getStyle() {
        return style;
    }

    public UIObject setStyle(UIStyle style) {
        this.style = style;
        return this;
    }

    public Vector2f getPosition() {
        return new Vector2f(position.x, position.y);
    }

    public UIObject setPosition(PositioningSetter positionSetter) {
        positionSetter.init(this);
        this.positionSetter = positionSetter;

        return this;
    }

    public UIObject setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    public Vector2f getSize() {
        return new Vector2f(size.x, size.y);
    }

    public UIObject setSize(PositioningSetter sizeSetter) {
        sizeSetter.init(this);
        this.sizeSetter = sizeSetter;

        return this;
    }

    public UIObject setSize(Vector2f size) {
        this.size = size;
        return this;
    }

    public Inputs getInputs() {
        return lastParent.getInputs();
    }

    protected void updateEvents(Inputs inputs) {
        if (eventHandler != null)
            eventHandler.update(this, inputs);

        if (positionSetter != null) {
            Vector2f newPosition = positionSetter.get();
            position.set(newPosition.x, newPosition.y);
        }

        if (sizeSetter != null) {
            Vector2f newSize = sizeSetter.get();
            size.set(newSize.x, newSize.y);
        }

        animations.update();
    }

    public void update(UIObject parent) {
        lastParent = parent;

        updateEvents(parent.getInputs());
    }
}
