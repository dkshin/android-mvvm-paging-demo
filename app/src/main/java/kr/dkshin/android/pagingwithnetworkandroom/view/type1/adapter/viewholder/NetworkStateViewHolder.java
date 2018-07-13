package kr.dkshin.android.pagingwithnetworkandroom.view.type1.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.api.NetworkState;
import kr.dkshin.android.pagingwithnetworkandroom.databinding.ItemNetworkStateBinding;
import kr.dkshin.android.pagingwithnetworkandroom.view.type1.adapter.ONPAdapter;

/**
 * Created by SHIN on 2018. 7. 13..
 */
public class NetworkStateViewHolder extends RecyclerView.ViewHolder {

    private ItemNetworkStateBinding mBinding;

    private NetworkStateItemViewModel mNetworkStateItemViewModel;

    private ONPAdapter.RetryCallback mRetryCallback;

    public NetworkStateViewHolder(ItemNetworkStateBinding binding, ONPAdapter.RetryCallback retryCallback) {
        super(binding.getRoot());
        this.mBinding = binding;
        this.mRetryCallback = retryCallback;
    }

    public void bindTo(NetworkState networkState) {
        mNetworkStateItemViewModel = new NetworkStateItemViewModel(networkState, mRetryCallback);
        mBinding.setViewModel(mNetworkStateItemViewModel);

        // Immediate Binding
        // When a variable or observable changes, the binding will be scheduled to change before
        // the next frame. There are times, however, when binding must be executed immediately.
        // To force execution, use the executePendingBindings() method.
        mBinding.executePendingBindings();
    }

}
