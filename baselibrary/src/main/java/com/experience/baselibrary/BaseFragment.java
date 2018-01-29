package com.experience.baselibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huawei.experience.baselibrary.R;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentId(), container, false);
        return view;
    }

    /**
     * 该方法紧跟onCreateView之后执行
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        //查找actionbar控件设置paddingtop
        BaseActivity activity = (BaseActivity) getActivity();
        //判断activity是否开启沉浸式通知栏
        if (activity.isOpenStatus()) {
            View actionbarview = view.findViewById(R.id.actionbar);
            if (actionbarview != null) {
                int heigth = ScreenUtil.getStatusHeight(getActivity());
                actionbarview.setPadding(0, heigth, 0, 0);
            }
        }

        fragmentManager = getChildFragmentManager();
        init(view);
        bindListener();
        loadDatas();
    }

    /**
     * 该方法紧跟onViewCreated之后执行
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            getDatas(bundle);
        }
    }

    protected void getDatas(Bundle bundle) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * @param view
     */
    protected void init(View view) {
    }

    /**
     *
     */
    protected void bindListener() {
    }

    /**
     * 加载数据的方法
     */
    protected void loadDatas() {
    }

    protected abstract int getContentId();


    private FragmentManager fragmentManager;
    //当前正在展示的Fragment
    private BaseFragment showFragment;


    /**
     * 展示Fragment
     */
    protected void showFragment(int resid, BaseFragment fragment) {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //隐藏正在展示的Fragment
        if (showFragment != null) {
            fragmentTransaction.hide(showFragment);
        }

        //展示需要显示的Fragment对象
        Fragment mFragment = fragmentManager.findFragmentByTag(fragment.getClass().getName());
        if (mFragment != null) {
            //说明存在
            fragmentTransaction.show(mFragment);
            showFragment = (BaseFragment) mFragment;
        } else {
            fragmentTransaction.add(resid, fragment, fragment.getClass().getName());
            showFragment = fragment;
        }
        fragmentTransaction.commit();
    }
}
