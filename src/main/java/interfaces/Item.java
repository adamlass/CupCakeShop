/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

/**
 *
 * @author adamlass
 */
public interface Item {
    public double getPrice();
    public String getName();
    public int getAmount();
    public void setAmount(int amount);
}
