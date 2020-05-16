package com.rpl6.foodmates;

import java.util.ArrayList;

public class ChefData {
    private static String[] chefNama = {
            "Ahmad Dahlan",
            "Ahmad Yani",
            "Sutomo",
            "Gatot Soebroto",
            "Ki Hadjar Dewantarai",
            "Mohammad Hatta",
            "Soedirman",
            "Soekarno",
            "Soepomo",
            "Tan Malaka"
    };

    private static String[] chefSpesialisasi = {
            "Tukang tidur",
            "Tukang tidur",
            "Tukang tidur",
            "Tukang tidur",
            "Tukang tidur",
            "Tukang tidur",
            "Tukang tidur",
            "Tukang tidur",
            "Tukang tidur",
            "Tukang tidur"
    };

    private static int[] chefUmur = {
            32,
            32,
            32,
            32,
            32,
            32,
            32,
            32,
            32,
            32
    };


    static ArrayList<Chef> getListData() {
        ArrayList<Chef> list = new ArrayList<>();
        for (int position = 0; position < chefNama.length; position++) {
            Chef chef = new Chef();
            chef.setNama(chefNama[position]);
            chef.setUmur(chefUmur[position]);
            chef.setSpesialisasi(chefSpesialisasi[position]);
            list.add(chef);
        }
        return list;
    }
}
