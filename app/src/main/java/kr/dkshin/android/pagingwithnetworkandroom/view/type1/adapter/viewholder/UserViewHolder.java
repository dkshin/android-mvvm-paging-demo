package kr.dkshin.android.pagingwithnetworkandroom.view.type1.adapter.viewholder;

import androidx.recyclerview.widget.RecyclerView;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.db.User;
import kr.dkshin.android.pagingwithnetworkandroom.databinding.ItemUserViewBinding;

/**
 * Created by SHIN on 2018. 7. 13..
 */
public class UserViewHolder extends RecyclerView.ViewHolder {

    private ItemUserViewBinding mBinding;

    private UserItemViewModel mUserItemViewModel;

    public UserViewHolder(ItemUserViewBinding binding) {
        super(binding.getRoot());
        this.mBinding = binding;
    }

    public void bindTo(User user) {
        mUserItemViewModel = new UserItemViewModel(user);
        mBinding.setViewModel(mUserItemViewModel);

        // Immediate Binding
        // When a variable or observable changes, the binding will be scheduled to change before
        // the next frame. There are times, however, when binding must be executed immediately.
        // To force execution, use the executePendingBindings() method.
        mBinding.executePendingBindings();
    }

}
