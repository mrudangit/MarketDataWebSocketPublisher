package com.messaging.marketdatapublisher;

import io.netty.channel.ChannelHandlerContext;
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

    public MarketDataClient(ChannelHandlerContext ctx) {
        this.context = ctx;
        this.startMarketData();

    }

    public void stopMarketData(){
        this.logger.info("Stoppping Market Data for : {} ", context.channel().id().asLongText());
        this.handle.dispose();
    }


    private void  startMarketData(){
        handle = Flux.interval(Duration.ofMillis(1000)).subscribe(aLong -> {
            this.context.channel().writeAndFlush(new TextWebSocketFrame("Sample Data".toUpperCase(Locale.US)));
        });
    }
}
