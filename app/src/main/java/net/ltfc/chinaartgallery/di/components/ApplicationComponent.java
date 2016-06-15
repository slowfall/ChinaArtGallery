package net.ltfc.chinaartgallery.di.components;

import android.content.Context;

import com.google.gson.Gson;

import net.ltfc.chinaartgallery.base.CAGApplication;
import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.di.modules.ApplicationModule;
import net.ltfc.chinaartgallery.base.model.db.DaoMaster;
import net.ltfc.chinaartgallery.base.model.rest.CAGService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by zack on 2016/3/23.
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(CAGApplication application);
    Context applicationContext();
    Gson gson();
    ToastUtils toastUtils();
    CAGService cagService();
    DaoMaster daoMaster();
}
