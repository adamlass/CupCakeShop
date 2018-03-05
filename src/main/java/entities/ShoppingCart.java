/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import interfaces.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adamlass
 */
public class ShoppingCart {
    private User owner;
    private List<Item> items;
    
    public ShoppingCart(){
        owner = null;
        items = new ArrayList<>();
    }
    
    public ShoppingCart(List<Item> items){
        this(null,items);
    }

    public ShoppingCart(User owner, List<Item> items) {
        this.owner = owner;
        this.items = items;
    }

    public User getOwner() {
        return owner;
    }

    public List<Item> getItems() {
        return items;
    }
    public void addItem(Item item){
        items.add(item);
    }
    public List<Item> removeItem(Item item){
        try {
            items.remove(item);
            return items;
        } catch (Exception e) {
            throw e;
        }
    }
    public boolean removeAllItems(List<Item> items){
        try {
            items.removeAll(items);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public double getPrice(){
        double res = 0;
        for(Item item : items){
            res += item.getPrice();
        }
        return res;
    }
}
