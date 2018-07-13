package kr.dkshin.android.pagingwithnetworkandroom.view.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import kr.dkshin.android.pagingwithnetworkandroom.BR;
import kr.dkshin.android.pagingwithnetworkandroom.R;
import kr.dkshin.android.pagingwithnetworkandroom.databinding.ActivityMainBinding;
import kr.dkshin.android.pagingwithnetworkandroom.view.base.BaseActivity;
import kr.dkshin.android.pagingwithnetworkandroom.view.type1.ONPActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator{

    private static final String TAG = "MainActivity";

    @Inject
    MainViewModel mMainViewModel;

    private ActivityMainBinding mActivityMainBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        return mMainViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
    }

    @Override
    public void handleError(Throwable throwable) {
        Logger.e("throwable : "+ throwable);
    }

    @Override
    public void openNetworkPagingActivity() {
        Logger.w("openNetworkPagingActivity");
        Intent intent = ONPActivity.newIntent(MainActivity.this);
        startActivity(intent);
    }

    @Override
    public void openNetworkAndRoomPagingActivity() {
        Logger.w("openNetworkAndRoomPagingActivity");
    }
}
