package cn.hitdb.forecast.algorithm.classification.train;

import cn.hitdb.core.model.ml.FeatureVector;
import cn.hitdb.core.algorithm.Predictable;
import cn.hitdb.forecast.algorithm.classification.model.LRConfig;

import java.util.ArrayList;
import java.util.List;

public class LogisticRegressionAlgorithm implements Predictable<FeatureVector> {

    private LRConfig                 config;
    private LogisticFunctionSelector selector;
    private List<FeatureVector> inputVectors;

    public LogisticRegressionAlgorithm(LRConfig config, List<FeatureVector> inputVectors) {
        this.config = config;
        this.inputVectors=inputVectors;
    }

    public void open() {
        selector = new LogisticFunctionSelector(config);

    }


    public void handle() {
        open();
        int iterator=0;
        while (iterator<config.getIteratorNum()){
            List<Double> iteratorList=getIteratorValue(inputVectors);
            updateFeatureWeight(iteratorList);
        }

    }



    public List<Double> getIteratorValue(List<FeatureVector> dataList) {
        List<Double> tempLabelLists = new ArrayList<>(dataList.size());
        for (FeatureVector vector : dataList) {

            double[] featureValues = vector.getFeatures();
            for (double item : featureValues) {
                double result = item * FeatureVector.getFeatureConfig().get(vector.getLabelIndex());
                tempLabelLists.add(result);
            }
        }
        return tempLabelLists;
    }

    public void updateFeatureWeight(List<Double> expectedList) {
        int sampleSize=inputVectors.size();
        double delta=0;
        for (int i = 0; i < sampleSize; i++) {
            double error = selector.getSelectResult(expectedList.get(i)) - inputVectors.get(i).getLabelValue();
            delta+=(expectedList.get(i)*error);
        }
        delta*=(1.0)/sampleSize*delta;
        for(int i=0;i<sampleSize;i++){
            double previousValue=FeatureVector.getFeatureConfig().get(i);
            FeatureVector.setFeatureConfig(i,previousValue-delta);
        }
    }

    public void close() {

        //释放资源
        config=null;
        selector=null;
        inputVectors=null;
    }



}
