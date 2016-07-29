package net.ltfc.chinaartgallery.base.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

import net.ltfc.chinaartgallery.base.CAGApplication;
import net.ltfc.chinaartgallery.di.components.ApplicationComponent;
import net.ltfc.chinaartgallery.di.modules.ActivityModule;

/**
 * Created by zack on 2016/1/12.
 */
public class BaseActivity extends AppCompatActivity {
    private ActivityModule activityModule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((CAGApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        if (activityModule == null) {
            activityModule = new ActivityModule(this);
        }
        return activityModule;
    }
}
