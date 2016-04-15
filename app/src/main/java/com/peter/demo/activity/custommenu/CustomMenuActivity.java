package com.peter.demo.activity.custommenu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 自定义左右滑动菜单
 * Created by songzhongkun on 16/3/25 10:45.
 */
public class CustomMenuActivity extends FragmentActivity {

    private MenuUI menuUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menuUI = new MenuUI(this);
        setContentView(menuUI);

        LeftMenuFragment leftMenuFragment = new LeftMenuFragment();
        getSupportFragmentManager().beginTransaction().add(MenuUI.LEFT_ID, leftMenuFragment).commit();
    }
}
