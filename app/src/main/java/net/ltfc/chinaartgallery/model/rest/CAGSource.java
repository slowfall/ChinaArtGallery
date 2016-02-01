package net.ltfc.chinaartgallery.model.rest;

import android.util.Log;
import android.view.SearchEvent;

import net.ltfc.chinaartgallery.event.GalleryEvent;
import net.ltfc.chinaartgallery.event.SearchHotEvent;
import net.ltfc.chinaartgallery.model.entities.Painting;

import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zack on 2016/1/12.
 */
public class CAGSource implements DataSource {
    private static CAGServerAPI cagServerAPI;

    public CAGSource() {
        if (cagServerAPI == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CAGServerURL.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            cagServerAPI = retrofit.create(CAGServerAPI.class);
        }
    }

    @Override
    public void getPaintingList(String category) {
        Call<List<Painting>> call = cagServerAPI.getPaintingList(category);
        call.enqueue(new MyCallBack<List<Painting>>(category));
    }

    @Override
    public void getAgeList(String age, String author) {
        Call<List<Painting>> call = cagServerAPI.getAgeList(age, author);
        call.enqueue(new MyCallBack<List<Painting>>(age + author));
    }

    @Override
    public void search(String key) {
        Call<List<Painting>> call = cagServerAPI.search(key);
        call.enqueue(new MyCallBack<List<Painting>>(key));
    }

    @Override
    public void getCagstoreOutline() {
        Call<List<Painting>> call = cagServerAPI.getCagstoreOutline();
        call.enqueue(new MyCallBack<List<Painting>>());
    }

    @Override
    public void getHotSearchKeys() {
        Call<List<String>> call = cagServerAPI.getHotSearchKeys();
        call.enqueue(new MyCallBack<List<String>>());
    }

    private static class MyCallBack<T> implements Callback<T> {
        private String flag;

        public MyCallBack() {

        }

        public MyCallBack(String flag) {
            this.flag = flag;
        }

        @Override
        public void onResponse(Response<T> response) {
            T body = response.body();
            if (body == null) {
                onFailure(new NullPointerException("Response body is null"));
                return;
            }
            Object event = null;
            Log.d("onResponse", body.toString());
            if (body instanceof List) {
                List bodyList = (List) body;
                if (bodyList.isEmpty()) {
                    // TODO: 2016/1/29 handle body list empty exception
                    return;
                }
                Object item = bodyList.get(0);
                if (item instanceof Painting) {
                    event = new GalleryEvent(flag, (List<Painting>) bodyList);
                } else if (item instanceof String) {
                    event = new SearchHotEvent((List<String>) bodyList);
                }
            }
            if (event != null) {
                EventBus.getDefault().post(event);
            }
        }

        @Override
        public void onFailure(Throwable t) {
            Log.e("HTTP request", t.toString());
        }
    }
}
