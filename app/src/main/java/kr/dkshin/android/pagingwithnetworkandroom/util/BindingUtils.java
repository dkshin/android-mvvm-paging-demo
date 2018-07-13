package kr.dkshin.android.pagingwithnetworkandroom.util;///*

//*  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *      https://mindorks.com/license/apache-v2
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License
// */

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

import androidx.databinding.BindingAdapter;
import kr.dkshin.android.pagingwithnetworkandroom.util.fresco.FrescoHelper;

/**
 * Created by amitshekhar on 11/07/17.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(SimpleDraweeView draweeView, String url) {
        FrescoHelper.setImage(draweeView, Uri.parse(url));
    }

//    @BindingAdapter({"onpAdapter"})
//    public static void addONPItems(RecyclerView recyclerView, List<User> userList) {
//        HomeAdapter adapter = (HomeAdapter) recyclerView.getAdapter();
//        if (adapter != null) {
//            adapter.addItems(collectionList);
//        }
//    }


}
