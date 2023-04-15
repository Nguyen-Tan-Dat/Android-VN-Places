package com.dat.vn_places.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VihiclesNumberFragment extends MyFragment {
    private HashMap<Integer, String> data;

    public VihiclesNumberFragment() {
        data();
    }

    private void data() {
        data = new HashMap<>();
        data.put(11, "Cao Bằng");
        data.put(47, "Đắk Lắk");
        data.put(81, "Gia Lai");
        data.put(12, "Lạng Sơn");
        data.put(48, "Đắk Nông");
        data.put(82, "Kon Tum");
        data.put(14, "Quảng Ninh");
        data.put(49, "Lâm Đồng");
        data.put(83, "Sóc Trăng");
        data.put(15, "Hải Phòng");
        data.put(16, "Hải Phòng");
        data.put(50, "TP.HCM");
        data.put(51, "TP.HCM");
        data.put(52, "TP.HCM");
        data.put(53, "TP.HCM");
        data.put(54, "TP.HCM");
        data.put(55, "TP.HCM");
        data.put(56, "TP.HCM");
        data.put(57, "TP.HCM");
        data.put(58, "TP.HCM");
        data.put(59, "TP.HCM");
        data.put(41, "TP.HCM");
        data.put(84, "Trà Vinh");
        data.put(17, "Thái Bình");
        data.put(61, "Bình Dương");
        data.put(85, "Ninh Thuận");
        data.put(18, "Nam Định");
        data.put(62, "Long An");
        data.put(86, "Bình Thuận");
        data.put(19, "Phú Thọ");
        data.put(63, "Tiền Giang");
        data.put(88, "Vĩnh Phúc");
        data.put(20, "Thái Nguyên");
        data.put(64, "Vĩnh Long");
        data.put(89, "Hưng Yên");
        data.put(21, "Yên Bái");
        data.put(65, "Cần Thơ");
        data.put(90, "Hà Nam");
        data.put(22, "Tuyên Quang");
        data.put(66, "Đồng Tháp");
        data.put(92, "Quảng Nam");
        data.put(23, "Hà Giang");
        data.put(67, "An Giang");
        data.put(93, "Bình Phước");
        data.put(24, "Lào Cai");
        data.put(68, "Kiên Giang");
        data.put(94, "Bạc Liêu");
        data.put(25, "Lai Châu");
        data.put(69, "Cà Mau");
        data.put(95, "Hậu Giang");
        data.put(26, "Sơn La");
        data.put(70, "Tây Ninh");
        data.put(97, "Bắc Kạn");
        data.put(27, "Điện Biên");
        data.put(71, "Bến Tre");
        data.put(13, "Bắc Giang");
        data.put(98, "Bắc Giang");
        data.put(28, "Hòa Bình");
        data.put(72, "BR. Vũng Tàu");
        data.put(99, "Bắc Ninh");
        data.put(29, "Hà Nội");
        data.put(30, "Hà Nội");
        data.put(31, "Hà Nội");
        data.put(32, "Hà Nội");
        data.put(33, "Hà Nội");
        data.put(34, "Hải Dương");
        data.put(35, "Ninh Bình");
        data.put(36, "Thanh Hóa");
        data.put(37, "Nghệ An");
        data.put(38, "Hà Tĩnh");
        data.put(39, "Đồng Nai");
        data.put(60, "Đồng Nai");
        data.put(43, "Đà Nẵng");
        data.put(73, "Quảng Bình");
        data.put(74, "Quảng Trị");
        data.put(75, "Huế");
        data.put(76, "Quảng Ngãi");
        data.put(77, "Bình Định");
        data.put(78, "Phú Yên");
        data.put(79, "Khánh Hòa");
        data.put(80, "Cơ quan TW *");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void load() {
        List<String> list = new ArrayList<>();
        for (Integer i : data.keySet())
            list.add(i + " - " + data.get(i));
        toAdapter(list);
    }

    public void search(String input) {
        ArrayList<String> rs = new ArrayList<>();
        for (Integer i : data.keySet()) {
            String info = i + " - " + data.get(i);
            if (info.toLowerCase().contains(input.toLowerCase()) || MyFragment.relative(info, input))
                rs.add(info);
        }
        toAdapter(rs);
    }

    public void inProvince(String p) {
        ArrayList<String> rs = new ArrayList<>();
        for (Integer i : data.keySet()) {
            String info = i + " - " + data.get(i);
            if (p.toLowerCase().contains(info.toLowerCase()) || MyFragment.relative(p, info))
                rs.add(info);
        }
        toAdapter(rs);
    }
}
