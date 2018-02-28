package cn.hitdb.enrich.forecast.algorithm.classification.train;

import cn.hitdb.enrich.forecast.algorithm.classification.model.LRConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestLogisticFunctionSelector {
    private LogisticFunctionSelector selector;

    @Before
    public void generateSelector() {
        LRConfig config = new LRConfig
                (null,
                        1,
                        null,
                        1.0,
                        12, 1.0);


        selector = new LogisticFunctionSelector(config);
    }

    @Test
    public void testSigmoid() {
        Assert.assertEquals(selector.sigmoid(0),0.50,0.0001);
    }
}
