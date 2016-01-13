package net.ltfc.chinaartgallery.ui.fragments;

import android.support.v4.app.Fragment;

/**
 * Created by zack on 2016/1/13.
 */
public class BaseFragment extends Fragment {
    private boolean isShownToUser = false;

    @Override
    public void onResume() {
        super.onResume();
        setUserVisibleHint(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isShownToUser && isVisibleToUser && isVisible()) {
            isShownToUser = true;
            //TODO load data
        }
    }
}
