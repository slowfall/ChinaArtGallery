package net.ltfc.chinaartgallery.detail.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.base.view.BaseActivity;
import net.ltfc.chinaartgallery.base.view.StatusBarCompat;
import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.di.components.ActivityComponent;
import net.ltfc.chinaartgallery.di.components.DaggerActivityComponent;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    DetailFragment detailFragment;
    @Inject
    ToastUtils toastUtils;
    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        StatusBarCompat.compat(this, getResources().getColor(R.color.colorPrimaryDark));
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            detailFragment = DetailFragment.newInstance(bundle);
            getSupportFragmentManager().beginTransaction().
                    add(R.id.frameLayout, detailFragment).commit();
            if (bundle != null) {
                Painting painting = bundle.getParcelable(Constants.KEY_PAINTING);
                if (painting != null) {
                    String paintingName = painting.getPaintingName();
                    if (!TextUtils.isEmpty(paintingName)) {
                        getSupportActionBar().setTitle(paintingName);
                    }
                }
            }
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initializeInjector();
    }

    private void initializeInjector() {
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        activityComponent.inject(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("DetailActivity", "dispatchTouchEvent");
//        toastUtils.showShort("dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("DetailActivity", "onTouchEvent");

        return super.onTouchEvent(event);
    }
}
