package kr.dkshin.android.pagingwithnetworkandroom.view.type1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import kr.dkshin.android.pagingwithnetworkandroom.BR;
import kr.dkshin.android.pagingwithnetworkandroom.R;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.api.NetworkState;
import kr.dkshin.android.pagingwithnetworkandroom.databinding.ActivityOnpBinding;
import kr.dkshin.android.pagingwithnetworkandroom.view.base.BaseActivity;
import kr.dkshin.android.pagingwithnetworkandroom.view.type1.adapter.ONPAdapter;

public class ONPActivity extends BaseActivity<ActivityOnpBinding, ONPViewModel> implements ONPNavigator, ONPAdapter.RetryCallback {

    private static final String TAG = "ONPActivity";

    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    @Inject
    LinearLayoutManager mLinearLayoutManager;

    private ONPViewModel mONPViewModel;
    private ActivityOnpBinding mActivityOnpBinding;
    private ONPAdapter onpAdapter;

    public static Intent newIntent(Context context) {
        return new Intent(context, ONPActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_onp;
    }

    @Override
    public ONPViewModel getViewModel() {
        mONPViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ONPViewModel.class);
        return mONPViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOnpBinding = getViewDataBinding();
        mONPViewModel.setNavigator(this);
        initAdapter();
        initSwipeToRefresh();
    }

    @Override
    public void handleError(Throwable throwable) {
        Logger.e("throwable : "+ throwable);
    }

    @Override
    public void retry() {
        mONPViewModel.retry();
    }

    private void initAdapter() {
        onpAdapter = new ONPAdapter(this);
        mActivityOnpBinding.onpRecyclerView.setLayoutManager(mLinearLayoutManager);
        mActivityOnpBinding.onpRecyclerView.setAdapter(onpAdapter);
        mONPViewModel.userList.observe(this, onpAdapter::submitList);
        mONPViewModel.getNetworkState().observe(this, onpAdapter::setNetworkState);
    }

    private void initSwipeToRefresh() {
        mONPViewModel.getRefreshState().observe(this, networkState -> {
            if (networkState != null) {
                if (onpAdapter.getCurrentList() != null) {
                    if (onpAdapter.getCurrentList().size() > 0) {
                        mActivityOnpBinding.onpSwipeRefreshLayout.setRefreshing(
                                networkState.getStatus() == NetworkState.LOADING.getStatus());
                    } else {
                        setInitialLoadingState(networkState);
                    }
                } else {
                    setInitialLoadingState(networkState);
                }
            }
        });
    }

    private void setInitialLoadingState(NetworkState networkState) {
        mONPViewModel.setInitialLoadingState(networkState);
        //mActivityOnpBinding.onpSwipeRefreshLayout.setEnabled(networkState.getStatus() == Status.SUCCESS);
    }

}
