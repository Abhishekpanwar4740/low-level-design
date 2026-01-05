package com.example.demo.bookMyShow;

import java.util.ArrayList;
import java.util.List;

public class Screen {
    int screenId;
    List<Seat> seatList=new ArrayList<>();

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }
}
