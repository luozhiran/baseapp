package com.yk.base.activity;

import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.itg.util_lib.StatusBarUtil;
import com.kongzue.dialog.interfaces.OnBackClickListener;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.yk.base.R;
import com.yk.base.base.BaseActivity;
import com.yk.base.databinding.ActivityZoomPhotoBinding;

import java.util.ArrayList;
import java.util.List;

public class ZoomPhotoActivity extends BaseActivity {

    public static final String IMG_LIST = "img_list";
    public static final String ONE_IMG = "one_img";
    public static final String SHOW_POSITION = "show_position";
    private ActivityZoomPhotoBinding mRootView;

    @Override
    public int layoutId() {
        return R.layout.activity_zoom_photo;
    }

    @Override
    public void init() {
        mRootView = findByView();
        String img = getIntent().getStringExtra(ONE_IMG);
        List<String> imgList = getIntent().getStringArrayListExtra(IMG_LIST);
        int position = getIntent().getIntExtra(SHOW_POSITION, 0);
        if (imgList == null && TextUtils.isEmpty(img)) {
            MessageDialog.build(mOwner)
                    .setTitle("提示")
                    .setMessage("传输的图片数据有问题，请重试")
                    .setOkButton("确定", new OnDialogButtonClickListener() {
                        @Override
                        public boolean onClick(BaseDialog baseDialog, View v) {
                            baseDialog.doDismiss();
                            finish();
                            return true;
                        }
                    }).setOnBackClickListener(new OnBackClickListener() {
                @Override
                public boolean onBackClick() {
                    return true;
                }
            })
                    .show();

            return;

        }
        ViewPager viewPager = findViewById(R.id.view_pager);
        if (!TextUtils.isEmpty(img)) {
            imgList = new ArrayList<>();
            imgList.add(img);
        }
        viewPager.setCurrentItem(position);
        viewPager.setAdapter(new SamplePagerAdapter(imgList));
    }

    @Override
    public void statusBarTransparent() {
        StatusBarUtil.setStatusBarColor(this, Color.parseColor("#cc000000"));
    }


}
