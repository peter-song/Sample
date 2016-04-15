package com.peter.demo.activity.designpatterns.uml;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.peter.common.utils.StringUtil;
import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;

import java.io.IOException;
import java.io.InputStream;

/**
 * UML
 * Created by songzhongkun on 16/3/31 16:10.
 */
public class UMLFragment extends BackHeaderFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_uml;
    }

    @Override
    public void initUI() {
        findViewById(R.id.iv_uml).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewFragment(new FullScreenFragment(), true);
            }
        });
    }

    @Override
    public void initData() {
        TextView tv_instructions = (TextView) findViewById(R.id.tv_instructions);
        tv_instructions.setMovementMethod(ScrollingMovementMethod.getInstance());
        try {
            InputStream inputStream = mContext.getAssets().open("uml.txt");
            tv_instructions.setText(StringUtil.getStringByFileName(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initHeader() {
        super.initHeader();
        getHeader().setTitle("UML类图图示样例");
    }
}
