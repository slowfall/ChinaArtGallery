package net.ltfc.chinaartgallery.base.view;

import android.support.v4.app.Fragment;

import net.ltfc.chinaartgallery.base.CAGApplication;
import net.ltfc.chinaartgallery.di.components.ApplicationComponent;
import net.ltfc.chinaartgallery.di.modules.ActivityModule;
import net.ltfc.chinaartgallery.di.modules.FragmentModule;
import net.ltfc.chinaartgallery.main.di.MainModule;

/**
 * Created by zack on 2016/1/13.
 */
public class BaseFragment extends Fragment {

    protected ApplicationComponent getApplicationComponent() {
        return ((CAGApplication) (getActivity()).getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return ((BaseActivity) getActivity()).getActivityModule();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected MainModule getMainModule() {
        return new MainModule();
    }

}
