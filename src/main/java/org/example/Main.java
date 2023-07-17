package org.example;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Integer>arrayList = new ArrayList<>();
        arrayList.add((int)(Math.random()*(100+1))); int min,max;
        min = arrayList.get(0);
        max = arrayList.get(0);
        for(int i=1; i<10; i++){
            arrayList.add((int)(Math.random()*(100+1)));
            if (max < arrayList.get(i)){
                max = arrayList.get(i);
            }

            if (min < arrayList.get(i)){
                min = arrayList.get(i);
            }
        }
        for(int i : arrayList){
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("max = " + max + " min = " + min);
        System.out.println("max / min = " + max/min);








//        Work work = new Work();
//        work.main_menu();

    }
}