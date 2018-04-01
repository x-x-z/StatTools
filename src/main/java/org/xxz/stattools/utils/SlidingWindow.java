package org.xxz.stattools.utils;

import java.lang.reflect.Array;

/**
 * Created by D.Kostin on 17.02.2018.
 */
public class SlidingWindow<T> {

    private Class<T> clazz;
    private T[] values;
    private int index;
    private int period;
    private int size;

    @SuppressWarnings("unchecked")
    public SlidingWindow(Class<T> clazz, int size) {
        this.clazz = clazz;
        this.size = size;
        this.values = (T[]) Array.newInstance(clazz, size);
    }

    public void add(T value) {
        this.values[this.index++] = value;
        if (this.index >= this.values.length) {
            this.index = 0;
            this.period++;
        }
    }

    @SuppressWarnings("unchecked")
    public T[] getValues() {
        T[] result = (T[]) Array.newInstance(this.clazz, this.values.length);
        System.arraycopy(this.values, this.index, result, 0, this.values.length - this.index);
        System.arraycopy(this.values, 0, result, this.values.length - this.index, this.index);
        return result;
    }

    public int getSize() {
        return size;
    }

    public boolean isFull() {
        return this.period > 0;
    }

    public void reset() {
        this.index = 0;
        this.period = 0;
    }
}
