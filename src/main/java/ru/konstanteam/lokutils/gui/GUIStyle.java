package ru.konstanteam.lokutils.gui;

import ru.konstanteam.lokutils.objects.Color;
import ru.konstanteam.lokutils.objects.ColorRGB;
import ru.konstanteam.lokutils.render.Font;
import ru.konstanteam.lokutils.render.context.GLContext;

import java.util.HashMap;

public class GUIStyle {
    private static HashMap<GLContext, GUIStyle> defaultStyles = new HashMap<>();
    protected HashMap<String, Color> colors = new HashMap<>();
    protected HashMap<String, Font> fonts = new HashMap<>();
    protected HashMap<String, Object> someParams = new HashMap<>();

    public static void generateDefaultStyle() {
        if (GLContext.getCurrent() == null)
            throw new RuntimeException("Default style cannot be generated without OpenGL context!");

        setDefault(
                new GUIStyle()
                        .setFont("default", new Font().load())
                        .setFont("windowTitle", new Font().load(new java.awt.Font("Aria", java.awt.Font.PLAIN, 10)))
                        .setFont("textFieldTitle", new Font().load(new java.awt.Font("TimesRomans", java.awt.Font.PLAIN, 11)))

                        .setColor("background", new Color(0.15f, 0.15f, 0.15f, 0.8f))
                        .setColor("brightBackground", new Color(0.2f, 0.2f, 0.2f, 0.8f))

                        .setColor("buttonPressed", new Color(0.38f, 0.38f, 0.38f, 0.8f))
                        .setColor("buttonPointed", new Color(0.33f, 0.33f, 0.33f, 0.8f))
                        .setColor("buttonBackground", new Color(0.2f, 0.2f, 0.2f, 0.8f))

                        .setColor("windowCloseButtonPressed", new ColorRGB(255, 170, 170, 255))
                        .setColor("windowCloseButtonBackground", new ColorRGB(255, 100, 100, 255))
                        .setColor("windowMinimizeButtonPressed", new ColorRGB(255, 255, 170, 255))
                        .setColor("windowMinimizeButtonBackground", new ColorRGB(255, 230, 100, 255))
                        .setColor("windowContentBackground", new Color(0.3f, 0.3f, 0.3f, 0.9f))
                        .setColor("windowBarBackground", new Color(0.25f, 0.25f, 0.25f, 0.8f))

                        .setColor("text", new Color(0.9f, 0.9f, 0.9f, 1))
                        .setColor("highlightedText", new Color(1, 1, 0.9f, 1))

                        .setColor("checkboxStroke", new Color(0.9f, 0.9f, 0.9f, 1))
                        .setColor("checkboxFillActive", new Color(0.8f, 0.8f, 0.8f, 1))
                        .setColor("checkboxFillInactive", new Color(0.5f, 0.5f, 0.5f, 1))

                        .setColor("separateColor", new Color(0.6f, 0.6f, 0.6f, 0.9f))

                        .setColor("sliderHead", new Color(0.2f, 0.2f, 0.2f, 1))
                        .setColor("sliderFullness", new Color(0.8f, 0.8f, 0.8f, 1))
                        .setColor("sliderEdges", new Color(0.15f, 0.15f, 0.15f, 0.8f))

                        .setColor("verticalScrollBarBackground", new Color(0.9f, 0.9f, 0.9f, 0.5f))
                        .setColor("verticalScrollBarHead", new Color(1f, 1f, 1f, 0.5f))

                        .setColor("horizontalScrollBarBackground", new Color(0.9f, 0.9f, 0.9f, 0.5f))
                        .setColor("horizontalScrollBarHead", new Color(1f, 1f, 1f, 0.5f))

                        .setObject("separateLineWidth", 1f)

                        .setColor("textFieldBackground", new Color(0.2f, 0.2f, 0.2f, 0.8f))
                        .setObject("textFieldRounded", 0.3f)


        );
    }

    public static GUIStyle getDefault() {
        GLContext context = GLContext.getCurrent();
        if (context == null)
            return null;

        if (!defaultStyles.containsKey(context))
            generateDefaultStyle();

        return defaultStyles.get(context);
    }

    public static void setDefault(GUIStyle style) {
        defaultStyles.put(GLContext.getCurrent(), style);
    }

    public Object getObject(String name) {
        return someParams.get(name);
    }

    public GUIStyle setObject(String name, Object object) {
        someParams.put(name, object);

        return this;
    }

    public Color getColor(String name) {
        return colors.getOrDefault(name, Color.BLACK);
    }

    public Font getFont(String name) {
        Font defaultFont = fonts.get("default");

        return fonts.getOrDefault(name, defaultFont);
    }

    public GUIStyle setColor(String name, Color color) {
        colors.put(name, color);

        return this;
    }

    public GUIStyle setFont(String name, Font font) {
        fonts.put(name, font);

        return this;
    }


}
