package com.maktab.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Entity
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private BusType type;
    private String company;
    private int countChair;
    @ElementCollection
    private Map<Integer,StateChair> chairMap=new HashMap<>();
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "bus")
    private List<Ticket> tickets=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BusType getType() {
        return type;
    }

    public void setType(BusType type) {
        this.type = type;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getCountChair() {
        return countChair;
    }

    public void setCountChair(int countChair) {
        this.countChair = countChair;
    }

    public Map<Integer, StateChair> getChairMap() {
        return chairMap;
    }

    public void setChairMap(Map<Integer, StateChair> chairMap) {
        this.chairMap = chairMap;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", type=" + type +
                ", company='" + company + '\'' +
                ", countChair=" + countChair +
                ", chairMap=" + chairMap +
                ", tickets=" + tickets +
                '}';
    }
}
