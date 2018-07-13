package kr.dkshin.android.pagingwithnetworkandroom.data.datasource;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import io.reactivex.disposables.CompositeDisposable;
import kr.dkshin.android.pagingwithnetworkandroom.data.DataManager;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.db.User;
import kr.dkshin.android.pagingwithnetworkandroom.util.rx.SchedulerProvider;

/**
 * Created by Ahmed Abd-Elmeged on 2/13/2018.
 * <p>
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
public class UsersDataSourceFactory extends DataSource.Factory<Long, User> {

    private CompositeDisposable compositeDisposable;
    private DataManager dataManager;
    private SchedulerProvider schedulerProvider;

    private MutableLiveData<UsersDataSource> usersDataSourceLiveData = new MutableLiveData<>();

    public UsersDataSourceFactory(DataManager dataManager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public DataSource<Long, User> create() {
        UsersDataSource usersDataSource = new UsersDataSource(dataManager, schedulerProvider, compositeDisposable);
        usersDataSourceLiveData.postValue(usersDataSource);
        return usersDataSource;
    }

    @NonNull
    public MutableLiveData<UsersDataSource> getUsersDataSourceLiveData() {
        return usersDataSourceLiveData;
    }

}
