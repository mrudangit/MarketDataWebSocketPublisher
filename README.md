# MarketDataWebSocketPublisher
Publishes large volume of market data to connected client

Test Program to consume test market data in Javascript Client

WebSocket Path : host:port/marketData


Connect via javascript WebSocket 
After connection send Login Info JSON 

```
export class LoginInfo{

  public sendMarketDataSnapShot = false;
  public sendMarketDataUpdates= false;
  public numOfMarketDataRecords = 0;
  public updatePercentage = 0;
  public marketDataSnapShotSize= 0;
  public updateFrequency = 100;
  public sendOrders= false;
  public numOfNewOrders= 0;
  public newOrderInterval= 0
}
```


User sister repo to consume the data

https://github.com/mrudangit/MarketMonitor

Happy Coding




