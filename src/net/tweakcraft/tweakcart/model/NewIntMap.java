/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.tweakcraft.tweakcart.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lennart
 */
public class NewIntMap {
    public class IntMapEntry{
        private int id;
        private byte data;
        private int amount;
        
        public IntMapEntry(int id, byte data, int amount){
            this.id = id;
            this.data = data;
            this.amount = amount;
        }
        
        public int getId(){
            return id;
        }
        
        public byte getData(){
            return data;
        }
        
        public int getAmount(){
            return amount;
        }
        
        
        public void setAmount(int amount){
            this.amount = amount;
        }
        
        public void decrementAmount(int dec){
            amount = amount - dec;
        }
        
        public void incrementAmount(int inc){
            amount = amount + inc;
        }

        @Override
        public boolean equals(Object other){
            return other instanceof IntMapEntry && ((IntMapEntry) other).data == data && ((IntMapEntry) other).id == id;
        }

        @Override
        public int hashCode() {
            //Generated no clue
            int hash = 7;
            hash = 83 * hash + this.id;
            hash = 83 * hash + this.data;
            return hash;
        }
    }
    
    private List<IntMapEntry> entryList = new ArrayList<IntMapEntry>();
    
    public NewIntMap(){     
    }
    
    public List<IntMapEntry> getEntryList(){
        return entryList;
    }
    
    public void addEntry(IntMapEntry ent){
        if(entryList.contains(ent)){
            //hij bevat dit element al :(
            //optellen dan maar
            IntMapEntry mapEnt = entryList.get(entryList.indexOf(ent));
            mapEnt.setAmount(Math.max(mapEnt.getAmount() + ent.getAmount(), Integer.MAX_VALUE));
        }else{
           entryList.add(ent); 
        }
    }
    
    public void addRange(int startID, int endID, byte startData, byte endData, int amount){
        //err tja, ik wil graag dat je geen items hoeft te specificeren, dan
        //hoeven we het niet steeds te patchen
        if(startID < endID){
            //Vul vanaf de begindata tot 15 de lijst met nieuwe IntMapEntry's
            for(byte i = startData; i < 15; i++){
                entryList.add(new IntMapEntry(startID, i, amount));
            }
            //Vul daartussen de lijst met -1 entries (alles dus)
            for(int j = startID + 1; j < endID - 1; j++){
                entryList.add(new IntMapEntry(j, (byte)-1, amount));
            }
            //Vul het laastste stukje tot aan de endData
            //TODO: moet dit een > of een >= zijn?
            for(byte k = endData; k > 0; k--){
                entryList.add(new IntMapEntry(endID, k, amount));
            }
            
        }
    }
    
    
    
    
    
}
