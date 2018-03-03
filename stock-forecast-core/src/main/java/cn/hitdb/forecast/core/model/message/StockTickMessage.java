package cn.hitdb.forecast.core.model.message;

public class StockTickMessage extends TickMessage {

    private static final int msgType=1;

    @Override
    public String toString() {
        return "StockTickMessage={messageType:"+msgType+"\t"+super.toString()+"}";
    }
}
