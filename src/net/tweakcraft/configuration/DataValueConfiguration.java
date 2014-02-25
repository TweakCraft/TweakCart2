/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tweakcraft.configuration;

import java.util.List;

/**
 *
 * @author lennart
 */
public class DataValueConfiguration {
    private final DataValueConfigurationAccessor dac;
    
    private List<Integer> eggdatas;
    private List<Integer> potiondatas;
        
    public DataValueConfiguration(DataValueConfigurationAccessor dac){
        this.dac = dac;
    }
    
    public void readEggData(){
        eggdatas = this.dac.getConfig().getIntegerList("eggdatas");
    }
    
    public void readPotionData(){
        potiondatas = this.dac.getConfig().getIntegerList("potiondatas");
    }
}
