/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparators;

import entities.User;
import java.util.Comparator;

/**
 *
 * @author adamlass
 */
public class usernamecomparator implements Comparator<User>{

    @Override
    public int compare(User o1, User o2) {
        return o1.getName().compareTo(o2.getName());
    }

    
}
