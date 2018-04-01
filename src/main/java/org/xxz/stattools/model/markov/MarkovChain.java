package org.xxz.stattools.model.markov;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by D.Kostin on 14.02.2018.
 */
public class MarkovChain {

    @Getter
    @Setter
    private double[][] transitionMatrix;

    @Getter
    private double[] initialStateVector;

    private double[] currentStateVector;

    @Getter
    private int iterationsCount;


    public void setInitialStateVector(double[] initialStateVector) {
        this.initialStateVector = initialStateVector;
        reset();
    }

    public double[] getNextStateProbability() {
        if (initialStateVector == null) {
            throw new IllegalStateException("Initial vector can not be null");
        }

        if(transitionMatrix == null) {
            throw new IllegalStateException("Transition Matrix can not be null");
        }

        double[] vector = new double[currentStateVector.length];
        for (int i = 0; i < currentStateVector.length; i++) {
            double value = 0.0;
            for (int j = 0; j < transitionMatrix.length; j++) {
                value += (currentStateVector[j]*transitionMatrix[j][i]);
            }
            vector[i] = value;
        }

        this.currentStateVector = vector;
        this.iterationsCount += 1;
        return vector;
    }

    public void reset() {
        this.currentStateVector = this.initialStateVector;
        this.iterationsCount = 0;
    }
}
