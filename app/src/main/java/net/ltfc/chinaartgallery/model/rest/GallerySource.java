package net.ltfc.chinaartgallery.model.rest;

import net.ltfc.chinaartgallery.event.GalleryEvent;
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
public class GallerySource implements DataSource {
    private final GalleryServerAPI galleryServerAPI;

    public GallerySource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GalleryServerURL.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        galleryServerAPI = retrofit.create(GalleryServerAPI.class);
    }

    @Override
    public void getPaintingList(String category) {
        Call<List<Painting>> call = galleryServerAPI.getPaintingList(category);
        call.enqueue(new MyCallBack<List<Painting>>(category));
    }

    @Override
    public void getAgeList(String age, String author) {
        Call<List<Painting>> call = galleryServerAPI.getAgeList(age, author);
        call.enqueue(new MyCallBack<List<Painting>>(age + author));
    }

    @Override
    public void search(String key) {
        Call<List<Painting>> call = galleryServerAPI.search(key);
        call.enqueue(new MyCallBack<List<Painting>>(key));
    }

    @Override
    public void getCagstoreOutline() {
        Call<List<Painting>> call = galleryServerAPI.getCagstoreOutline();
        call.enqueue(new MyCallBack<List<Painting>>());
    }

    @Override
    public void getHotSearchKeys() {
        Call<List<Painting>> call = galleryServerAPI.getHotSearchKeys();
        call.enqueue(new MyCallBack<List<Painting>>());
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
            GalleryEvent<T> event = new GalleryEvent<>(flag, response.body());
            EventBus.getDefault().post(event);
        }

        @Override
        public void onFailure(Throwable t) {

        }
    }
}
