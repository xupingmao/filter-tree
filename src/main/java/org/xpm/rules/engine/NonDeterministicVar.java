package org.xpm.rules.engine;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xupingmao on 2017/12/7.
 */
public class NonDeterministicVar implements Iterator {

    private List<Object> values;
    private int nextIndex;
    private int maxIndex;
    private Object current;
    private NonDeterministicVar parent;

    public NonDeterministicVar(NonDeterministicVar parent, Object... vars) {
        this.parent = parent;
        values = Arrays.asList(vars);
        maxIndex = values.size();
        nextIndex = 0;
    }

    private void reset() {
        nextIndex = 1;
        current = values.get(0);
        if (this.parent != null) {
            this.parent.reset();
        }
    }

    @Override
    public boolean hasNext() {
        if (nextIndex < maxIndex) {
            return true;
        }
        if (parent != null) {
            return parent.hasNext();
        }
        return false;
    }

    @Override
    public Object next() {
        if (nextIndex == 0) {
            reset();
            return current;
        }
        if (nextIndex == maxIndex) {
            if (parent != null) {
                parent.next();
                current = values.get(0);
                nextIndex = 1;
            } else {
                throw new IndexOutOfBoundsException("no more elements");
            }
        } else {
            current = values.get(nextIndex);
            nextIndex++;
        }
        return current;
    }

    @Override
    public void remove() {

    }

    public Object getCurrent() {
        return current;
    }
}
