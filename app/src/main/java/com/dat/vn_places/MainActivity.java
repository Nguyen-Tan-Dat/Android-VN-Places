package com.dat.vn_places;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dat.vn_places.ui.main.DistrictsFragment;
import com.dat.vn_places.ui.main.ProvincesFragment;
import com.dat.vn_places.ui.main.VihiclesNumberFragment;
import com.dat.vn_places.ui.main.WardsFragment;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    public static ViewPager viewPager;
    public static TextView textView;
    private ProgressBar progressBar;
    private ProvincesFragment pf;
    private DistrictsFragment df;
    private WardsFragment wf;
    VihiclesNumberFragment vf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        wf = new WardsFragment();
        df = new DistrictsFragment(wf);
        vf = new VihiclesNumberFragment();
        pf = new ProvincesFragment(wf,df,vf);
        setContentView(R.layout.activity_main);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pf.search(query);
                df.search(query);
                wf.search(query);
                vf.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        TabLayout tabLayout = findViewById(R.id.tabs);
        progressBar = findViewById(R.id.progress);
        viewPager = findViewById(R.id.view_pager);
        textView = findViewById(R.id.textView);
        if (!isInternetConnected()) {
            textView.setText("Không có kết nối Internet");
            Toast.makeText(this, "No connect", Toast.LENGTH_SHORT).show();
        }
        PagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @StringRes
            private final int[] TAB_TITLES = new int[]{
                    R.string.tab_provinces,
                    R.string.tab_districts,
                    R.string.tab_wards,
                    R.string.tab_vehicle,
            };

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return pf;
                    case 1:
                        return df;
                    case 2:
                        return wf;
                    case 3:
                        return vf;
                    default:
                        return null;
                }
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return getResources().getString(TAB_TITLES[position]);
            }

            @Override
            public int getCount() {
                return TAB_TITLES.length;
            }

        };
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static HashMap<String, HashMap<String, ArrayList<String>>> data = new HashMap<>();
    private static String generalInformation = "";

    public static void showInfo() {
        textView.setText(generalInformation);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loadData() {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://provinces.open-api.vn/api/?depth=3", null, response -> {
            progressBar.setVisibility(View.VISIBLE);
            int cp = 0, cd = 0, cw = 0;
            try {

                for (int i = 0; i < response.length(); i++) {
                    HashMap<String, ArrayList<String>> districts = new HashMap<>();
                    JSONArray jsonDistricts = (JSONArray) ((JSONObject) response.get(i)).get("districts");
                    cp++;
                    for (int j = 0; j < jsonDistricts.length(); j++) {
                        ArrayList<String> wards = new ArrayList<>();
                        cd++;
                        JSONArray jsonWards = (JSONArray) ((JSONObject) jsonDistricts.get(j)).get("wards");
                        for (int k = 0; k < jsonWards.length(); k++) {
                            cw++;
                            wards.add(((JSONObject) jsonWards.get(k)).get("name").toString());
                        }
                        districts.put(((JSONObject) jsonDistricts.get(j)).get("name").toString(), wards);
                    }
                    data.put(((JSONObject) response.get(i)).get("name").toString(), districts);
                }
                 } catch (Exception e) {
                e.printStackTrace();
            }
            pf.load();
            df.load();
            wf.load();
            vf.load();
            generalInformation = "Vietnam has " + cp + " provinces, " + cd + " districts, " + cw + " wards";
            showInfo();
            progressBar.setVisibility(View.GONE);
        }, error -> Log.d("my-api", "went Wrong"));
        requestQueue.add(jsonArrayRequest);
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onLost(Network network) {
            textView.setText("Không có kết nỗi Internet");
            Toast.makeText(MainActivity.this, "No connect", Toast.LENGTH_SHORT).show();
        }

        public void showNotification(String message) {
            Toast toast = Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        @Override
        public void onAvailable(Network network) {
            showNotification("Internet connected");
            loadData();
        }
    };
}
