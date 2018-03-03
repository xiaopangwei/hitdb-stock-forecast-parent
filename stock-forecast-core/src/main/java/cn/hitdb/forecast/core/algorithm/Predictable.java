package cn.hitdb.forecast.core.algorithm;

public interface Predictable<T> {
    void open();
    //List<T> fromSource(String ... args);
    void handle();
    //void toSink(List<T> messages);
    void close();



}
