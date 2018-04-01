package org.xxz.stattools.model.markov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PredictableMarkovChainTest {

    @Test
    public void fitTest() throws Exception {
        PredictableMarkovChain<Character> markovChain = getModel();
        double[][] transitionMatrix = markovChain.getTransitionMatrix();
        double[][] expected = new double[][]{
                {2.0/3.0, 0.0/3.0, 1.0/3.0},
                {0.0/2.0, 0.0/2.0, 2.0/2.0},
                {1.0/3.0, 2.0/3.0, 0.0/3.0}
        };
        Assert.assertTrue(Arrays.deepEquals(expected, transitionMatrix));
    }

    @Test
    public void predictTest() throws Exception {
        PredictableMarkovChain<Character> model = getModel();
        model.setInitialStateVector(new double[]{1.0, 0.0, 0.0});
        Character predicted = model.predict();
        Assert.assertTrue(predicted.equals('A'));
    }

    @Test
    public void predict5Test() throws Exception {
        PredictableMarkovChain<Character> model = getModel();
        model.setInitialStateVector(new double[]{1.0, 0.0, 0.0});
        List<Character> predicted = model.predict(5);
        Assert.assertEquals(Arrays.asList('A', 'A', 'A', 'A', 'C'), predicted);
    }

    private PredictableMarkovChain<Character> getModel() {
        List<Character> values = Arrays.asList('A','A','A','C','B','C','B','C','A');
        PredictableMarkovChain<Character> markovChain = new PredictableMarkovChain<>('A', 'B', 'C');
        markovChain.fit(values);
        return markovChain;
    }
}