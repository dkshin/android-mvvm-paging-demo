package kr.dkshin.android.pagingwithnetworkandroom.util.fresco;

import android.graphics.PointF;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by SHIN on 2018. 7. 5..
 */
public class FrescoHelper {

    public static final PointF FOCUS_POINT = new PointF(1, 0.5f);

    public static void setImage(SimpleDraweeView simpleDraweeView, Uri uri) {
        try {
            simpleDraweeView.setLegacyVisibilityHandlingEnabled(true);

            DraweeController ctrl = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setTapToRetryEnabled(true)
                    .setAutoPlayAnimations(true)
                    .setOldController(simpleDraweeView.getController())
                    .build();
            simpleDraweeView.setController(ctrl);

            simpleDraweeView.getHierarchy().setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);
            simpleDraweeView.getHierarchy().setActualImageFocusPoint(FOCUS_POINT);
        } catch (NullPointerException e) {
            simpleDraweeView.setController(null);
        }


    }
}
