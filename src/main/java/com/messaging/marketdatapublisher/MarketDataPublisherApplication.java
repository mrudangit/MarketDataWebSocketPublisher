package com.messaging.marketdatapublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.Charset;

@SpringBootApplication
public class MarketDataPublisherApplication {

    private static Logger logger = LoggerFactory.getLogger(MarketDataPublisherApplication.class.getName());

    public static void main(String[] args) {


        Charset.availableCharsets().forEach((s, charset) -> {
            logger.info(" Name : {}  Set : {}", s, charset);
        });

        logger.info("Default CharSet : {}", Charset.defaultCharset());
        SpringApplication.run(MarketDataPublisherApplication.class, args);
    }

}
