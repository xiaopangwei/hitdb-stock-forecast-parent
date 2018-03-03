package cn.hitdb.window.forecast.algorithm.feature.normalization.train;

import cn.hitdb.forecast.core.algorithm.Predictable;
import cn.hitdb.forecast.core.model.ml.FeatureVector;
import cn.hitdb.window.forecast.algorithm.feature.normalization.model.ZScoreConfig;

import java.util.List;

public class ZScoreAlgorithm implements Predictable<FeatureVector> {
    private ZScoreConfig        config;
    private List<FeatureVector> inputData;
    private double[]            acc;
    private double[]            std;


    public ZScoreAlgorithm(ZScoreConfig config, List<FeatureVector> vectors) {
        this.config = config;
        this.inputData = vectors;
    }


    @Override
    public void open() {

    }

    @Override
    public void handle() {

        for (FeatureVector item : inputData) {
            double[] sample = item.getFeatures();
            //index为列序号
            for (Integer index : config.getNumericColumnIndex()) {
                double cell = sample[index];
                acc[index] += cell;
            }
        }
        int sampleSize = inputData.size();
        //Std
        for (FeatureVector item : inputData) {
            double[] sample = item.getFeatures();
            //index为列序号
            double diff =0;
            for (Integer index : config.getNumericColumnIndex()) {
                double avg  = acc[index] * 1.0 / sampleSize;
                 diff+=(sample[index] - avg) * (sample[index] - avg);
            }
        }

    }

    @Override
    public void close() {
        config = null;
        inputData = null;
        acc = null;
        std = null;

    }
}
