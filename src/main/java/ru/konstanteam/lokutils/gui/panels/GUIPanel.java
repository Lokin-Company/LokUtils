package ru.konstanteam.lokutils.gui.panels;

import ru.konstanteam.lokutils.gui.eventsystem.events.Event;
import ru.konstanteam.lokutils.gui.layout.GUIAbstractLayout;
import ru.konstanteam.lokutils.gui.objects.GUIBlackout;
import ru.konstanteam.lokutils.objects.Point;
import ru.konstanteam.lokutils.objects.Size;
import ru.konstanteam.lokutils.render.context.GLContext;
import ru.konstanteam.lokutils.tools.property.PropertyBasic;

import java.util.function.Supplier;

import static java.lang.Math.min;

public class GUIPanel<T extends GUIAbstractLayout> extends GUIBlackout {
    protected T rootLayout;

    protected Supplier<Point> canvasPosition;
    protected Supplier<Size> canvasSize;

    public GUIPanel(T rootLayout) {
        this.rootLayout = rootLayout;

        canvasPosition = () -> {
            float pixelsRound = this.getPixelsIndentation();

            return new Point(pixelsRound, pixelsRound);
        };

        canvasSize = () -> {
            float pixelsRound = this.getPixelsIndentation();

            return Size.max(size().get().relativeTo(pixelsRound * 2, pixelsRound * 2), rootLayout.minimumSize().get());
        };

        rootLayout.size().track(canvasSize, size());
        customersContainer.setCustomer(Event.class, rootLayout.getCustomersContainer());

        minimumSize().track(rootLayout.minimumSize());
        size().set(new Size(256, 256));

        setRounded(0.3f);
    }

    public float getPixelsIndentation() {
        Size size = size().get();
        return min(size.width, size.height) * rounded / 5f;
    }

    public T getRootLayout() {
        return rootLayout;
    }

    @Override
    public void init(GUIAbstractLayout owner) {
        super.init(owner);

        rootLayout.init(owner);
    }

    @Override
    public void update() {
        super.update();

        rootLayout.update();
    }

    @Override
    public void render() {
        super.render();

        GLContext.getCurrent().getViewTools().pushTranslate(canvasPosition.get());
        rootLayout.render();
        GLContext.getCurrent().getViewTools().popTranslate();
    }
}