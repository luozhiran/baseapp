package com.yk.base.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itg.lib_log.L;
import com.yk.base.WeakHandler;


public abstract class BaseFragment extends Fragment implements Handler.Callback {

    protected WeakHandler mHandler = new WeakHandler(this);
    private ViewDataBinding mTemp;

    public BaseFragment() {

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        L.e("BaseFragment onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e("BaseFragment onCreate");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        L.e("BaseFragment onCreateView");
        mTemp = DataBindingUtil.inflate(LayoutInflater.from(getContext()), layoutId(), container, false);
        return mTemp.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.e("BaseFragment onActivityCreated");
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        L.e("BaseFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.e("BaseFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        L.e("BaseFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.e("BaseFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacksAndMessages(null);
        L.e("BaseFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.e("BaseFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.e("BaseFragment onDetach");
    }


    public abstract int layoutId();

    public <T> T findByView() {
        return (T) mTemp;
    }

    public abstract void init();

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return true;
    }

}
