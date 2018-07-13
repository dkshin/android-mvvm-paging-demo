package kr.dkshin.android.pagingwithnetworkandroom.view.main;


import dagger.Module;
import dagger.Provides;
import kr.dkshin.android.pagingwithnetworkandroom.data.DataManager;
import kr.dkshin.android.pagingwithnetworkandroom.util.rx.SchedulerProvider;

@Module
public class MainActivityModule {

    @Provides
    MainViewModel provideMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new MainViewModel(dataManager, schedulerProvider);
    }

}