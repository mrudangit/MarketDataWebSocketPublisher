package com.messaging.marketdatapublisher;

public class MarketData {

    public String symbol;
    public int symbolId;
    public double mid;

    public double askPrice0;
    public double askPrice1;
    public double askPrice2;
    public double askPrice3;
    public double askPrice4;

    public long askSize0;
    public long askSize1;
    public long askSize2;
    public long askSize3;
    public long askSize4;


    public double bidPrice0;
    public double bidPrice1;
    public double bidPrice2;
    public double bidPrice3;
    public double bidPrice4;

    public long bidSize0;
    public long bidSize1;
    public long bidSize2;
    public long bidSize3;
    public long bidSize4;

    public static MarketData createInstance(){
        MarketData m = new MarketData();
        m.symbol = "IBM";

        return m;
    }


}
