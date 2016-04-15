package com.peter.demo.activity.custommenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.peter.demo.R;

/**
 * Created by songzhongkun on 16/3/25 11:47.
 */
public class LeftMenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_leftmenu, container, false);
        v.findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "点击了",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
