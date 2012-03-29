
import java.util.ArrayList;
import java.util.List;
import net.tweakcraft.tweakcart.model.IntMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lennart
 */
public class IntMapTest {
    public static void main(String[] args){
        IntMap map = new IntMap();
        List<Integer> duplicates = new ArrayList<Integer>();
        boolean dups = false;
        for(int i = 0; i < 4000; i++){
            if(map.hasDataValue(i)){
                for(byte j = 0; j < 15; j++){
                    int pos = IntMap.getIntIndex(i, j);
                    if(pos != -1){
                        System.out.println(i + " " + j + " " + pos);
                        if(duplicates.contains(pos)){
                        dups = true;
                        System.out.println("Problem at pos: " + pos);
                        }
                        else{
                            duplicates.add(pos);
                        }
                    }
                    
                    
                }
            }
        }
        
        if(dups){
            System.out.println("ohjee" + IntMap.materialSize);
        }else{
            System.out.println("oke");
        }
    }
}

