package net.ltfc.chinaartgallery.detail.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.Constants;
import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.base.model.rest.CAGServerURL;
import net.ltfc.chinaartgallery.base.view.BaseFragment;
import net.ltfc.chinaartgallery.common.ToastUtils;
import net.ltfc.chinaartgallery.detail.di.DaggerDetailComponent;
import net.ltfc.chinaartgallery.detail.di.DetailComponent;
import net.ltfc.chinaartgallery.detail.di.DetailModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends BaseFragment implements DetailView {
    @Inject
    ToastUtils toastUtils;
    private Painting painting;
    @Bind(R.id.wvContent)
    DetailContent wvContent;

    public static DetailFragment newInstance(Bundle args) {
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(args);
        return detailFragment;
    }

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            painting = getArguments().getParcelable(Constants.KEY_PAINTING);
        }
        DetailComponent detailComponent = DaggerDetailComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .fragmentModule(getFragmentModule())
                .detailModule(new DetailModule(this)).build();
        detailComponent.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        if (painting != null) {
            initWebView(wvContent);
            wvContent.loadUrl(CAGServerURL.getPaintingOutlineURL(painting.get_id()));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initWebView(WebView webView) {
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

}
