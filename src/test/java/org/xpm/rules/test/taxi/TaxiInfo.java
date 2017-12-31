package org.xpm.rules.test.taxi;

import org.xpm.rules.RuleContext;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xupingmao on 2017/11/30.
 */
public class TaxiInfo extends RuleContext {

    private Date startTime;

    private double distance;

    private BigDecimal startingPrice;
    private BigDecimal overPrice;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public BigDecimal getOverPrice() {
        return overPrice;
    }

    public void setOverPrice(BigDecimal overPrice) {
        this.overPrice = overPrice;
    }
}
