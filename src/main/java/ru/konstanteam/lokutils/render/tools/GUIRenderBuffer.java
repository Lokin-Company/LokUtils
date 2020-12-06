package ru.konstanteam.lokutils.render.tools;

import org.lwjgl.opengl.GL20C;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import ru.konstanteam.lokutils.gui.core.GUIShader;
import ru.konstanteam.lokutils.objects.*;
import ru.konstanteam.lokutils.render.context.GLContext;
import ru.konstanteam.lokutils.render.Texture;
import ru.konstanteam.lokutils.render.VBO;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class GUIRenderBuffer {
    protected VBO vertexVbo;
    protected VBO textureVbo;
    protected GUIShader shader;
    protected Texture texture;

    protected ArrayList<Float> vertexBuffer = new ArrayList<>();
    protected ArrayList<Float> texBuffer = new ArrayList<>();

    public GUIRenderBuffer(GUIShader shader){
        this.shader = shader;
    }

    public GUIShader getDefaultShader(){
        return shader;
    }

    public Texture getCurrentTexture(){
        return texture;
    }

    public void begin(Texture texture){
        if (vertexVbo == null)
            this.vertexVbo = new VBO();

        if (textureVbo == null)
            this.textureVbo = new VBO();

        this.texture = texture;
    }

    public void begin(){
        begin(null);
    }

    public void addVertex(float f){
        vertexBuffer.add(f);
    }

    public void addVertex(float f1, float f2){
        vertexBuffer.add(f1);
        vertexBuffer.add(f2);
    }

    public void addVertex(Vector2f vector2f){
        addVertex(vector2f.getX());
        addVertex(vector2f.getY());
    }

    public void addVertex(Vector3f vector3f){
        addVertex(vector3f.getX());
        addVertex(vector3f.getY());
        addVertex(vector3f.getZ());
    }

    public void addVertex(Vector4f vector4f){
        addVertex(vector4f.getX());
        addVertex(vector4f.getY());
        addVertex(vector4f.getZ());
        addVertex(vector4f.getZ());
    }

    public void addVertex(Point point){
        addVertex(point.x);
        addVertex(point.y);
    }

    public void addVertex(Rect rect){
        addVertex(rect.getTopLeftPoint());
        addVertex(rect.getTopRightPoint());
        addVertex(rect.getBottomRightPoint());
        addVertex(rect.getBottomLeftPoint());
    }

    public void addRawTexCoord(float f){
        texBuffer.add(f);
    }

    public void addRawTexCoord(float x, float y){
        addRawTexCoord(x);
        addRawTexCoord(y);
    }

    public void addTexCoord(Point point){
        float x = point.x;
        float y = point.y;

        if (texture != null){
            x /= texture.getSize().getX();
            y /= texture.getSize().getX();
        }

        addRawTexCoord(x, y);
    }

    public void addTexCoord(Rect rect){
        addTexCoord(rect.getTopLeftPoint());
        addTexCoord(rect.getTopRightPoint());
        addTexCoord(rect.getBottomRightPoint());
        addTexCoord(rect.getBottomLeftPoint());
    }

    public void draw(int type, Color color, GUIShader shader){
        boolean textureActive = texture != null && texBuffer.size() > 0;

        vertexBuffer.add(vertexBuffer.get(0));
        vertexBuffer.add(vertexBuffer.get(1));

        vertexVbo.putData(vertexBuffer);
        vertexVbo.bind();

        GL20C.glEnableVertexAttribArray(0);
        GL20C.glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        if (textureActive) {
            texture.bind();

            textureVbo.putData(texBuffer);
            textureVbo.bind();

            GL20C.glEnableVertexAttribArray(1);
            GL20C.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            textureVbo.unbind();
        }

        shader.bind();
        shader.setTranslate(GLContext.getCurrent().getViewTools().getCurrentTranslate());
        shader.setUseTexture(textureActive);

        if (color == null){
            float[] currentColor = new float[4];

            glGetFloatv(GL_CURRENT_COLOR, currentColor);
            shader.setColor(new Color(currentColor[0], currentColor[1], currentColor[2], currentColor[3]));
        }else
            shader.setColor(color);

        glDrawArrays(type, 0, vertexVbo.getSize());

        GL20C.glDisableVertexAttribArray(0);
        GL20C.glDisableVertexAttribArray(1);

        shader.unbind();
        if (textureActive) texture.unbind();

        vertexBuffer.clear();
        texBuffer.clear();

        this.texture = null;
    }

    public void draw(int type, Color color){
        draw(type, color, shader);
    }

    public void draw(int type){
        draw(type, null);
    }

    public void draw(){
        draw(GL_POLYGON);
    }
}
