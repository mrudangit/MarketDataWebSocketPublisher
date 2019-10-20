package com.messaging.marketdatapublisher;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;


public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private ConcurrentHashMap<String , MarketDataClient> clients = new ConcurrentHashMap<>();
    private Gson gson = new Gson();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {

        this.logger.info("Web Socket Client Connected : {} ", ctx.channel().id().asLongText());
        MarketDataClient marketDataClient = new MarketDataClient(ctx);
        this.clients.put(ctx.channel().id().asLongText(), marketDataClient);

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {

        this.logger.info("Web Socket Client Dis-Connected : {} ", ctx.channel().id().asLongText());
        MarketDataClient removed = this.clients.remove(ctx.channel().id().asLongText());
        removed.stopMarketData();


    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {


        if(frame instanceof CloseWebSocketFrame)
        {
            String reasonText = ((CloseWebSocketFrame) frame).reasonText();
            this.logger.info("WebSocket Connection Closed : {}", reasonText );

        }

        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            this.logger.info("Message From Client : {}", request);
            String clientId = ctx.channel().id().asLongText();
            ClientLoginInfo clientLoginInfo = gson.fromJson(request, ClientLoginInfo.class);
            this.logger.info("Client : {} Login Info : {} ", clientId, clientLoginInfo);
            MarketDataClient marketDataClient = this.clients.get(clientId);
            marketDataClient.Login(clientLoginInfo);


        }

        if(frame instanceof BinaryWebSocketFrame)
        {
            ByteBuf content = ((BinaryWebSocketFrame) frame).content();
            this.logger.info("Binary Data String  : {}", content.toString(Charset.defaultCharset()));
            ctx.channel().writeAndFlush(new TextWebSocketFrame("Sample Data".toUpperCase(Locale.US)));

        }
    }

}
