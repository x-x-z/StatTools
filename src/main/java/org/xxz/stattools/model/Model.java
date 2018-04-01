package org.xxz.stattools.model;

import java.util.List;

/**
 * @author Denis Kostin
 */
public interface Model<T> {

    /**
     * Create a model from values
     * @param values values to fit
     */
    void fit(List<T> values);

    /**
     * Predict next value based on created model
     * @return predicted value
     */
    T predict();

    /**
     * Predict next value<b>s</b> based on created model
     * @param steps values count
     * @return predicted values
     */
    List<T> predict(int steps);

}
