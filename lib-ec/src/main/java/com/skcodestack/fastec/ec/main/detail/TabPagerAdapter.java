package com.skcodestack.fastec.ec.main.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * Email  1562363326@qq.com
 * Github https://github.com/skcodestack
 * Created by sk on 2018/7/24
 * Version  1.0
 * Description:
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private final ArrayList<String> TAB_TITLE = new ArrayList<>();
    private final ArrayList<ArrayList<String>> PICTURES = new ArrayList<>();


    public TabPagerAdapter(FragmentManager fm, JSONObject mObject) {
        super(fm);
        initData(mObject);
    }

    private void initData(JSONObject mObject) {
        JSONArray tabs = mObject.getJSONArray("tabs");
        int size = tabs.size();
        for (int i = 0; i < size; i++) {
            JSONObject object = tabs.getJSONObject(i);
            String name = object.getString("name");
            JSONArray pictures = object.getJSONArray("pictures");
            ArrayList<String> picList = new ArrayList<>();
            int picSize = pictures.size();
            for (int j = 0; j < picSize; j++) {
                String picUrl = pictures.getString(j);
                picList.add(picUrl);
            }
            TAB_TITLE.add(name);
            PICTURES.add(picList);
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            ImageDelegate.create(PICTURES.get(position));
        }else if(position == 1){
            ImageDelegate.create(PICTURES.get(position));
        }
        return ImageDelegate.create(PICTURES.get(position));
    }

    @Override
    public int getCount() {
        return TAB_TITLE.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLE.get(position);
    }
}
