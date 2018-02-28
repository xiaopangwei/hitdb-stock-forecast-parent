package cn.hitdb.enrich.core.model.message;

public class QuotationTickMessage extends TickMessage {

    private static final int msgType=0;

    @Override
    public String toString() {
        return "QuotationTickMessage={messageType:\t"+msgType+super.toString()+"}";
    }
}
