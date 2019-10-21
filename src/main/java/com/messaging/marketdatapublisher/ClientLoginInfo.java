package com.messaging.marketdatapublisher;

public class ClientLoginInfo {
    @Override
    public String toString() {
        return "ClientLoginInfo{" +
                "sendMarketDataSnapShot=" + sendMarketDataSnapShot +
                ", sendMarketDataUpdates=" + sendMarketDataUpdates +
                ", numOfMarketDataRecords=" + numOfMarketDataRecords +
                ", updatePercentage=" + updatePercentage +
                ", marketDataSnapShotSize=" + marketDataSnapShotSize +
                ", sendOrders=" + sendOrders +
                ", numOfNewOrders=" + numOfNewOrders +
                ", newOrderInterval=" + newOrderInterval +
                '}';
    }

    public boolean sendMarketDataSnapShot;
    public boolean sendMarketDataUpdates;
    public long numOfMarketDataRecords;
    public int  updatePercentage;
    public int  marketDataSnapShotSize;
    public int updateFrequency;


    public boolean sendOrders;
    public int numOfNewOrders;
    public int newOrderInterval;




}
