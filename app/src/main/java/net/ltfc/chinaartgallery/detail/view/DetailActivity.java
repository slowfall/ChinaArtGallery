package net.ltfc.chinaartgallery.detail.view;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.FrameLayout;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.base.view.BaseActivity;
import net.ltfc.chinaartgallery.base.view.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;

    DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

}
