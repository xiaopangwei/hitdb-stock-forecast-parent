package cn.hitdb.forecast.core.model.ml;

import java.io.Serializable;
import java.util.Arrays;

public class FeatureVector implements Serializable{
    private        long          id;
    private static FeatureConfig feature;
    private        int           labelIndex;
    private        int           labelValue;
    private        double[]      features;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static FeatureConfig getFeatureConfig() {
        return feature;
    }

    public static void setFeatureConfig(FeatureConfig feature) {
        FeatureVector.feature = feature;
    }

    public static void setFeatureConfig(int index,double value){
        FeatureVector.getFeatureConfig().update(index,value);
    }
    public int getLabelIndex() {
        return labelIndex;
    }

    public void setLabelIndex(int labelIndex) {
        this.labelIndex = labelIndex;
    }

    public int getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(int labelValue) {
        this.labelValue = labelValue;
    }

    public double[] getFeatures() {
        return features;
    }

    public void setFeatures(double[] features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "FeatureVector{" +
                "id=" + id +
                ", labelIndex=" + labelIndex +
                ", labelValue=" + labelValue +
                ", features=" + Arrays.toString(features) +
                '}';
    }
}
