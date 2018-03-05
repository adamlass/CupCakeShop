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
public class User {

    private String name;
    private String password;
    private boolean admin;
    private double balance;
    private ShoppingCart shoppingCart;

    public User(String name, String password) {
        this(name,password,false,0);
    }
    
    public User(String name, String password, boolean admin) {
        this(name, password, admin, 0);
    }

    public User(String name, String password, boolean admin, double balance) {
        this(name,password,admin,balance,new ShoppingCart());
    }
    
    public User(String name, String password, boolean admin, double balance,ShoppingCart shoppingCart) {
        this.name = name;
        this.password = password;
        this.admin = admin;
        this.balance = balance;
        this.shoppingCart = shoppingCart;
    }
    
    

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public double getBalance() {
        return balance;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }
    
    public String getColor(){
        String res = "black";
        if(balance < 0){
            res = "red";
        } else if(balance > 0){
            res = "green";
        }
        return res;
    }


}
