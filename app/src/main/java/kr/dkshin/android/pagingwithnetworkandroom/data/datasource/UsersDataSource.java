package kr.dkshin.android.pagingwithnetworkandroom.data.datasource;


import com.orhanobut.logger.Logger;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.ItemKeyedDataSource;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import kr.dkshin.android.pagingwithnetworkandroom.data.DataManager;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.api.NetworkState;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.db.User;
import kr.dkshin.android.pagingwithnetworkandroom.util.rx.SchedulerProvider;

/**
 * Created by Ahmed Abd-Elmeged on 2/13/2018.
 */
public class UsersDataSource extends ItemKeyedDataSource<Long, User> {

    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;

    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();

    private MutableLiveData<NetworkState> initialLoad = new MutableLiveData<>();

    /**
     * Keep Completable reference for the retry event
     */
    private Completable retryCompletable;

    UsersDataSource(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        this.mDataManager = dataManager;
        this.mSchedulerProvider = schedulerProvider;
        this.mCompositeDisposable = compositeDisposable;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public void retry() {
        if (retryCompletable != null) {
            getCompositeDisposable()
                    .add(retryCompletable
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe(() -> {
                            }, throwable -> Logger.e(throwable.getMessage()))
                    );
        }
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull LoadInitialCallback<User> callback) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING);
        initialLoad.postValue(NetworkState.LOADING);

        //get the initial users from the api
        getCompositeDisposable().add(getDataManager().requestUserList(1, params.requestedLoadSize)
                .subscribe(users -> {
                    // clear retry since last request succeeded
                    setRetry(null);
                    networkState.postValue(NetworkState.LOADED);
                    initialLoad.postValue(NetworkState.LOADED);
                    callback.onResult(users);
                },
                throwable -> {
                    // keep a Completable for future retry
                    setRetry(() -> loadInitial(params, callback));
                    NetworkState error = NetworkState.error(throwable.getMessage());
                    // publish the error
                    networkState.postValue(error);
                    initialLoad.postValue(error);
                }));
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {
        // set network value to loading.
        networkState.postValue(NetworkState.LOADING);

        //get the users from the api after id
        getCompositeDisposable().add(getDataManager().requestUserList(params.key, params.requestedLoadSize).subscribe(users -> {
                    // clear retry since last request succeeded
                    setRetry(null);
                    networkState.postValue(NetworkState.LOADED);
                    callback.onResult(users);
                },
                throwable -> {
                    // keep a Completable for future retry
                    setRetry(() -> loadAfter(params, callback));
                    // publish the error
                    networkState.postValue(NetworkState.error(throwable.getMessage()));
                }));
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<User> callback) {
        // ignored, since we only ever append to our initial load
    }

    /**
     * The id field is a unique identifier for users.
     */
    @NonNull
    @Override
    public Long getKey(@NonNull User item) {
        return item.getId();
    }

    @NonNull
    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    @NonNull
    public MutableLiveData<NetworkState> getInitialLoad() {
        return initialLoad;
    }

    private void setRetry(final Action action) {
        if (action == null) {
            this.retryCompletable = null;
        } else {
            this.retryCompletable = Completable.fromAction(action);
        }
    }

}
