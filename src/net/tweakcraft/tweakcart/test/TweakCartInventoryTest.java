/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tweakcraft.tweakcart.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 *
 * @author lennart
 */
public class TweakCartInventoryTest {

    public Map<MaterialData, Integer> countContentsOfInventory(ItemStack[] inf) {
        Map<MaterialData, Integer> result = new HashMap<MaterialData, Integer>();
        for (ItemStack stack : inf) {
            int amount = result.get(new MaterialData(stack.getType(), stack.getData().getData())) == null ? 0 : result.get(new MaterialData(stack.getType(), stack.getData().getData()));
            if (amount != 0) {
                //get the item, add amount
                result.remove(new MaterialData(stack.getType(), stack.getData().getData()));
                result.put(new MaterialData(stack.getType(), stack.getData().getData()), amount + stack.getAmount());
            } else {
                result.put(new MaterialData(stack.getType(), stack.getData().getData()), stack.getAmount());
            }
        }
        return result;
    }

    public boolean compareInventories(ItemStack[] inf1, ItemStack[] inf2) {
        Map<MaterialData, Integer> inf1map = countContentsOfInventory(inf1);
        Map<MaterialData, Integer> inf2map = countContentsOfInventory(inf2);

        return inf1map.equals(inf2map);
    }

    public void printFormatted(ItemStack[] inf) {
        Map<MaterialData, Integer> infmap = countContentsOfInventory(inf);

        for (Entry<MaterialData, Integer> entry : infmap.entrySet()) {
            System.out.println(String.format("Material: %d, Data: %d, Amount: %d", entry.getKey().getItemTypeId(), entry.getKey().getData(), entry.getValue()));
        }
    }

    public static void main(String[] args) {
        new TweakCartInventoryTest();
    }

    public TweakCartInventoryTest() {
        test();
    }

    private void test() {
        ItemStack[] inv = {new ItemStack(2, 64), new ItemStack(1, 64), new ItemStack(1, 64)};
        ItemStack[] inv2 = {new ItemStack(1, 32), new ItemStack(1, 32), new ItemStack(1, 32), new ItemStack(1, 32), new ItemStack(1, 32), new ItemStack(1, 32)};

        printFormatted(inv);
        printFormatted(inv2);

        System.out.println(compareInventories(inv, inv2));

    }
}
