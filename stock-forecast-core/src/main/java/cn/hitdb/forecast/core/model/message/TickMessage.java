package cn.hitdb.forecast.core.model.message;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.util.Date;

public class TickMessage extends BaseMessage{


    //市场代码,证券代码,时间,最新价,成交笔数,成交额,成交量,方向
    // ,买一价,买二价,买三价,买四价,买五价,
    // 卖一价,卖二价,卖三价,卖四价,卖五价,
    // 买一量,买二量,买三量,买四量,买五量,
    // 卖一量,卖二量,卖三量,卖四量,卖五量

    //市场代码
    @CsvBindByPosition(position = 0)
    private String marketCode;
    //证券代码
    @CsvBindByPosition(position = 1)
    private String securityCode;
    //成交时间
    @CsvBindByPosition(position = 2)
    @CsvDate("yyyy-MM-dd hh:mm:ss")
    private Date matchTime;
    //成交笔数
    @CsvBindByPosition(position = 3)
    private long matchTickCount;
    //成交额
    @CsvBindByPosition(position = 4)
    private long matchTickQuantity;
    //成交量
    @CsvBindByPosition(position = 5)
    private long matchTickAmount;
    //买卖方向
    @CsvBindByPosition(position = 6)
    private char side;

    @CsvBindByPosition(position = 7)
    private double buyPrice1;
    @CsvBindByPosition(position = 8)
    private double buyPrice2;
    @CsvBindByPosition(position = 9)
    private double buyPrice3;
    @CsvBindByPosition(position = 10)
    private double buyPrice4;
    @CsvBindByPosition(position = 11)
    private double buyPrice5;

    @CsvBindByName(column = "卖一价")
    private double sellPrice1;
    @CsvBindByName(column = "卖二价")
    private double sellPrice2;
    @CsvBindByName(column = "卖三价")
    private double sellPrice3;
    @CsvBindByName(column = "卖四价")
    private double sellPrice4;
    @CsvBindByName(column = "卖五价")
    private double sellPrice5;


    @CsvBindByName(column = "买一量")
    private double buyQuantity1;
    @CsvBindByName(column = "买二量")
    private double buyQuantity2;
    @CsvBindByName(column = "买三量")
    private double buyQuantity3;
    @CsvBindByName(column = "买四量")
    private double buyQuantity4;
    @CsvBindByName(column = "买五量")
    private double buyQuantity5;

    @CsvBindByName(column = "卖一量")
    private double sellQuantity1;
    @CsvBindByName(column = "卖二量")
    private double sellQuantity2;
    @CsvBindByName(column = "卖三量")
    private double sellQuantity3;
    @CsvBindByName(column = "卖四量")
    private double sellQuantity4;
    @CsvBindByName(column = "卖五量")
    private double sellQuantity5;



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
                ", buyPrice1=" + buyPrice1 +
                ", buyPrice2=" + buyPrice2 +
                ", buyPrice3=" + buyPrice3 +
                ", buyPrice4=" + buyPrice4 +
                ", buyPrice5=" + buyPrice5 +
                ", sellPrice1=" + sellPrice1 +
                ", sellPrice2=" + sellPrice2 +
                ", sellPrice3=" + sellPrice3 +
                ", sellPrice4=" + sellPrice4 +
                ", sellPrice5=" + sellPrice5 +
                ", buyQuantity1=" + buyQuantity1 +
                ", buyQuantity2=" + buyQuantity2 +
                ", buyQuantity3=" + buyQuantity3 +
                ", buyQuantity4=" + buyQuantity4 +
                ", buyQuantity5=" + buyQuantity5 +
                ", sellQuantity1=" + sellQuantity1 +
                ", sellQuantity2=" + sellQuantity2 +
                ", sellQuantity3=" + sellQuantity3 +
                ", sellQuantity4=" + sellQuantity4 +
                ", sellQuantity5=" + sellQuantity5 +
                '}';
    }
}
