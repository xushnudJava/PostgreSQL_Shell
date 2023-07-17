package imtihon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class masala2 {
    public static void main(String[] args) {
        List<Integer>list = new ArrayList<>();
        System.out.println("Saralanmagan to'plam:");
        for(int i=0; i<15; i++){
            list.add((int)(Math.random()*(100+1)));
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
        System.out.println();
        Collections.sort(list);
        System.out.println("Saralangan to'plam:");
        for (int i:list){
            System.out.print(i + " ");
        }
    }
}
