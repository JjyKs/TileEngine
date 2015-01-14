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
public class MagicHelmet extends Item {
    public MagicHelmet(){
        texture = 448;
        usable = false;
        equipable = true;
        selected = false;
        text = ""
            + "Magic helmet                  "
            + "This magic helmet is crafted  "
            + "and enchanted by the greatest "
            + "blacksmiths in Aladuom.       ";
    }    
}