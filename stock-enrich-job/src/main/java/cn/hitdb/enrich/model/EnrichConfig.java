package cn.hitdb.enrich.model;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/28
 * @time 下午11:37
 */
public class EnrichConfig {
    private String binPath;
    private StaticTableSchema staticTableSchema;

    public String getBinPath() {
        return binPath;
    }

    public void setBinPath(String binPath) {
        this.binPath = binPath;
    }

    public StaticTableSchema getStaticTableSchema() {
        return staticTableSchema;
    }

    public void setStaticTableSchema(StaticTableSchema staticTableSchema) {
        this.staticTableSchema = staticTableSchema;
    }
}
