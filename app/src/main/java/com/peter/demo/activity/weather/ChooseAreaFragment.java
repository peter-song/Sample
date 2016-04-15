package com.peter.demo.activity.weather;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.peter.common.adapter.CommonAdapter;
import com.peter.common.utils.LogUtils;
import com.peter.common.utils.log.ILog;
import com.peter.common.view.XListView;
import com.peter.demo.R;
import com.peter.demo.db.WeatherDB;
import com.peter.demo.model.weather.City;
import com.peter.demo.model.weather.County;
import com.peter.demo.model.weather.Province;
import com.peter.demo.net.HttpCallbackListener;
import com.peter.demo.net.HttpUtil;
import com.peter.demo.net.Utility;
import com.peter.demo.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择地址类
 * Created by songzhongkun on 15/10/15.
 */
public class ChooseAreaFragment extends BaseFragment implements AdapterView.OnItemClickListener, XListView.IXListViewListener {

    // 省市县级别
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    // 省市县常量
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String COUNTY = "county";
    private ProgressDialog progressDialog;

    private TextView titleText;
    private XListView mListView;
    private CommonAdapter<String> mAdapter;
    private WeatherDB weatherDB;
    private List<String> dataList = new ArrayList<String>();

    // 省列表
    private List<Province> provinceList;
    // 市列表
    private List<City> cityList;
    // 县列表
    private List<County> countyList;
    // 选中省
    private Province selectedProvince;
    // 选中城市
    private City selectedCity;
    // 当前选中级别
    private int currentLevel;

    private Handler mHandler;

    // 是否来自于天气页面
    private boolean isFromWeatherActivity;

    // 每一次显示的条目数
    private int pageSize = 10;

    // 是否允许初始化数据
    private boolean isInitData = true;

    // 首次加载省份数据,用于辨别数据库是否有数据
    private int firstLoad = 1;
    // 是否存在数据
    private boolean isHasData = false;
    private static final ILog logger = LogUtils.getLogger(ChooseAreaFragment.class);

    @Override
    public int layoutId() {
        return R.layout.fragment_choosearea;
    }

