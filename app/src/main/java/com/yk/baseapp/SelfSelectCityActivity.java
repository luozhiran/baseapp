package com.yk.baseapp;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;

import com.cretin.tools.cityselect.CityResponse;
import com.cretin.tools.cityselect.model.CityModel;
import com.google.gson.Gson;
import com.itg.util_lib.FileUtils;
import com.yk.base.base.BaseActivity;
import com.yk.baseapp.databinding.ActivitySelfSelectCityBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class SelfSelectCityActivity extends BaseActivity {


    private ActivitySelfSelectCityBinding mRootView;

    @Override
    public int layoutId() {
        return R.layout.activity_self_select_city;
    }

    @Override
    public void init() {
        //0 所有 1 热门城市 2 当前城市 3 仅列表
        int type = getIntent().getIntExtra("type", 0);
        mRootView = findByView();
        mRootView.cityView.setSearchTips("请输入城市名称或者拼音");
        type = 0;
        initCityData(type);
    }

    private void initCityData(int type){

     Observable<CityResponse.DataBean> source = Observable.create(new ObservableOnSubscribe<CityResponse.DataBean>() {
         @Override
         public void subscribe(ObservableEmitter<CityResponse.DataBean> emitter) throws Exception {
             String str = FileUtils.readAssets("city.json");
             Gson gson = new Gson();
             CityResponse cityResponse =gson.fromJson(str,CityResponse.class);
             List<CityResponse.DataBean> cityList =cityResponse.getData();
             for (CityResponse.DataBean item:cityList) {
                 emitter.onNext(item);
             }
             emitter.onComplete();
         }
     });
        Observable<CityModel> cityModelObservable = source.map(new Function<CityResponse.DataBean, CityModel>() {
         @Override
         public CityModel apply(CityResponse.DataBean dataBean) throws Exception {
             if (dataBean.getSons() == null){
                 return new CityModel(dataBean.getName(),dataBean.getAreaId());
             }else {
                 for (CityResponse.DataBean.SonsBean son :dataBean.getSons()){
                     return new CityModel(son.getName(), son.getAreaId());
                 }
             }
             return null;
         }
     });
        Observable<CityModel> setThreadMode = cityModelObservable.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
        Disposable disposable = setThreadMode.reduceWith(new Callable<List<CityModel>>() {
            @Override
            public List<CityModel> call() throws Exception {
                return new ArrayList<>();
            }
        }, new BiFunction<List<CityModel>, CityModel, List<CityModel>>() {
            @Override
            public List<CityModel> apply(List<CityModel> cityModels, CityModel cityModel) throws Exception {
                cityModels.add(cityModel);
                return cityModels;
            }
        }).subscribe(new Consumer<List<CityModel>>() {
            @Override
            public void accept(List<CityModel> cityModels) throws Exception {
                final List<CityModel> hotCitys = new ArrayList<>();
                hotCitys.add(new CityModel("深圳", "10000000"));
                hotCitys.add(new CityModel("广州", "10000001"));
                hotCitys.add(new CityModel("北京", "10000002"));
                hotCitys.add(new CityModel("天津", "10000003"));
                hotCitys.add(new CityModel("武汉", "10000004"));

                //设置当前城市数据
                final CityModel currentCity = new CityModel("深圳", "10000000");
                if (type == 0) {
                    mRootView.cityView.bindData(cityModels, hotCitys, currentCity);
                } else if (type == 1) {
                    mRootView.cityView.bindData(cityModels, hotCitys, null);
                } else if (type == 2) {
                    mRootView.cityView.bindData(cityModels, null, currentCity);
                } else {
                    mRootView.cityView.bindData(cityModels, null, null);
                }
            }
        });

    }
}
