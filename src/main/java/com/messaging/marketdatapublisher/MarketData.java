package com.messaging.marketdatapublisher;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.RandomStringUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

public class MarketData {


    public static final int  INDEX_OFFSET=0;
    public static final int SYMBOL_OFFSET=4;
    public static final int SYMBOL_ID_OFFSET=44;
    public static final int MID_OFFSET=52;

    public static final int ASKPRICE0_OFFSET=60;
    public static final int ASKPRICE1_OFFSET=68;
    public static final int ASKPRICE2_OFFSET=76;
    public static final int ASKPRICE3_OFFSET=84;
    public static final int ASKPRICE4_OFFSET=92;

    public static final int BIDPRICE0_OFFSET=100;
    public static final int BIDPRICE1_OFFSET=108;
    public static final int BIDPRICE2_OFFSET=116;
    public static final int BIDPRICE3_OFFSET=124;
    public static final int BIDPRICE4_OFFSET=132;

    public static final int ASKSIZE0_OFFSET=140;
    public static final int ASKSIZE1_OFFSET=148;
    public static final int ASKSIZE2_OFFSET=156;
    public static final int ASKSIZE3_OFFSET=164;
    public static final int ASKSIZE4_OFFSET=172;


    public static final int BIDSIZE0_OFFSET=180;
    public static final int BIDSIZE1_OFFSET=188;
    public static final int BIDSIZE2_OFFSET=196;
    public static final int BIDSIZE3_OFFSET=204;
    public static final int BIDSIZE4_OFFSET=212;


    public static final int REVISIONID_OFFSET = 220;
    public static final int SPREAD_OFFSET = 228;

    private static long counter=0;
    private static Random random = new Random();

    public static final int SIZE = 236;

    public final int index; // 4
    private final ByteBuf marketDataBuffer;


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
        this.marketDataBuffer = Unpooled.buffer(MarketData.SIZE, MarketData.SIZE);

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

    public byte[] toBinaryByteBuf(){

        this.marketDataBuffer.setIntLE(INDEX_OFFSET,index);
        this.marketDataBuffer.setBytes(SYMBOL_OFFSET, this.symbol.getBytes());
        this.marketDataBuffer.setLongLE(SYMBOL_ID_OFFSET, index);

        this.marketDataBuffer.setDoubleLE(MID_OFFSET, mid);

        this.marketDataBuffer.setDoubleLE(ASKPRICE0_OFFSET, askPrice0);
        this.marketDataBuffer.setDoubleLE(ASKPRICE1_OFFSET, askPrice1);
        this.marketDataBuffer.setDoubleLE(ASKPRICE2_OFFSET, askPrice2);
        this.marketDataBuffer.setDoubleLE(ASKPRICE3_OFFSET, askPrice3);
        this.marketDataBuffer.setDoubleLE(ASKPRICE4_OFFSET, askPrice4);

        this.marketDataBuffer.setDoubleLE(BIDPRICE0_OFFSET, bidPrice0);
        this.marketDataBuffer.setDoubleLE(BIDPRICE1_OFFSET, bidPrice1);
        this.marketDataBuffer.setDoubleLE(BIDPRICE2_OFFSET, bidPrice2);
        this.marketDataBuffer.setDoubleLE(BIDPRICE3_OFFSET, bidPrice3);
        this.marketDataBuffer.setDoubleLE(BIDPRICE4_OFFSET, bidPrice4);


        this.marketDataBuffer.setLongLE(ASKSIZE0_OFFSET, askSize0);
        this.marketDataBuffer.setLongLE(ASKSIZE1_OFFSET, askSize1);
        this.marketDataBuffer.setLongLE(ASKSIZE2_OFFSET, askSize2);
        this.marketDataBuffer.setLongLE(ASKSIZE3_OFFSET, askSize3);
        this.marketDataBuffer.setLongLE(ASKSIZE4_OFFSET, askSize4);

        this.marketDataBuffer.setLongLE(BIDSIZE0_OFFSET, bidSize0);
        this.marketDataBuffer.setLongLE(BIDSIZE1_OFFSET, bidSize1);
        this.marketDataBuffer.setLongLE(BIDSIZE2_OFFSET, bidSize2);
        this.marketDataBuffer.setLongLE(BIDSIZE3_OFFSET, bidSize3);
        this.marketDataBuffer.setLongLE(BIDSIZE4_OFFSET, bidSize4);

        this.marketDataBuffer.setLongLE(REVISIONID_OFFSET, revisionId);
        this.marketDataBuffer.setDoubleLE(SPREAD_OFFSET, spread);



        return this.marketDataBuffer.array();
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
