/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tileengine.items;

/**
 *
 * @author Jyri
 */
public class Kissa extends Item {
    public Kissa(){
        texture = 417;
        usable = false;
        equipable = false;
        selected = false;
        text = ""
            + "White cat                     "
            + "This lovely cat is utterly    "
            + "useless,but it is soft though."
            + "                              ";
    }    
}
