package com.peter.demo.activity.designpatterns.factorymethod;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.peter.common.utils.StringUtil;
import com.peter.demo.R;
import com.peter.demo.activity.template.BackHeaderFragment;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by songzhongkun on 16/3/30 14:49.
 */
public class FactoryMethodFragment extends BackHeaderFragment {

    @Bind(R.id.et_num1)
    EditText et_num1;
    @Bind(R.id.et_num2)
    EditText et_num2;
    @Bind(R.id.tv_result)
    TextView tv_result;

    @Override
    public int layoutId() {
        return R.layout.fragment_factory_method;
    }

    @Override
    public void initUI() {

    }

    @Override
    public void initData() {
        TextView tv_instructions = (TextView) findViewById(R.id.tv_instructions);
        tv_instructions.setMovementMethod(ScrollingMovementMethod.getInstance());
        try {
            InputStream inputStream = mContext.getAssets().open("factoryMethod.txt");
            tv_instructions.setText(StringUtil.getStringByFileName(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initHeader() {
        super.initHeader();
        getHeader().setTitle("工厂方法模式");
    }

    @OnClick({R.id.btn_add, R.id.btn_sub})
    public void click(View v) {
        int a = Integer.parseInt(et_num1.getText().toString());
        int b = Integer.parseInt(et_num2.getText().toString());

        int result = 0;
        switch (v.getId()) {
            case R.id.btn_add:
                Factory factory1 = new AddFactory();
                Operation operation1 = factory1.createOperation();
                operation1.numberA = a;
                operation1.numberB = b;
                result = operation1.getResult();
                break;
            case R.id.btn_sub:
                Factory factory2 = new SubFactory();
                Operation operation2 = factory2.createOperation();
                operation2.numberA = a;
                operation2.numberB = b;
                result = operation2.getResult();
                break;
        }
        tv_result.setText(String.valueOf(result));
    }
}
