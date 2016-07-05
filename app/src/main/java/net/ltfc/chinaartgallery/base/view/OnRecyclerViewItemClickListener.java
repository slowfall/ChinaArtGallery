package net.ltfc.chinaartgallery.base.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zack on 2016/6/24.
 */
public interface OnRecyclerViewItemClickListener {
    void onItemClick(RecyclerView.Adapter adapter, View view, int position, long id);
}
