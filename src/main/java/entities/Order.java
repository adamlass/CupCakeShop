/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author adamlass
 */
public class Order {
    private int idorders;
    private User owner;
    private double price;
    private Calendar date;
    
    private List<CupCake> orderLines;

    public Order(int idorders, User owner, double price, Calendar date, List<CupCake> orderLines) {
        this.idorders = idorders;
        this.owner = owner;
        this.price = price;
        this.date = date;
        this.orderLines = orderLines;
    }

    public int getIdorders() {
        return idorders;
    }

    public User getOwner() {
        return owner;
    }

    public double getPrice() {
        return price;
    }

    public Calendar getDate() {
        return date;
    }

    public List<CupCake> getOrderLines() {
        return orderLines;
    }
    
    public String getDateFormat(){
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return  dateFormat.format(date.getTime());
    }
    
    
    
    
}
