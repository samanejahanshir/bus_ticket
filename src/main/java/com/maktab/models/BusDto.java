package com.maktab.models;

public class BusDto {
    private long ticketSale;
    private  String companyName;
    private int chairReminding;
    private BusType type;
    private  String date;

    public long getTicketSale() {
        return ticketSale;
    }

    public void setTicketSale(long ticketSale) {
        this.ticketSale = ticketSale;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getChairReminding() {
        return chairReminding;
    }

    public void setChairReminding(int chairReminding) {
        this.chairReminding = chairReminding;
    }

    public BusType getType() {
        return type;
    }

    public void setType(BusType type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "BusDto{" +
                "ticketSale=" + ticketSale +
                ", companyName='" + companyName + '\'' +
                ", chairReminding=" + chairReminding +
                ", type=" + type +
                ", date='" + date + '\'' +
                '}';
    }
}
