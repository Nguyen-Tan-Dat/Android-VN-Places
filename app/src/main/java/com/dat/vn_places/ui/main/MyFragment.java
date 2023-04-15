package com.dat.vn_places.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.dat.vn_places.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class MyFragment extends Fragment {
    protected ArrayAdapter<String> adapter;
    protected ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);
        adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        listView = view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        final boolean[] isScrollingUp = {false};
        final long[] lastScrollTime = {0};
        listView.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    lastScrollTime[0] = System.currentTimeMillis();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getY() > 0 && event.getY() < v.getHeight()) {
                        isScrollingUp[0] = true;
                    } else {
                        isScrollingUp[0] = false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (isScrollingUp[0] && listView.getFirstVisiblePosition() == 0 &&
                            listView.getChildAt(0).getTop() == 0) {
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - lastScrollTime[0] > 800) {
                            load();
                        }
                    }
                    break;
            }
            return false;
        });
        load();
        return view;
    }

    protected void toAdapter(List<String> list) {
        list.sort(Comparator.comparing(String::toString));
        for (int i = list.size(); i < 14; i++)
            list.add("");
        try {
            adapter.clear();
            adapter.addAll(list);
            listView.smoothScrollToPosition(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean relative(String content, String charAts) {
        StringBuilder h = new StringBuilder();
        int length = content.length();
        char prevChar = ' ';
        for (int i = 0; i < length; i++) {
            char currentChar = content.charAt(i);
            if (Character.isWhitespace(prevChar) && !Character.isWhitespace(currentChar))
                h.append(Character.toLowerCase(currentChar));
            prevChar = currentChar;
        }
        return h.toString().contains(charAts.toLowerCase());
    }

    public abstract void load();

    public abstract void search(String input);
}
