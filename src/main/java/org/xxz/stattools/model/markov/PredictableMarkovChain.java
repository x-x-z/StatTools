package org.xxz.stattools.model.markov;

import org.xxz.stattools.model.Model;
import org.xxz.stattools.utils.BiHashMap;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Denis Kostin
 */
public class PredictableMarkovChain<T> extends MarkovChain implements Model<T> {

    private final Set<T> states;
    private final BiHashMap<T, Integer> stateOrder;

    @SafeVarargs
    @SuppressWarnings("varargs")
    public PredictableMarkovChain(T ... states) {
        if(states == null || states.length == 0) {
            throw new IllegalArgumentException("States set can not be null or empty");
        }

        this.states = new LinkedHashSet<>(Arrays.asList(states));
        this.stateOrder = new BiHashMap<>();
        int p=0;
        for (T state : states) {
            this.stateOrder.put(state, p++);
        }
    }

    public void setInitialStateVector(T state) {
        double[] initVector = new double[this.states.size()];
        initVector[stateOrder.get(state)] = 1.0;
        setInitialStateVector(initVector);
        reset();
    }

    @Override
    public void fit(List<T> values) {
        setTransitionMatrix(getTransitionMatrix(values));
    }

    @Override
    public T predict() {
        double row[] = getNextStateProbability();
        Integer maxProbabilityStateIndex = IntStream.range(0, row.length)
                .boxed()
                .reduce((a, b) -> row[a] < row[b] ? b : a)
                .orElse(null);

        return stateOrder.getKey(maxProbabilityStateIndex);
    }

    @Override
    public List<T> predict(int steps) {
        List<T> result = new ArrayList<>();
        for(int i=0; i<steps; i++) {
            result.add(predict());
        }
        return result;
    }

    /**
     * Расчёт переходной матрицы
     * @param values начальные распределения
     * @return переходная матрица
     */
    private double[][] getTransitionMatrix(List<T> values) {
        int statesSize = states.size();
        int[] statesSum = new int[statesSize];
        double[][] transitionMatrix = new double[statesSize][statesSize];

        T prev = values.get(0);
        for(int i=1; i<values.size(); i++) {
            T curr = values.get(i);
            Integer idx1 = stateOrder.get(prev);
            Integer idx2 = stateOrder.get(curr);

            if(idx1 == null || idx2 == null) {
                throw new IllegalStateException("State '" + curr + "' not found in states");
            }

            transitionMatrix[idx1][idx2] += 1;
            statesSum[idx1] += 1;

            prev = curr;
        }

        for(int i=0; i<transitionMatrix[0].length; i++) {
            for(int j=0; j<transitionMatrix[1].length; j++) {
                transitionMatrix[i][j] /= statesSum[i];
            }
        }

        return transitionMatrix;
    }

    public Set<T> getStates() {
        return Collections.unmodifiableSet(states);
    }
}
