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

package kr.dkshin.android.pagingwithnetworkandroom.di.builder;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import kr.dkshin.android.pagingwithnetworkandroom.view.main.MainActivity;
import kr.dkshin.android.pagingwithnetworkandroom.view.main.MainActivityModule;
import kr.dkshin.android.pagingwithnetworkandroom.view.type1.ONPActivity;
import kr.dkshin.android.pagingwithnetworkandroom.view.type1.ONPActivityModule;


/**
 * Created by amitshekhar on 14/09/17.
 */
@Module
public abstract class ActivityBuilder {

//    @ContributesAndroidInjector(modules = SignActivityModule.class)
//    abstract SignActivity bindSignActivity();

    @ContributesAndroidInjector(modules = {
            MainActivityModule.class,
    })
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {
            ONPActivityModule.class,
    })
    abstract ONPActivity bindONPActivity();

}
