package ru.konstanteam.lokutils.gui.core.windows.bar;

import ru.konstanteam.lokutils.gui.core.windows.window.GUIWindow;
import ru.konstanteam.lokutils.gui.eventsystem.CustomersContainer;
import ru.konstanteam.lokutils.objects.Rect;
import ru.konstanteam.lokutils.objects.Size;
import ru.konstanteam.lokutils.tools.property.PropertyBasic;

public abstract class GUIWindowBar<T extends GUIWindow> {
    protected final PropertyBasic<Size> minimumSize = new PropertyBasic<>(Size.ZERO);

    public abstract void render();

    public abstract void init(T window);

    public abstract void update();

    public abstract Rect getRect();

    public PropertyBasic<Size> minimumSize() {
        return minimumSize;
    }

    public abstract CustomersContainer getCustomersContainer();

    public abstract String getTitle();

    public abstract void setTitle(String text);
}
