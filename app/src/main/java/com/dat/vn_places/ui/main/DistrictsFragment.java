package com.dat.vn_places.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dat.vn_places.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DistrictsFragment extends MyFragment {
    private final WardsFragment wardsFragment;

    public DistrictsFragment(WardsFragment wardsFragment) {
        this.wardsFragment = wardsFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            try {
                String[] i = listView.getAdapter().getItem(position).toString().split(", ");
                wardsFragment.inDistrict(i[1], i[0]);
                MainActivity.viewPager.setCurrentItem(2);
            } catch (Exception ignored) {
            }
        });
        return view;
    }

    public void load() {
        List<String> list = new ArrayList<>();
        for (String i : MainActivity.data.keySet())
            for (String name : MainActivity.data.get(i).keySet())
                list.add(name + ", " + i);
        MainActivity.textView.setText("Việt Nam có "+ list.size()+ " quận huyện");
        toAdapter(list);
    }

    public void search(String input) {
        ArrayList<String> rs = new ArrayList<>();
        for (String i : MainActivity.data.keySet())
            for (String name : MainActivity.data.get(i).keySet())
                if (name.toLowerCase().contains(input.toLowerCase()) || relative(name, input))
                    rs.add(name + ", " + i);
        toAdapter(rs);
    }

    public void inProvince(String province) {
        ArrayList<String> rs = new ArrayList<>();
        int cw=0;
        for (String name : MainActivity.data.get(province).keySet()) {
            rs.add(name + ", " + province);
            cw+=MainActivity.data.get(province).get(name).size();
        }
        MainActivity.textView.setText(province + " có " + rs.size() + " quận huyện, "  +cw+ " xã phường.");
        toAdapter(rs);
    }
}
