package cn.hitdb.forecast.core.model.message;

import java.io.Serializable;
import java.util.List;

public class BaseMessage implements Serializable {
    private String        sourceId         = null;
    private String        sourceName       = null;
    private String        sourceTimestamp  = null;
    private List<String>  sourceHandleList = null;
    private Configuration configuration    = null;

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceTimestamp() {
        return sourceTimestamp;
    }

    public void setSourceTimestamp(String sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }

    public List<String> getSourceHandleList() {
        return sourceHandleList;
    }

    public void setSourceHandleList(List<String> sourceHandleList) {
        this.sourceHandleList = sourceHandleList;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "sourceId='" + sourceId + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", sourceTimestamp='" + sourceTimestamp + '\'' +
                ", sourceHandleList=" + sourceHandleList +
                ", configuration=" + configuration +
                '}';
    }
}
