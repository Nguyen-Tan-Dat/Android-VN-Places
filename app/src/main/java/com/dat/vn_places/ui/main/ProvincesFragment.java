package com.dat.vn_places.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dat.vn_places.MainActivity;

import java.util.ArrayList;
import java.util.Comparator;

public class ProvincesFragment extends MyFragment {
    public final WardsFragment wf;
    public final DistrictsFragment df;
    public final VihiclesNumberFragment vf;

    public ProvincesFragment(WardsFragment wf, DistrictsFragment df, VihiclesNumberFragment vf) {
        this.wf = wf;
        this.df = df;
        this.vf = vf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            try {
                String p = listView.getAdapter().getItem(position).toString();
                df.inProvince(p);
                MainActivity.viewPager.setCurrentItem(1);
            } catch (Exception ignored) {
            }
        });
        return view;
    }

    public void load() {
        ArrayList<String> list = new ArrayList<>(MainActivity.data.keySet());
        list.sort(Comparator.comparing(String::toString));
        toAdapter(list);
        MainActivity.textView.setText("Việt Nam có "+ list.size()+" tỉnh thành");
    }

    public void search(String input) {
        ArrayList<String> rs = new ArrayList<>();
        for (String name : MainActivity.data.keySet()) {
            if (name.contains(input) || relative(name, input))
                rs.add(name);
        }
        toAdapter(rs);
    }
}
