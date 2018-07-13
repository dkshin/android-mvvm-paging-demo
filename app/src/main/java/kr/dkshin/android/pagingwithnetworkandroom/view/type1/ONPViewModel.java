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

package kr.dkshin.android.pagingwithnetworkandroom.view.type1;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import kr.dkshin.android.pagingwithnetworkandroom.data.DataManager;
import kr.dkshin.android.pagingwithnetworkandroom.data.datasource.UsersDataSource;
import kr.dkshin.android.pagingwithnetworkandroom.data.datasource.UsersDataSourceFactory;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.api.NetworkState;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.api.Status;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.db.User;
import kr.dkshin.android.pagingwithnetworkandroom.util.rx.SchedulerProvider;
import kr.dkshin.android.pagingwithnetworkandroom.view.base.BaseViewModel;

/**
 * Created by amitshekhar on 07/07/17.
 */

public class ONPViewModel extends BaseViewModel<ONPNavigator> {

    LiveData<PagedList<User>> userList;

    public final ObservableBoolean isErrorMessageVisible;
    public final ObservableBoolean isRetryButtonVisible;
    public final ObservableBoolean isLoadingProgressBarVisible;
    public final ObservableBoolean isSwipeRefreshLayoutEnable;
    public final ObservableField<String> errorMessage;

    private static final int pageSize = 15;
    private UsersDataSourceFactory usersDataSourceFactory;

    public ONPViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        usersDataSourceFactory = new UsersDataSourceFactory(getDataManager(), getSchedulerProvider(), getCompositeDisposable());
        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build();

        userList = new LivePagedListBuilder<>(usersDataSourceFactory, config).build();

        isErrorMessageVisible = new ObservableBoolean(false);
        errorMessage = new ObservableField<>("");
        isRetryButtonVisible = new ObservableBoolean(false);
        isLoadingProgressBarVisible = new ObservableBoolean(true);
        isSwipeRefreshLayoutEnable = new ObservableBoolean(true);
    }

    public void retry() {
        usersDataSourceFactory.getUsersDataSourceLiveData().getValue().retry();
    }

    public void refresh() {
        usersDataSourceFactory.getUsersDataSourceLiveData().getValue().invalidate();
    }

    public LiveData<NetworkState> getNetworkState() {
        return Transformations.switchMap(usersDataSourceFactory.getUsersDataSourceLiveData(), UsersDataSource::getNetworkState);
    }

    public LiveData<NetworkState> getRefreshState() {
        return Transformations.switchMap(usersDataSourceFactory.getUsersDataSourceLiveData(), UsersDataSource::getInitialLoad);
    }

    public void setInitialLoadingState(NetworkState networkState) {
        isErrorMessageVisible.set((networkState.getMessage() != null ? true : false));
        errorMessage.set(networkState.getMessage());

        isRetryButtonVisible.set(networkState.getStatus() == Status.FAILED ? true : false);
        isLoadingProgressBarVisible.set(networkState.getStatus() == Status.RUNNING ? true : false);
        isSwipeRefreshLayoutEnable.set(networkState.getStatus() == Status.SUCCESS ? true : false);
    }



}
