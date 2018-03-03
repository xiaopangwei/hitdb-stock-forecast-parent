package cn.hitdb.window.forecast.algorithm.feature.normalization.model;

import cn.hitdb.forecast.core.model.ml.MLConfigration;

import java.util.List;

public class ZScoreConfig extends MLConfigration {
    private List<Integer> numericColumnIndex;
    private int           columnSize;

    public ZScoreConfig(List<Integer> numericColumnIndex, int size) {
        this.numericColumnIndex = numericColumnIndex;
        this.columnSize = size;

    }

    public List<Integer> getNumericColumnIndex() {
        return numericColumnIndex;
    }

    public void setNumericColumnIndex(List<Integer> numericColumnIndex) {
        this.numericColumnIndex = numericColumnIndex;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }
}
