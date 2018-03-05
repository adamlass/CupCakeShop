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
public class CupCake implements Item{
    private CakePart topping;
    private CakePart buttom;
    private String name;
    private int amount;
    
    public CupCake(CakePart topping, CakePart buttom){
        this(topping,buttom,"CupCake");
    }

    public CupCake(CakePart topping, CakePart buttom, String name) {
        this(topping,buttom,name,1);
    }
    
    public CupCake(CakePart topping, CakePart buttom, String name, int amount) {
        this.topping = topping;
        this.buttom = buttom;
        this.name = name;
        this.amount = amount;
    }
    
    

    public CakePart getTopping() {
        return topping;
    }

    public void setTopping(CakePart topping) {
        this.topping = topping;
    }

    public CakePart getButtom() {
        return buttom;
    }

    public void setButtom(CakePart buttom) {
        this.buttom = buttom;
    }
    
    
    @Override
    public double getPrice(){
        return (topping.getPrice() + buttom.getPrice())*amount;
    }

    @Override
    public String getName() {
        return name;
    }
    
    public List<CakePart> getSubParts(){
        List<CakePart> res = new ArrayList<>();
        res.add(topping);
        res.add(buttom);
        
        return res;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
    
}
