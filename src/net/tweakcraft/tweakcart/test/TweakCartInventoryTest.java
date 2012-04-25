/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tweakcraft.tweakcart.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.MaterialData;

/**
 *
 * @author lennart
 */
public class TweakCartInventoryTest {

    public Map<MaterialData, Integer> countContentsOfInventory(Inventory inf) {
        Map<MaterialData, Integer> result = new HashMap<MaterialData, Integer>();
        ItemStack[] stacks = inf.getContents();
        for (ItemStack stack : stacks) {
            int amount;
            if ((amount = result.get(new MaterialData(stack.getType(), stack.getData().getData()))) != 0) {
                //get the item, add amount
                result.remove(new MaterialData(stack.getType(), stack.getData().getData()));
                result.put(new MaterialData(stack.getType(), stack.getData().getData()), amount + stack.getAmount());
            }
        }
        return result;
    }

    public boolean compareInventories(Inventory inf1, Inventory inf2) {
        Map<MaterialData, Integer> inf1map = countContentsOfInventory(inf1);
        Map<MaterialData, Integer> inf2map = countContentsOfInventory(inf2);

        return inf1map.equals(inf2map);
    }
    
    public void printFormatted(Inventory inf){
        Map<MaterialData, Integer> infmap = countContentsOfInventory(inf);
        
        for(Entry<MaterialData, Integer> entry : infmap.entrySet()){
            System.out.println(String.format("Material: %d, Data: %d, Amount: %d", entry.getKey().getItemTypeId(), entry.getKey().getData(), entry.getValue()));
        }
    }
}
