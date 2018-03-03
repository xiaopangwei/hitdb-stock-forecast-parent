package cn.hitdb.window.forecast.algorithm.classification.train;

import cn.hitdb.window.forecast.algorithm.classification.model.LRConfig;

public class LogisticFunctionSelector {
    private static final String SIGMOD = "SIGMOD";
    private LRConfig config;

    public LogisticFunctionSelector(LRConfig config) {
        this.config = config;
    }

    public double getSelectResult(double x) {
        String functionName = config.getFunctionName();
        switch (functionName) {
            case SIGMOD:
                return sigmoid(x);

            default:
                return x;
        }

    }

    public double sigmoid(double z) {
        //return Math.exp(z) / (1.0 + Math.exp(z));//1
        return 1.0 / (1.0 + Math.exp(-z));//1
    }
}
