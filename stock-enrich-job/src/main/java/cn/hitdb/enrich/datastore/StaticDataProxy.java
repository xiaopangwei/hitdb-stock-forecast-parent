package cn.hitdb.enrich.datastore;

import cn.hitdb.enrich.model.StaticDataField;
import cn.hitdb.enrich.model.StaticTableSchema;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>Company: Harbin Institute of Technology</p>
 *
 * @author weihuang
 * @date 2018/2/28
 * @time 下午3:57
 */
public class StaticDataProxy {

    private Map<String, StaticDataField> fieldsMap         = null;
    private byte[]                       data              = null;
    private ByteBuffer                   dataBuffer        = null;
    private StaticTableSchema            staticTableSchema = null;

    public StaticDataProxy() {

    }

    public StaticDataProxy(StaticTableSchema staticTableSchema) {
        this.staticTableSchema = staticTableSchema;
    }


    public void setSchema(StaticTableSchema schema) {
        this.staticTableSchema = schema;
        for (StaticDataField field : schema.getFields()) {
            fieldsMap.put(field.getName(), field);
        }
    }

    public Map<String, StaticDataField> getFieldsMap() {
        return fieldsMap;
    }

    public void setFieldsMap(Map<String, StaticDataField> fieldsMap) {
        this.fieldsMap = fieldsMap;
    }

    public ByteBuffer getDataBuffer() {
        return dataBuffer;
    }

    public void setDataBuffer(ByteBuffer dataBuffer) {
        this.dataBuffer = dataBuffer;
    }

    public StaticTableSchema getStaticTableSchema() {
        return staticTableSchema;
    }

    public void setStaticTableSchema(StaticTableSchema staticTableSchema) {
        this.staticTableSchema = staticTableSchema;
    }

    public void newData() {
        this.data = new byte[staticTableSchema.getTotalSize()];
        dataBuffer = ByteBuffer.wrap(this.data);
    }

    public void setData(byte[] data) {
        this.data = data;
        dataBuffer = ByteBuffer.wrap(this.data);
    }

    public void setBytes(StaticDataField field, byte[] src, int offset, int length) {
        dataBuffer.position(field.getOffset());
        dataBuffer.put(src, offset, length);
    }

    public void setBytes(StaticDataField field, byte[] src, int offset) {
        dataBuffer.position(field.getOffset());
        dataBuffer.put(src, offset, src.length);
    }

    public void setBytes(StaticDataField field, byte[] src) {
        dataBuffer.position(field.getOffset());
        dataBuffer.put(src, 0, src.length);
    }

    public void setBytes(String fieldName, byte[] src, int offset) {
        setBytes(fieldsMap.get(fieldName), src, offset);
    }

    public void getBytes(StaticDataField field, byte[] dst, int offset) {
        dataBuffer.position(field.getOffset());
        dataBuffer.get(dst, offset, field.getSize());
    }

    public byte[] getBytes(StaticDataField field) {
        byte[] dst = new byte[field.getSize()];
        getBytes(field, dst, 0);
        return dst;
    }

    public byte[] getBytes(String fieldName) {
        return getBytes(fieldsMap.get(fieldName));
    }
}
