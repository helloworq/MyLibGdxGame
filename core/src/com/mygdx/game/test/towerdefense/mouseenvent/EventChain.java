package com.mygdx.game.test.towerdefense.mouseenvent;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.LinkedList;

/**
 * 界面选择界面的时候可能有多层，考虑设计一个响应链简化处理
 */
public class EventChain<T extends Unit> {
    private final LinkedList<T> elements = new LinkedList<>();

    public void pushEvent(T t) {
        elements.addLast(t);
    }

    public T getTopEvent() {
        return elements.getLast();
    }

    public T getRootEvent() {
        return elements.getFirst();
    }

    public void clear() {
        elements.clear();
    }

    public boolean canExecute() {
        return !elements.isEmpty() && elements.getFirst().getConsumer() != null;
    }
}
