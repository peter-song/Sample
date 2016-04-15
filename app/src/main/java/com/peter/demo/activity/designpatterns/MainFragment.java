package com.peter.demo.activity.designpatterns;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.peter.common.adapter.CommonAdapter;
import com.peter.demo.R;
import com.peter.demo.activity.designpatterns.builder.BuilderFragment;
import com.peter.demo.activity.designpatterns.factorymethod.FactoryMethodFragment;
import com.peter.demo.activity.designpatterns.uml.UMLFragment;
import com.peter.demo.activity.scrollerview.MyGridView;
import com.peter.demo.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 设计模式
 * Created by songzhongkun on 16/3/30 10:19.
 */
public class MainFragment extends BaseFragment {

    private MyGridView listview1, listview2, listview3, listview4, listview5;
    private List<String> chuangjian, jiegou, xingwei, yuanze, uml;
    private HashMap<String, List<String>> titleData = new HashMap<>();

    @Override
    public int layoutId() {
        return R.layout.fragment_design_patterns_main;
    }

    @Override
    public void initUI() {
        listview1 = (MyGridView) findViewById(R.id.listview1);
        listview2 = (MyGridView) findViewById(R.id.listview2);
        listview3 = (MyGridView) findViewById(R.id.listview3);
        listview4 = (MyGridView) findViewById(R.id.listview4);
        listview5 = (MyGridView) findViewById(R.id.listview5);

        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        openNewFragment(new FactoryMethodFragment(), true);
                        break;
                    case 1:
                        break;
                    case 2:
                        openNewFragment(new BuilderFragment(), true);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alert(jiegou.get(position));
            }
        });
        listview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alert(xingwei.get(position));
            }
        });
        listview4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PrincipleFragment principleFragment = new PrincipleFragment();
                Bundle b = new Bundle();
                b.putString("title", yuanze.get(position));
                b.putInt("id", (int) id);
                principleFragment.setArguments(b);
                openNewFragment(principleFragment, true);
            }
        });
        listview5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    openNewFragment(new UMLFragment(), true);
                }else if (position == 1){
                    alert("简单工厂");
                }
            }
        });
    }

    @Override
    public void initData() {
        initTitleData();
        chuangjian = titleData.get("创建型");
        jiegou = titleData.get("结构型");
        xingwei = titleData.get("行为型");
        yuanze = titleData.get("原则");
        uml = titleData.get("URL");

        listview1.setAdapter(new CommonAdapter<String>(mContext, chuangjian, R.layout.item_single_textview) {
            @Override
            public void convert(ViewHolder holder, String item) {
                holder.setText(R.id.tv_title, item);
            }
        });
        listview2.setAdapter(new CommonAdapter<String>(mContext, jiegou, R.layout.item_single_textview) {
            @Override
            public void convert(ViewHolder holder, String item) {
                holder.setText(R.id.tv_title, item);
            }
        });
        listview3.setAdapter(new CommonAdapter<String>(mContext, xingwei, R.layout.item_single_textview) {
            @Override
            public void convert(ViewHolder holder, String item) {
                holder.setText(R.id.tv_title, item);
            }
        });
        listview4.setAdapter(new CommonAdapter<String>(mContext, yuanze, R.layout.item_single_textview) {
            @Override
            public void convert(ViewHolder holder, String item) {
                holder.setText(R.id.tv_title, item);
            }
        });
        listview5.setAdapter(new CommonAdapter<String>(mContext, uml, R.layout.item_single_textview) {
            @Override
            public void convert(ViewHolder holder, String item) {
                holder.setText(R.id.tv_title, item);
            }
        });
    }

    @Override
    public void initHeader() {
        getHeader().setTitle("设计模式和原则");
        getHeader().hideLeftAndRight();
    }

    private void initTitleData() {
        titleData = new HashMap<>();
        List list1 = new ArrayList();
        list1.add("URL类图图示样例");
        list1.add("简单工厂");
        titleData.put("URL", list1);

        list1 = new ArrayList<>();
        list1.add("工程方法");
        list1.add("抽象工厂");
        list1.add("建造者");
        list1.add("原型");
        list1.add("单例");
        titleData.put("创建型", list1);

        list1 = new ArrayList<>();
        list1.add("适配器");
        list1.add("桥接");
        list1.add("组合");
        list1.add("装饰");
        list1.add("外观");
        list1.add("享元");
        list1.add("代理");
        titleData.put("结构型", list1);

        list1 = new ArrayList<>();
        list1.add("解释器");
        list1.add("模板方法");
        list1.add("责任链");
        list1.add("命令");
        list1.add("迭代器");
        list1.add("中介者");
        list1.add("备忘录");
        list1.add("观察者");
        list1.add("状态");
        list1.add("策略");
        list1.add("访问者");
        titleData.put("行为型", list1);

        list1 = new ArrayList<>();
        list1.add("单一职责");
        list1.add("接口隔离原则");
        list1.add("开放-封闭");
        list1.add("依赖倒转");
        list1.add("里氏替换");
        list1.add("合成/聚合复用");
        list1.add("迪米特");
        titleData.put("原则", list1);
    }

}
