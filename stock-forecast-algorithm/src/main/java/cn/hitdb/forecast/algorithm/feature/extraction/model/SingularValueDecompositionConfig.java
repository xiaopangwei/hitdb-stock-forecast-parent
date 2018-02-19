package cn.hitdb.forecast.algorithm.feature.extraction.model;

import cn.hitdb.core.model.ml.MLConfigration;

public class SingularValueDecompositionConfig extends MLConfigration{
    private int remainedFeatureNum;
    private double threshold=1.0;

    public int getRemainedFeatureNum() {
        return remainedFeatureNum;
    }

    public void setRemainedFeatureNum(int remainedFeatureNum) {
        this.remainedFeatureNum = remainedFeatureNum;
    }

    public void setThreShould(double percentage){
        this.threshold=percentage;
    }

}