    @Override
    public void initUI() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            isFromWeatherActivity = bundle.getBoolean("from_weather_activity", false);
        }
        SharedPreferences prefe = PreferenceManager.getDefaultSharedPreferences(mContext);
        if (prefe.getBoolean("city_selected", false) && !isFromWeatherActivity) {
            closeTopFragment();
            openNewFragment(new WeatherFragment(), true);
            isInitData = false;
            return;
        }

        titleText = (TextView) findViewById(R.id.title_text);
        mListView = (XListView) findViewById(R.id.xListView);
        // 设置列表项点击监听事件
        mListView.setOnItemClickListener(this);
        // 设置下拉以及加载更多监听事件
        mListView.setXListViewListener(this);
        // 设置是否可以下拉刷新
        mListView.setPullRefreshEnable(false);
        // 设置是否可以加载更多
        mListView.setPullLoadEnable(true);
        mHandler = new Handler();
    }

    @Override
    public void initData() {
        if (isInitData) {
            weatherDB = WeatherDB.getInstance(mContext);
            mListView.setAdapter(mAdapter = new CommonAdapter<String>(mContext, dataList, R.layout.item_weather_list) {
                @Override
                public void convert(ViewHolder helper, String item) {
                    helper.setText(R.id.tv_title, item);
                }
            });
            queryProvinces(String.valueOf(pageSize));
        }
    }

    @Override
    public void initHeader() {
        getHeader().hide();
    }

    /**
     * 点击列表项
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (currentLevel == LEVEL_PROVINCE) {
            selectedProvince = weatherDB.getProvinceById(position);
            queryCities();
            // 设置是否可以加载更多
            mListView.setPullLoadEnable(false);
        } else if (currentLevel == LEVEL_CITY) {
            selectedCity = weatherDB.getCityByCityCode(getCityCode(
                    selectedProvince.provinceCode, position));
            queryCounties();
            // 设置是否可以加载更多
            mListView.setPullLoadEnable(false);
        } else if (currentLevel == LEVEL_COUNTY) {
            closeAllFragment();
            County county = countyList.get(position - 1);
            WeatherFragment weatherFragment = new WeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("county_code", county.countyCode);
            weatherFragment.setArguments(bundle);
            openNewFragment(weatherFragment, true);
        }
    }

    /**
     * 根据省份编号和城市id获取城市编号
     *
     * @param @param  provinceCodeId
     * @param @param  cityId
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getCityCode
     */
    public String getCityCode(String provinceCodeId, int cityId) {
        String cityCode = "";
        if (cityId < 10) {
            cityCode += "0" + cityId;
        } else {
            cityCode += cityId;
        }
        return provinceCodeId + cityCode;
    }

    /**
     * 根据城市编号和县id获取县编号
     *
     * @param @param  provinceId
     * @param @param  cityId
     * @param @param  countyId
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: getCountyCode
     */
    public String getCountyCode(String cityCode, int countyId) {
        String countyCode = "";
        if (countyId < 10) {
            countyCode += "0" + countyId;
        } else {
            countyCode += countyId;
        }
        return cityCode + countyCode;
    }

    private void queryProvinces(String limit) {
        queryProvinces(limit, false);
    }

    /**
     * 查询全国所有的省,优先从数据库读取,如果数据库没有再去服务器获取
     */
    private void queryProvinces(String limit, boolean isBack) {
        provinceList = weatherDB.loadProvinces(limit);
        if (provinceList.size() > 0) {
            if (firstLoad == 1) {
                isHasData = true;
                firstLoad++;
            }
            if (isBack) {
                dataList.clear();
            }
            for (Province province : provinceList) {
                dataList.add(province.provinceName);
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            titleText.setText(R.string.China);
            currentLevel = LEVEL_PROVINCE;
        } else if (isHasData) {
            alert("没有更多数据");
        } else {
            queryFromServer(null, PROVINCE);
        }
    }

    /**
     * 查询选中省内所有的市,优先从数据库读取,如果数据库没有再去服务器获取
     */
    private void queryCities() {
        cityList = weatherDB.loadCities(selectedProvince.id);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.cityName);
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            titleText.setText(selectedProvince.provinceName);
            currentLevel = LEVEL_CITY;
        } else {
            queryFromServer(selectedProvince.provinceCode, CITY);
        }
    }

    /**
     * 查询选中市内所有的县,优先从数据库读取,如果数据库没有再去服务器获取
     */
    private void queryCounties() {
        countyList = weatherDB.loadCounties(selectedCity.id);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.countyName);
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            titleText.setText(selectedCity.cityName);
            currentLevel = LEVEL_COUNTY;
        } else {
            queryFromServer(selectedCity.cityCode, COUNTY);
        }
    }

    /**
     * 根据传入的代号和类型到服务器上查询市县数据
     */
    private void queryFromServer(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if (PROVINCE.equals(type)) {
                    result = Utility.handleProvincesResponse(weatherDB, response);
                } else if (CITY.equals(type)) {
                    result = Utility.handleCitiesResponse(weatherDB, response,
                            selectedProvince.id);
                } else if (COUNTY.equals(type)) {
                    result = Utility.handleCountiesResponse(weatherDB, response,
                            selectedCity.id);
                }
                if (result) {
                    // 通过runOnUiThread()方法回到主线程处理逻辑
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if (PROVINCE.equals(type)) {
                                queryProvinces(String.valueOf(pageSize));
                            } else if (CITY.equals(type)) {
                                queryCities();
                            } else if (COUNTY.equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                // 通过runOnUiThread()方法回到主线程处理逻辑
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        alert(R.string.loadFail);
                    }
                });
            }
        });
    }

    /**
     * 显示加载提示
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭加载提示
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 下拉刷新(对于此案例,该功能没用,因为地址总数轻易不会变)
     */
    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataList.clear();
                geneItems();
                mAdapter.notifyDataSetChanged();
                mListView.setAdapter(mAdapter = new CommonAdapter<String>(mContext, dataList, R.layout.item_weather_list) {
                    @Override
                    public void convert(ViewHolder helper, String item) {
                        helper.setText(R.id.tv_title, item);
                    }
                });
                onLoad();
            }
        }, 1000);
    }

    /**
     * 加载更多数据
     */
    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 1000);
    }

    private int start = 1;

    /**
     * 加载数据实现
     */
    private void geneItems() {
        String limit = "";
        if (currentLevel == LEVEL_PROVINCE) {
            limit = (start * pageSize) + "," + pageSize;
            start++;
            queryProvinces(limit);
        } else if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryCounties();
        }
    }

    /**
     * 停止加载数据
     */
    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getResources().getString(R.string.justNow));
    }

    @Override
    public boolean onFragmentBackPressed() {
        if (currentLevel == LEVEL_COUNTY) {
            queryCities();
            return true;
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces(String.valueOf(pageSize), true);
            return true;
        } else if (currentLevel == LEVEL_PROVINCE) {
            return false;
        } else {
            return false;
        }
    }

}
