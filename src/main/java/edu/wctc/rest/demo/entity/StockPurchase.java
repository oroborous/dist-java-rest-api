package edu.wctc.rest.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockPurchase {
    private String name;
    private String symbol;
    private double sharePrice;
    private int numShares;

    public StockPurchase(String name, String symbol, double sharePrice, int numShares) {
        this.name = name;
        this.symbol = symbol;
        this.sharePrice = sharePrice;
        this.numShares = numShares;
    }

    @Override
    public String toString() {
        return "StockPurchase{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", sharePrice=" + sharePrice +
                ", numShares=" + numShares +
                '}';
    }
}
