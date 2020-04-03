package ru.lokincompany.lokutils.ui.animation;

import ru.lokincompany.lokutils.ui.UIObject;

import java.util.HashMap;
import java.util.Map;

public class Animations {

    protected HashMap<String, Animation> animationHashMap = new HashMap<>();
    protected UIObject source;

    public Animations(UIObject source){
        this.source = source;
    }

    public Animation getAnimation(String name){
        return animationHashMap.getOrDefault(name, null);
    }

    public Animations addAnimation(Animation animation){
        animationHashMap.put(animation.getName(), animation);
        animation.init(source);

        return this;
    }

    public Animations stopAnimation(String name){
        if (!animationHashMap.containsKey(name)) return this;

        animationHashMap.get(name).stop();
        return this;
    }

    public Animations startAnimation(String name){
        if (!animationHashMap.containsKey(name)) return this;

        animationHashMap.get(name).start();
        return this;
    }

    public void update() {
        for (Animation animation : animationHashMap.values()){
            if (animation.isRun())
                animation.update();
        }
    }

}
