package com.messaging.marketdatapublisher;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

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
        this.createMarketDataRecords();
        this.startPumping();
    }

    private void createMarketDataRecords() {
        for(int i =0; i < this.clientLoginInfo.marketDataSnapShotSize;i ++){
            MarketData instance = MarketData.createInstance();
            this.marketDataSnapShot.put(instance.symbolId, instance);
        }

        this.marketDataArray = this.marketDataSnapShot.values().toArray();
    }

    private void startPumping(){
        sendMarketDataSnapShot();
        if(this.clientLoginInfo.sendMarketDataUpdates){

            this.marketDataUpdatesTimer = Flux.interval(Duration.ofMillis(this.clientLoginInfo.updateFrequency)).subscribe(aLong -> {
                this.sendMarketDataUpdates();
            });

        }



    }

    private void sendMarketDataSnapShot() {


        this.marketDataSnapShot.values().forEach(marketData -> {
            this.context.channel().writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(marketData.toBinary())));
        });

    }

    private void sendMarketDataUpdates() {


        for(int i=0;i < this.numOfUpdates;i++){

            MarketData instance = (MarketData) this.marketDataArray[random.nextInt(this.marketDataArray.length)];
            instance.generateMarketData();
            this.logger.info("Sending Market Data : {}", instance.toString());
            this.context.channel().writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(instance.toBinary())));
        }

    }
}
