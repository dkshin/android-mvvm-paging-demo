package kr.dkshin.android.pagingwithnetworkandroom.view.type1;


import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import kr.dkshin.android.pagingwithnetworkandroom.ViewModelProviderFactory;
import kr.dkshin.android.pagingwithnetworkandroom.data.DataManager;
import kr.dkshin.android.pagingwithnetworkandroom.util.rx.SchedulerProvider;

@Module
public class ONPActivityModule {

    @Provides
    ONPViewModel onpViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new ONPViewModel(dataManager, schedulerProvider);
    }

    @Provides
    ViewModelProvider.Factory provideONPViewModel(ONPViewModel onpViewModel) {
        return new ViewModelProviderFactory<>(onpViewModel);
    }

    @Provides
    LinearLayoutManager provideONPLinearLayoutManager(ONPActivity activity) {
        return new LinearLayoutManager(activity);
    }

}