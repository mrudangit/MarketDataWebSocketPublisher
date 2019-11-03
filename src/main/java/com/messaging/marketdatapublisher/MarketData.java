package com.messaging.marketdatapublisher;

import org.apache.commons.lang3.RandomStringUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Random;

public class MarketData {

    private static long counter=0;
    private static Random random = new Random();

    public static final int SIZE = 236;

    public final int index; // 4


    public String symbol;   // 40
    public long symbolId;   // 8
    public double mid;      // 8

    public double askPrice0;    // 5*8 = 40
    public double askPrice1;
    public double askPrice2;
    public double askPrice3;
    public double askPrice4;

    public long askSize0;  // 5*8 = 40
    public long askSize1;
    public long askSize2;
    public long askSize3;
    public long askSize4;


    public double bidPrice0; // 5*8 = 40
    public double bidPrice1;
    public double bidPrice2;
    public double bidPrice3;
    public double bidPrice4;

    public long bidSize0;   // 5*8 = 40
    public long bidSize1;
    public long bidSize2;
    public long bidSize3;
    public long bidSize4;

    public long revisionId;

    public double spread;


    public MarketData(int index){
        this.index = index;
    }



    @Override
    public String toString() {
        return "MarketData{" +
                "symbol='" + symbol + '\'' +
                ", symbolId=" + symbolId +
                ", mid=" + mid +
                ", askPrice0=" + askPrice0 +
                ", askPrice1=" + askPrice1 +
                ", askPrice2=" + askPrice2 +
                ", askPrice3=" + askPrice3 +
                ", askPrice4=" + askPrice4 +
                ", askSize0=" + askSize0 +
                ", askSize1=" + askSize1 +
                ", askSize2=" + askSize2 +
                ", askSize3=" + askSize3 +
                ", askSize4=" + askSize4 +
                ", bidPrice0=" + bidPrice0 +
                ", bidPrice1=" + bidPrice1 +
                ", bidPrice2=" + bidPrice2 +
                ", bidPrice3=" + bidPrice3 +
                ", bidPrice4=" + bidPrice4 +
                ", bidSize0=" + bidSize0 +
                ", bidSize1=" + bidSize1 +
                ", bidSize2=" + bidSize2 +
                ", bidSize3=" + bidSize3 +
                ", bidSize4=" + bidSize4 +
                '}';
    }




    public byte[] toBinary(){
        ByteBuffer buffer = ByteBuffer.allocate(MarketData.SIZE);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.putInt(this.index);
        byte[] bytes = this.symbol.getBytes();
        buffer.put(bytes);
        buffer.putLong(this.symbolId);
        buffer.putDouble(this.mid);

        buffer.putDouble(this.askPrice0);
        buffer.putDouble(this.askPrice1);
        buffer.putDouble(this.askPrice2);
        buffer.putDouble(this.askPrice3);
        buffer.putDouble(this.askPrice4);

        buffer.putDouble(this.bidPrice0);
        buffer.putDouble(this.bidPrice1);
        buffer.putDouble(this.bidPrice2);
        buffer.putDouble(this.bidPrice3);
        buffer.putDouble(this.bidPrice4);

        buffer.putLong(this.askSize0);
        buffer.putLong(this.askSize1);
        buffer.putLong(this.askSize2);
        buffer.putLong(this.askSize3);
        buffer.putLong(this.askSize4);

        buffer.putLong(this.bidSize0);
        buffer.putLong(this.bidSize1);
        buffer.putLong(this.bidSize2);
        buffer.putLong(this.bidSize3);
        buffer.putLong(this.bidSize4);

        buffer.putLong(this.revisionId);
        buffer.putDouble(this.spread);

        return buffer.array();

    }

    public static String fixedLengthString(String string, int length) {
        return   String.format("%-" + length + "." + length + "s", string);
    }

    public static MarketData createInstance(int index) {
        MarketData m = new MarketData(index);
        m.symbol = RandomStringUtils.randomAlphabetic(40);
        m.symbolId = Math.abs(random.nextLong());
        m.mid      = 100*random.nextDouble();

        m.askPrice0 = 100*random.nextDouble();
        m.askPrice1 = 100*random.nextDouble();
        m.askPrice2 = 100*random.nextDouble();
        m.askPrice3 = 100*random.nextDouble();
        m.askPrice4 = 100*random.nextDouble();

        m.bidPrice0 = 100*random.nextDouble();
        m.bidPrice1 = 100*random.nextDouble();
        m.bidPrice2 = 100*random.nextDouble();
        m.bidPrice3 = 100*random.nextDouble();
        m.bidPrice4 = 100*random.nextDouble();

        m.askSize0 = (long) (100*random.nextDouble());
        m.askSize1 = (long) (100*random.nextDouble());
        m.askSize2 = (long) (100*random.nextDouble());
        m.askSize3 = (long) (100*random.nextDouble());
        m.askSize4 = (long) (100*random.nextDouble());

        m.bidSize0 = (long) (100*random.nextDouble());
        m.bidSize1 = (long) (100*random.nextDouble());
        m.bidSize2 = (long) (100*random.nextDouble());
        m.bidSize3 = (long) (100*random.nextDouble());
        m.bidSize4 = (long) (100*random.nextDouble());

        m.revisionId = 0;

        m.spread = 100*random.nextDouble();


        return m;
    }


    public void generateMarketData(){
        MarketData m = this;


        m.askPrice0 = 100*random.nextDouble();
        m.askPrice1 = 100*random.nextDouble();
        m.askPrice2 = 100*random.nextDouble();
        m.askPrice3 = 100*random.nextDouble();
        m.askPrice4 = 100*random.nextDouble();

        m.bidPrice0 = 100*random.nextDouble();
        m.bidPrice1 = 100*random.nextDouble();
        m.bidPrice2 = 100*random.nextDouble();
        m.bidPrice3 = 100*random.nextDouble();
        m.bidPrice4 = 100*random.nextDouble();

        m.askSize0 = (long) (100*random.nextDouble());
        m.askSize1 = (long) (100*random.nextDouble());
        m.askSize2 = (long) (100*random.nextDouble());
        m.askSize3 = (long) (100*random.nextDouble());
        m.askSize4 = (long) (100*random.nextDouble());

        m.bidSize0 = (long) (100*random.nextDouble());
        m.bidSize1 = (long) (100*random.nextDouble());
        m.bidSize2 = (long) (100*random.nextDouble());
        m.bidSize3 = (long) (100*random.nextDouble());
        m.bidSize4 = (long) (100*random.nextDouble());

        m.revisionId++;
        m.mid      = ((bidPrice0 + askPrice0) / 2);

        m.spread = askPrice0 - bidPrice0;

    }


}
