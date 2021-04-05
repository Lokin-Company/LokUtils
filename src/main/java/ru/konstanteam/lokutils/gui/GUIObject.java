package ru.konstanteam.lokutils.gui;

import ru.konstanteam.lokutils.gui.animation.Animations;
import ru.konstanteam.lokutils.gui.eventsystem.CustomersContainer;
import ru.konstanteam.lokutils.gui.layout.GUIAbstractLayout;
import ru.konstanteam.lokutils.gui.objects.GUITextField;
import ru.konstanteam.lokutils.gui.style.GUIObjectAsset;
import ru.konstanteam.lokutils.gui.style.GUIStyle;
import ru.konstanteam.lokutils.objects.Size;
import ru.konstanteam.lokutils.render.context.GLContext;
import ru.konstanteam.lokutils.tools.property.Property;

public abstract class GUIObject {
    protected Property<Size> minimumSize = new Property<>(Size.ZERO);
    protected Property<Size> maximumSize = new Property<>(new Size(Float.MAX_VALUE, Float.MAX_VALUE));
    protected Property<Size> size = new Property<>(minimumSize);

    protected GUIStyle style;
    protected GUIObjectAsset asset;
    protected String name = "UIObject";

    protected Animations animations = new Animations(this);
    protected CustomersContainer customersContainer = new CustomersContainer();

    protected GUIAbstractLayout owner;

    public GUIObject(){
        this.asset = getStyle().asset(this.getClass());
    }

    public GUIObjectAsset getAsset() {
        return asset;
    }

    public GUIAbstractLayout getOwner() {
        return owner;
    }

    public GUIObject getFocusableObject() {
        return this;
    }

    public Animations getAnimations() {
        return animations;
    }

    public String getName() {
        return name;
    }

    public GUIObject setName(String name) {
        this.name = name;

        return this;
    }

    public GUIStyle getStyle() {
        return style != null ? style : (owner != null ? owner.getStyle() : GUIStyle.getDefault());
    }

    public GUIObject setStyle(GUIStyle style) {
        this.style = style;
        this.asset = style.asset(this.getClass());

        return this;
    }

    public Property<Size> size() {
        return size;
    }

    public Property<Size> minimumSize() {
        return minimumSize;
    }

    public Property<Size> maximumSize() {
        return maximumSize;
    }

    public CustomersContainer getCustomersContainer() {
        return customersContainer;
    }

    public void init(GUIAbstractLayout owner) {
        this.owner = owner;
    }

    public void update() {
        if (style == null)
            this.asset = getStyle().asset(this.getClass());

        animations.update(owner.getRefreshRate());
    }

    public abstract void render();
}
