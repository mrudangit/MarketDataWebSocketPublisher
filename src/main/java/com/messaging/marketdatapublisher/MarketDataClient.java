package com.messaging.marketdatapublisher;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class MarketDataClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final ChannelHandlerContext context;
    private Disposable handle;
    private ClientLoginInfo clientLoginInfo;
    private Disposable marketDataUpdatesTimer;
    private ConcurrentHashMap<Long, MarketData> marketDataSnapShot = new ConcurrentHashMap<>();
    private int numOfUpdates;
    private Random random = new Random();
    private Object[] marketDataArray;


    public MarketDataClient(ChannelHandlerContext ctx) {
        this.context = ctx;
    }

    public void stopMarketData(){
        this.logger.info("Stopping Market Data for : {} ", context.channel().id().asLongText());
        if( this.handle != null) {
            this.handle.dispose();
        }
        if( this.marketDataUpdatesTimer != null){
            this.marketDataUpdatesTimer.dispose();
        }
    }


    private void  startMarketData(){
        handle = Flux.interval(Duration.ofMillis(1000)).subscribe(aLong -> {
            this.context.channel().writeAndFlush(new TextWebSocketFrame("Sample Data".toUpperCase(Locale.US)));
        });
    }

    public void Login(ClientLoginInfo clientLoginInfo) {

        this.clientLoginInfo = clientLoginInfo;
        this.numOfUpdates = (int) Math.floor ((this.clientLoginInfo.marketDataSnapShotSize*this.clientLoginInfo.updatePercentage)/100.0);
        this.logger.info("Number of Update = {}", this.numOfUpdates);
        this.createMarketDataRecords();
        this.startPumping();
    }

    private void createMarketDataRecords() {
        for(int i =0; i < this.clientLoginInfo.marketDataSnapShotSize;i ++){
            MarketData instance = MarketData.createInstance(i);
            this.marketDataSnapShot.put(instance.symbolId, instance);
        }

        this.marketDataArray = this.marketDataSnapShot.values().toArray();
    }

    private void startPumping(){
        sendMarketDataSnapShot();


        if(this.clientLoginInfo.sendMarketDataUpdates){

            this.marketDataUpdatesTimer = Flux.interval(Duration.ofMillis(2000), Duration.ofMillis(this.clientLoginInfo.updateFrequency)).subscribe(aLong -> {
                this.sendMarketDataUpdates();
            });

        }



    }

    private void sendMarketDataSnapShot() {

        ByteBuf buffer = Unpooled.buffer (this.clientLoginInfo.marketDataSnapShotSize*MarketData.SIZE,this.clientLoginInfo.marketDataSnapShotSize*MarketData.SIZE);
        this.marketDataSnapShot.values().forEach(marketData -> {
            marketData.generateMarketData();
            buffer.writeBytes(marketData.toBinaryByteBuf());
        });
        this.context.channel().writeAndFlush(new BinaryWebSocketFrame(buffer));

        assert buffer.refCnt() == 0;



    }

    private void sendMarketDataUpdates() {

        ByteBuf buffer = Unpooled.buffer (this.numOfUpdates*MarketData.SIZE,this.numOfUpdates*MarketData.SIZE);



        for(int i=0;i < this.numOfUpdates;i++){

            MarketData instance = (MarketData) this.marketDataArray[random.nextInt(this.marketDataArray.length)];
            instance.generateMarketData();
            buffer.writeBytes(instance.toBinaryByteBuf());

        }
        this.context.channel().writeAndFlush(new BinaryWebSocketFrame(buffer));

        assert buffer.refCnt() == 0;
    }
}
