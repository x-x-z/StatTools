package org.xxz.stattools.model.markov;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MarkovChainTest {

    /*
        http://neerc.ifmo.ru/wiki/index.php?title=Примеры_использования_Марковских_цепей
        Погода классифицируется в прогнозах как ясная, умеренно пасмурная и пасмурная.
        Если погода ясная, то вероятность, что она будет ясной на следующий день, составляет 0.5; вероятность, что она будет умеренно пасмурной, равна 0.4; а вероятность пасмурной погоды на следующий день составляет 0.1.
        Если погода умеренно пасмурная, то вероятность, что на следующий день она будет ясной, равна 0.3; вероятность, что погода останется умеренно пасмурной, равна 0.5; а вероятность пасмурной погоды на следующий день составляет 0.2.
        Если же погода пасмурная, то вероятность, что она будет ясной на следующий день составляет 0.2; вероятность что она станет умеренно пасмурной, равна 0.4; вероятность что на следующий день она останется пасмурной, равна 0.4.

        Вопрос 1 : Если вероятность ясной погоды в воскресенье равна 0.6, а вероятность умеренно пасмурной — 0.4, то какова вероятность, что погода в понедельник будет ясной?
        Вопрос 2 : Какова вероятность, что во вторник погода будет умеренно пасмурной?
    */

    @Test
    public void getNextStateProbabilityTest() throws Exception {
        double[][] transitionMatrix = new double[][]{
                {0.5, 0.4, 0.1},
                {0.3, 0.5, 0.2},
                {0.2, 0.4, 0.4}
        };
        double[] initialVector = new double[]{0.6, 0.4, 0.0}; // воскресенье

        MarkovChain markovChain = new MarkovChain();
        markovChain.setTransitionMatrix(transitionMatrix);
        markovChain.setInitialStateVector(initialVector);

        double[] nextStateProbability = markovChain.getNextStateProbability();
        Assert.assertTrue(Arrays.equals(new double[]{0.42, 0.44, 0.14}, nextStateProbability)); // понедельник

        nextStateProbability = markovChain.getNextStateProbability();
        Assert.assertEquals(2, markovChain.getIterationsCount());
        Assert.assertTrue(Arrays.equals(new double[]{0.37, 0.444, 0.186}, nextStateProbability)); // вторник
    }

}