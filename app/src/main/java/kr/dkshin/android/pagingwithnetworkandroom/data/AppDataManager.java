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

package kr.dkshin.android.pagingwithnetworkandroom.data;

import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import kr.dkshin.android.pagingwithnetworkandroom.data.db.DbHelper;
import kr.dkshin.android.pagingwithnetworkandroom.data.local.pref.PreferencesHelper;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.db.User;
import kr.dkshin.android.pagingwithnetworkandroom.data.remote.ApiHelper;

/**
 * Created by amitshekhar on 07/07/17.
 */
@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    @Override
    public Single<List<User>> getUserListFromRoom() {
        return mDbHelper.getUserListFromRoom();
    }

    @Override
    public Single<Boolean> insertUserList(List<User> userList) {
        return mDbHelper.insertUserList(userList);
    }

    @Override
    public Single<Boolean> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Single<Boolean> deleteUser(User user) {
        return mDbHelper.deleteUser(user);
    }

    @Override
    public Single<List<User>> requestUserList(long userId, int perPage) {
        return mApiHelper.requestUserList(userId, perPage);
    }
}
