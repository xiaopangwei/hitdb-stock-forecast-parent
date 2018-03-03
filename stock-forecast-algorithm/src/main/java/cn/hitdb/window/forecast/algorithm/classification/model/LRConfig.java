package cn.hitdb.window.forecast.algorithm.classification.model;

import cn.hitdb.forecast.core.model.ml.MLConfigration;

import java.util.Arrays;

public class LRConfig extends MLConfigration {
    private String[] ignoredFields;
    private int[]    ignoreFieldIndex;
    private String   labelFeatureName;
    private int      labelFeatureIndex;
    private String   functionName;
    private double rate;
    private int iteratorNum;
    private double erorrThreshold;

    public LRConfig(String labelFeatureName, int labelFeatureIndex, String functionName,
                    double rate, int iteratorNum, double erorrThreshold) {
        this.labelFeatureName = labelFeatureName;
        this.labelFeatureIndex = labelFeatureIndex;
        this.functionName = functionName;
        this.rate = rate;
        this.iteratorNum = iteratorNum;
        this.erorrThreshold = erorrThreshold;
    }

    public String[] getIgnoredFields() {
        return ignoredFields;
    }

    public void setIgnoredFields(String[] ignoredFields) {
        this.ignoredFields = ignoredFields;
    }

    public String getLabelFeatureName() {
        return labelFeatureName;
    }

    public void setLabelFeatureName(String labelFeatureName) {
        this.labelFeatureName = labelFeatureName;
    }

    public int getLabelFeatureIndex() {
        return labelFeatureIndex;
    }

    public void setLabelFeatureIndex(int labelFeatureIndex) {
        this.labelFeatureIndex = labelFeatureIndex;
    }

    public int[] getIgnoreFieldIndex() {
        return ignoreFieldIndex;
    }

    public void setIgnoreFieldIndex(int[] ignoreFieldIndex) {
        this.ignoreFieldIndex = ignoreFieldIndex;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getIteratorNum() {
        return iteratorNum;
    }

    public void setIteratorNum(int iteratorNum) {
        this.iteratorNum = iteratorNum;
    }

    public double getErorrThreshold() {
        return erorrThreshold;
    }

    public void setErorrThreshold(double erorrThreshold) {
        this.erorrThreshold = erorrThreshold;
    }

    @Override
    public String toString() {
        return "LRConfig{" +
                "ignoredFields=" + Arrays.toString(ignoredFields) +
                ", ignoreFieldIndex=" + Arrays.toString(ignoreFieldIndex) +
                ", labelFeatureName='" + labelFeatureName + '\'' +
                ", labelFeatureIndex=" + labelFeatureIndex +
                ", functionName='" + functionName + '\'' +
                ", rate=" + rate +
                '}';
    }
}
