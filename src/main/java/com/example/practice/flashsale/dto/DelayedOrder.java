package com.example.practice.flashsale.dto;

import lombok.Data;

import java.util.concurrent.Delayed;
@Data
public class DelayedOrder implements Delayed {
    private Order order;
    private long expireTime;

    public DelayedOrder(Order order, long expireTime) {
        this.order = order;
        this.expireTime = System.currentTimeMillis()+expireTime;
    }

    @Override
    public long getDelay(java.util.concurrent.TimeUnit unit) {
        long diff = expireTime - System.currentTimeMillis();
        return unit.convert(diff, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
      return Long.compare(this.expireTime, ((DelayedOrder) o).expireTime);
    }
}
