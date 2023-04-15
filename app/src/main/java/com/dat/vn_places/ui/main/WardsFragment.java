package com.dat.vn_places.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dat.vn_places.MainActivity;

import java.util.ArrayList;

public class WardsFragment extends MyFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void load() {
        ArrayList<String> list = new ArrayList<>();
        for (String i : MainActivity.data.keySet())
            for (String j : MainActivity.data.get(i).keySet())
                for (String name : MainActivity.data.get(i).get(j))
                    list.add(name + ", " + j + ", " + i);
        toAdapter(list);
    }

    public void search(String input) {
        ArrayList<String> rs = new ArrayList<>();
        for (String i : MainActivity.data.keySet())
            for (String j : MainActivity.data.get(i).keySet())
                for (String name : MainActivity.data.get(i).get(j))
                    if (name.toLowerCase().contains(input.toLowerCase()) || relative(name, input))
                        rs.add(name + ", " + j + ", " + i);
        toAdapter(rs);

    }

    public void inDistrict(String province, String district) {
        ArrayList<String> rs = new ArrayList<>();
        for (String name : MainActivity.data.get(province).get(district))
            rs.add(name + ", " + district + ", " + province);
        MainActivity.textView.setText(district + ", " + province + " có " + rs.size() + " xã phường");
        toAdapter(rs);

    }
}
