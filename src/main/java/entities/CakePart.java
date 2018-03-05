/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author adamlass
 */
public class CakePart {

    private int dbIndex;
    private String name;
    private double price;

    public CakePart(int dbIndex, String name, double price) {
        this.dbIndex = dbIndex;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getDbIndex() {
        return dbIndex;
    }
    
    
    
    
}
