package com.maktab.models;

public class TicketDto {
    private  String companyName;
    private  String busType;
    private String date;
    private String time;
    private long price;
    private  int chairReminding;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getChairReminding() {
        return chairReminding;
    }

    public void setChairReminding(int chairReminding) {
        this.chairReminding = chairReminding;
    }

    @Override
    public String toString() {
        return "TicketDto{" +
                "companyName='" + companyName + '\'' +
                ", busType='" + busType + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", price=" + price +
                ", count=" + chairReminding +
                '}';
    }
}
