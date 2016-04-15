package com.peter.demo.activity.box;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.peter.common.utils.StringUtil;
import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;

import java.io.IOException;
import java.io.InputStream;

/**
 * 帮助
 * Created by songzhongkun on 16/2/26 15:30.
 */
public class HelpFragment extends BackHeaderFragment implements View.OnClickListener {

    private Button btn_yumeiren, btn_qiangjinjiu, btn_huixiangoushu;
    private TextView tv_about;

    @Override
    public int layoutId() {
        return R.layout.fragment_abut;
    }

    @Override
    public void initUI() {
        btn_yumeiren = (Button) findViewById(R.id.btn_yumeiren);
        btn_qiangjinjiu = (Button) findViewById(R.id.btn_qiangjinjiu);
        btn_huixiangoushu = (Button) findViewById(R.id.btn_huixiangoushu);
        tv_about = (TextView) findViewById(R.id.tv_about);
        tv_about.setMovementMethod(ScrollingMovementMethod.getInstance());

        btn_yumeiren.setOnClickListener(this);
        btn_qiangjinjiu.setOnClickListener(this);
        btn_huixiangoushu.setOnClickListener(this);
    }

    @Override
    public void initData() {
        btn_yumeiren.performClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yumeiren:
                try {
                    InputStream inputStream = mContext.getAssets().open("yumeiren.txt");
                    tv_about.setText(StringUtil.getStringByFileName(inputStream));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_qiangjinjiu:
                InputStream inputStream = mContext.getResources().openRawResource(R.raw.qiangjinjiu);
                tv_about.setText(StringUtil.getStringByFileName(inputStream));
                break;
            case R.id.btn_huixiangoushu:
                InputStream inputStream2 = mContext.getResources().openRawResource(R.raw.huixiangoushu);
                tv_about.setText(StringUtil.getStringByFileName(inputStream2));
                break;
        }
    }
}
