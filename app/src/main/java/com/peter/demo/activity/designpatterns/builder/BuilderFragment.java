package com.peter.demo.activity.designpatterns.builder;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.peter.common.utils.StringUtil;
import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;

import java.io.IOException;
import java.io.InputStream;

import butterknife.OnClick;

/**
 * Created by songzhongkun on 16/3/30 13:32.
 */
public class BuilderFragment extends BackHeaderFragment {

    @Override
    public int layoutId() {
        return R.layout.fragment_builder;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        TextView tv_instructions = (TextView) findViewById(R.id.tv_instructions);
        tv_instructions.setMovementMethod(ScrollingMovementMethod.getInstance());
        try {
            InputStream inputStream = mContext.getAssets().open("builder.txt");
            tv_instructions.setText(StringUtil.getStringByFileName(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.btn_createA, R.id.btn_createB})
    public void click(View v) {
        Director director = new Director();
        switch (v.getId()) {
            case R.id.btn_createA:
                Builder b1 = new ConcreteBuilder1();
                director.construct(b1);
                Product p1 = b1.getRequest();
                p1.show(mContext);
                break;
            case R.id.btn_createB:
                Builder b2 = new ConcreteBuilder2();
                director.construct(b2);
                Product p2 = b2.getRequest();
                p2.show(mContext);
                break;
        }
    }

    @Override
    public void initHeader() {
        super.initHeader();
        getHeader().setTitle("建造者模式");
    }
}
