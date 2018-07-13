/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package kr.dkshin.android.pagingwithnetworkandroom.view.type1.adapter.viewholder;


import com.orhanobut.logger.Logger;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.api.NetworkState;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.api.Status;
import kr.dkshin.android.pagingwithnetworkandroom.view.type1.adapter.ONPAdapter;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class NetworkStateItemViewModel {

    public final ObservableBoolean isErrorMessageVisible;
    public final ObservableBoolean isRetryButtonVisible;
    public final ObservableBoolean isLoadingProgressBarVisible;
    public final ObservableField<String> errorMessage;

    private final NetworkState mNetworkState;
    private final ONPAdapter.RetryCallback mRetryCallback;

    public NetworkStateItemViewModel(NetworkState networkState, ONPAdapter.RetryCallback retryCallback) {
        this.mNetworkState = networkState;
        this.mRetryCallback = retryCallback;
        isErrorMessageVisible = new ObservableBoolean(mNetworkState.getMessage() != null ? true : false);
        errorMessage = new ObservableField<>(mNetworkState.getMessage());

        isRetryButtonVisible = new ObservableBoolean(mNetworkState.getStatus() == Status.FAILED ? true : false);
        isLoadingProgressBarVisible = new ObservableBoolean(mNetworkState.getStatus() == Status.RUNNING ? true : false);
        Logger.e("mNetworkState.getStatus() : "+ mNetworkState.getStatus());
    }

    public void onClickRetryButton(){
        mRetryCallback.retry();
    }

}
