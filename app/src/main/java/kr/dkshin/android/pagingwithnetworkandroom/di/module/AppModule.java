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

package kr.dkshin.android.pagingwithnetworkandroom.di.module;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import kr.dkshin.android.pagingwithnetworkandroom.data.AppDataManager;
import kr.dkshin.android.pagingwithnetworkandroom.data.DataManager;
import kr.dkshin.android.pagingwithnetworkandroom.data.db.AppDatabase;
import kr.dkshin.android.pagingwithnetworkandroom.data.db.AppDbHelper;
import kr.dkshin.android.pagingwithnetworkandroom.data.db.DbHelper;
import kr.dkshin.android.pagingwithnetworkandroom.data.local.pref.AppPreferencesHelper;
import kr.dkshin.android.pagingwithnetworkandroom.data.local.pref.PreferencesHelper;
import kr.dkshin.android.pagingwithnetworkandroom.data.remote.ApiHelper;
import kr.dkshin.android.pagingwithnetworkandroom.data.remote.AppApiHelper;
import kr.dkshin.android.pagingwithnetworkandroom.di.DatabaseInfo;
import kr.dkshin.android.pagingwithnetworkandroom.di.PreferenceInfo;
import kr.dkshin.android.pagingwithnetworkandroom.util.AppConstants;
import kr.dkshin.android.pagingwithnetworkandroom.util.rx.AppSchedulerProvider;
import kr.dkshin.android.pagingwithnetworkandroom.util.rx.SchedulerProvider;


/**
 * Created by amitshekhar on 07/07/17.
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, dbName)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }


}
