package kr.dkshin.android.pagingwithnetworkandroom.view.type1.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import kr.dkshin.android.pagingwithnetworkandroom.R;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.api.NetworkState;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.db.User;
import kr.dkshin.android.pagingwithnetworkandroom.databinding.ItemNetworkStateBinding;
import kr.dkshin.android.pagingwithnetworkandroom.databinding.ItemUserViewBinding;
import kr.dkshin.android.pagingwithnetworkandroom.view.type1.adapter.viewholder.NetworkStateViewHolder;
import kr.dkshin.android.pagingwithnetworkandroom.view.type1.adapter.viewholder.UserViewHolder;

/**
 * Created by Ahmed Abd-Elmeged on 2/16/2018.
 */
public class ONPAdapter extends PagedListAdapter<User, RecyclerView.ViewHolder> {

    private NetworkState networkState;

    private RetryCallback retryCallback;

    public interface RetryCallback {
        void retry();
    }

    public ONPAdapter(RetryCallback retryCallback) {
        super(UserDiffCallback);
        this.retryCallback = retryCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case R.layout.item_user_view:
                ItemUserViewBinding itemUserViewBinding = ItemUserViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new UserViewHolder(itemUserViewBinding);
            case R.layout.item_network_state:
                ItemNetworkStateBinding itemNetworkStateBinding = ItemNetworkStateBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new NetworkStateViewHolder(itemNetworkStateBinding, retryCallback);
            default:
                throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.item_user_view:
                ((UserViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.item_network_state:
                ((NetworkStateViewHolder) holder).bindTo(networkState);
                break;
        }
    }


    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.item_network_state;
        } else {
            return R.layout.item_user_view;
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    /**
     * Set the current network state to the adapter
     * but this work only after the initial load
     * and the adapter already have list to add new loading raw to it
     * so the initial loading state the activity responsible for handle it
     *
     * @param newNetworkState the new network state
     */
    public void setNetworkState(NetworkState newNetworkState) {
        if (getCurrentList() != null) {
            if (getCurrentList().size() != 0) {
                NetworkState previousState = this.networkState;
                boolean hadExtraRow = hasExtraRow();
                this.networkState = newNetworkState;
                boolean hasExtraRow = hasExtraRow();
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount());
                    } else {
                        notifyItemInserted(super.getItemCount());
                    }
                } else if (hasExtraRow && previousState != newNetworkState) {
                    notifyItemChanged(getItemCount() - 1);
                }
            }
        }
    }

    private static DiffUtil.ItemCallback<User> UserDiffCallback = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };

}