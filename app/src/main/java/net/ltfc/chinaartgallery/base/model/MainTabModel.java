package net.ltfc.chinaartgallery.base.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.model.entities.MainTab;
import net.ltfc.chinaartgallery.common.Utils;
import net.ltfc.chinaartgallery.event.MainEvent;

import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by zack on 2016/1/11.
 */
public class MainTabModel {
    private Context context;

    public MainTabModel(Context context) {
        this.context = context;
    }

    public void fetchList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Type type = new TypeToken<List<MainTab>>() {
                }.getType();
                List<MainTab> tabList = gson.fromJson(Utils.readRawFile(context, R.raw.main), type);
                final MainEvent event = new MainEvent(tabList);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        EventBus.getDefault().post(event);
                    }
                });
            }
        }).start();
    }
}
