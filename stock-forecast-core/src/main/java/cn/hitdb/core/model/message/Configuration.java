package cn.hitdb.core.model.message;

import java.io.Serializable;
import java.util.Map;

public class Configuration implements Serializable{
    private Map<String,Object> properties;
    public void setProperties(String key,Object value){
        properties.put(key,value);
    }
    public Object getProperties(String key){
        return properties.get(key);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "properties=" + properties +
                '}';
    }
}
