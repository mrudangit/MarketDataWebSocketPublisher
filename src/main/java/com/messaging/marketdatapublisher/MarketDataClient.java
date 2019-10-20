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

public class MarketDataClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final ChannelHandlerContext context;
    private Disposable handle;
    private ClientLoginInfo clientLoginInfo;
    private Disposable marketDataUpdatesTimer;

    public MarketDataClient(ChannelHandlerContext ctx) {
        this.context = ctx;


    }

    public void stopMarketData(){
        this.logger.info("Stoppping Market Data for : {} ", context.channel().id().asLongText());
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
        this.startPumping();
    }

    private void startPumping(){
        sendMarketDataSnapShot();
        if(this.clientLoginInfo.sendMarketDataUpdates){
            this.marketDataUpdatesTimer = Flux.interval(Duration.ofMillis(1000)).subscribe(aLong -> {
                this.sendMarketDataSnapShot();
            });

        }



    }

    private void sendMarketDataSnapShot() {

        for(int i=0;i < this.clientLoginInfo.marketDataSnapShotSize;i++){
            MarketData instance = MarketData.createInstance();
            this.logger.info("Sending Market Data : {}", instance.toString());
            this.context.channel().writeAndFlush(new BinaryWebSocketFrame(Unpooled.wrappedBuffer(instance.toBinary())));
        }

    }
}
