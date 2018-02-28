package cn.hitdb.enrich;

import cn.hitdb.enrich.datastore.BaseDataStore;
import cn.hitdb.enrich.datastore.StaticDataProxy;
import cn.hitdb.enrich.model.EnrichConfig;

import java.io.File;
import java.io.Serializable;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/28
 * @time 下午10:57
 */
public class DataEnricher implements Serializable{
    private BaseDataStore dataStore;
    private EnrichConfig config;
    private StaticDataProxy dataProxy;
    public void open(){
        dataStore.open(new File(config.getBinPath()));
        dataProxy=new StaticDataProxy(config.getStaticTableSchema());
    }

    public void close(){
        dataStore.close();
    }

    public void copyFields(){

    }
}
