package cn.hitdb.core.model.message;

import org.apache.flink.api.java.tuple.Tuple5;

import java.util.Date;

public class TickMessage extends BaseMessage{

    //市场代码
    private String marketCode;
    //证券代码
    private String securityCode;
    //成交时间
    private Date matchTime;
    //成交笔数
    private long matchTickCount;
    //成交额
    private long matchTickQuantity;
    //成交量
    private long matchTickAmount;
    //买卖方向
    private char side;
    //买1到5价格
    private Tuple5<Double,Double,Double,Double,Double> buyPrices;
    //卖1到5价格
    private Tuple5<Double,Double,Double,Double,Double> sellPrices;
    //买1到5总量
    private Tuple5<Integer,Integer,Integer,Integer,Integer> buyQuantity;
    //卖1到5总量
    private Tuple5<Integer,Integer,Integer,Integer,Integer> sellQuantity;


    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public long getMatchTickCount() {
        return matchTickCount;
    }

    public void setMatchTickCount(long matchTickCount) {
        this.matchTickCount = matchTickCount;
    }

    public long getMatchTickQuantity() {
        return matchTickQuantity;
    }

    public void setMatchTickQuantity(long matchTickQuantity) {
        this.matchTickQuantity = matchTickQuantity;
    }

    public long getMatchTickAmount() {
        return matchTickAmount;
    }

    public void setMatchTickAmount(long matchTickAmount) {
        this.matchTickAmount = matchTickAmount;
    }

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public Tuple5<Double, Double, Double, Double, Double> getBuyPrices() {
        return buyPrices;
    }

    public void setBuyPrices(Tuple5<Double, Double, Double, Double, Double> buyPrices) {
        this.buyPrices = buyPrices;
    }

    public Tuple5<Double, Double, Double, Double, Double> getSellPrices() {
        return sellPrices;
    }

    public void setSellPrices(Tuple5<Double, Double, Double, Double, Double> sellPrices) {
        this.sellPrices = sellPrices;
    }

    public Tuple5<Integer, Integer, Integer, Integer, Integer> getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Tuple5<Integer, Integer, Integer, Integer, Integer> buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Tuple5<Integer, Integer, Integer, Integer, Integer> getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Tuple5<Integer, Integer, Integer, Integer, Integer> sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    @Override
    public String toString() {
        return "TickMessage{" +
                "marketCode='" + marketCode + '\'' +
                ", securityCode='" + securityCode + '\'' +
                ", matchTime=" + matchTime +
                ", matchTickCount=" + matchTickCount +
                ", matchTickQuantity=" + matchTickQuantity +
                ", matchTickAmount=" + matchTickAmount +
                ", side=" + side +
                ", buyPrices=" + buyPrices +
                ", sellPrices=" + sellPrices +
                ", buyQuantity=" + buyQuantity +
                ", sellQuantity=" + sellQuantity +
                '}';
    }
}
