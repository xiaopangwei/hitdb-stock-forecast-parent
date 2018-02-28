package cn.hitdb.enrich.model;

import java.io.Serializable;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/28
 * @time 下午4:00
 */
public class StaticTableSchema implements Serializable {
    private StaticDataField[] fields;

    public int getTotalSize() {
        int base = 0;
        for (StaticDataField item : fields) {
            base += (item.getSize());
        }
        return base;
    }

    public StaticDataField[] getFields() {
        return fields;
    }

    public void setFields(StaticDataField[] fields) {
        this.fields = fields;
    }
}
