package com.peter.demo.activity.designpatterns;

import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.peter.common.utils.StringUtil;
import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;

import java.io.IOException;
import java.io.InputStream;

/**
 * 原则
 * Created by songzhongkun on 16/3/30 11:47.
 */
public class PrincipleFragment extends BackHeaderFragment {

    private TextView tv_title;
    private TextView tv_content;

    private int id;

    @Override
    public int layoutId() {
        return R.layout.fragment_principle;
    }

    @Override
    public void initUI() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_content = (TextView) findViewById(R.id.tv_content);

        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void initData() {
        String title = getArguments().getString("title");
        tv_title.setText(title + "原则");

        id = getArguments().getInt("id");
        showContent(id);
    }

    @Override
    public void initHeader() {
        super.initHeader();
        getHeader().setTitle("设计原则");
    }

    private void showContent(int id) {
        String name = null;
        switch (id) {
            case 0:
                name = "srp";
                break;
            case 1:
                name = "isp";
                break;
            case 2:
                name = "ocp";
                break;
            case 3:
                name = "dip";
                break;
            case 4:
                name = "lsp";
                break;
            case 5:
                name = "carp";
                break;
            case 6:
                name = "lod";
                break;
        }

        try {
            InputStream inputStream = mContext.getAssets().open(name + ".txt");
            tv_content.setText(StringUtil.getStringByFileName(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
