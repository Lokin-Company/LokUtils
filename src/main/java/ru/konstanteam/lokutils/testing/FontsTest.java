package ru.konstanteam.lokutils.testing;

import ru.konstanteam.lokutils.applications.Application;
import ru.konstanteam.lokutils.gui.style.GUIStyle;
import ru.konstanteam.lokutils.gui.core.maincanvas.GUIMainCanvasSystem;
import ru.konstanteam.lokutils.gui.layout.Alignment;
import ru.konstanteam.lokutils.gui.layout.ListLayout;
import ru.konstanteam.lokutils.gui.objects.GUIText;
import ru.konstanteam.lokutils.gui.objects.margin.GUIMargin;
import ru.konstanteam.lokutils.gui.objects.margin.Margin;
import ru.konstanteam.lokutils.gui.panels.scroll.ScrollPanel;
import ru.konstanteam.lokutils.objects.Size;
import ru.konstanteam.lokutils.render.Window;

import java.awt.*;
import java.util.Locale;

public class FontsTest extends Application<GUIMainCanvasSystem> {
    public FontsTest() {
        super(new GUIMainCanvasSystem(), new Window().setTitle("LokUtils - fonts test"));
    }

    @Override
    public void initEvent() {
        this.window.setWindowCloseCallback((win) -> this.close());

        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        Font[] allFonts = ge.getAllFonts();
        ScrollPanel scrollPanel = new ScrollPanel();
        ListLayout listLayout = new ListLayout();
        scrollPanel.layout().addObject(listLayout, Alignment.TOP_LEFT);

        for (Font font : allFonts) {
            String fontName = font.getFontName(Locale.US);
            GUIStyle.getDefault().asset(GUIText.class).font(fontName, new ru.konstanteam.lokutils.render.text.Font(font.getName(), 14));
            GUIText text = new GUIText();
            text.setStyleFontName(fontName).string().set(fontName);

            listLayout.addObject(text);
        }

        listLayout.size().track(() -> listLayout.minimumSize().get().setWidth(scrollPanel.size().get().width), listLayout.minimumSize(), scrollPanel.size());

        scrollPanel.size().set(new Size(450,450));
        this.GUIController.getLayout().addObject(new GUIMargin(scrollPanel, new Margin(12)), Alignment.CENTER);
    }
}
