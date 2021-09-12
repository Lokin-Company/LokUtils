package ru.konstanteam.lokutils.tools.property;

public interface ChangeListener<T> {
    void changed(Value<T> value, T from, T to);
}
