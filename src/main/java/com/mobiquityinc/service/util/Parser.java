package com.mobiquityinc.service.util;

import com.mobiquityinc.domain.Item;
import com.mobiquityinc.domain.Pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static List<Pack> getPackages(String path) throws IOException{

        BufferedReader br = new BufferedReader(new FileReader(path));
        String currentLine;

        List<Pack> packs = new ArrayList<>();
        while ((currentLine = br.readLine()) != null) {
            Pack pack = new Pack();
            String[] str = currentLine.split(" ");
            List<Item> items = new ArrayList<>();
            double cap = 0.00;
            for(int i=0; i<str.length; i++){
                if(i<2){
                    cap = Double.parseDouble(str[0]);
                }
                else{
                    String[] spl = str[i].split("[\\(||\\)]")[1].split(",");
                    int index = Integer.parseInt(spl[0]);
                    double weight = Double.parseDouble(spl[1]), cost = Double.parseDouble(spl[2].substring(1));
                    Item itm = new Item(index, weight, cost);
                    items.add(itm);
                }
                pack.setCapacity(cap);
                pack.setItems(items);
            }
            packs.add(pack);
        }

        return packs;
    }

}
