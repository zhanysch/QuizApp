package kg.appsstudio.daggerparttwo.ui.helper;

import android.view.View;

import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SnapHelperByOne extends LinearSnapHelper {

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY){
        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }
        final View currentView = findSnapView(layoutManager);
        if( currentView == null ){
            return RecyclerView.NO_POSITION;
        }
        return layoutManager.getPosition(currentView);
    }
}
