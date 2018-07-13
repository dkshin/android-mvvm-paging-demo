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

package kr.dkshin.android.pagingwithnetworkandroom.data.db;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import kr.dkshin.android.pagingwithnetworkandroom.data.model.db.User;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Single<List<User>> getUserListFromRoom() {
        return Single.fromCallable(() -> mAppDatabase.userDao().loadAll());
    }

    @Override
    public Single<Boolean> insertUserList(List<User> userList) {
        return Single.fromCallable(() -> {
            mAppDatabase.userDao().insertAll(userList);
            return true;
        });
    }

    @Override
    public Single<Boolean> insertUser(User user) {
        return Single.fromCallable(() -> {
            mAppDatabase.userDao().insert(user);
            return true;
        });
    }

    @Override
    public Single<Boolean> deleteUser(User user) {
        return Single.fromCallable(() -> {
            mAppDatabase.userDao().delete(user);
            return true;
        });
    }
}
