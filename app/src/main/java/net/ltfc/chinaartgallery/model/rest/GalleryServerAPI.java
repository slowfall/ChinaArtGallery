package net.ltfc.chinaartgallery.model.rest;

import net.ltfc.chinaartgallery.model.entities.Painting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zack on 2016/1/12.
 */
public interface GalleryServerAPI {
    @GET("/api/{category}")
    Call<List<Painting>> getPaintingList(
            @Path("category") String category);

    @GET("/api/age/{age}/{author}")
    Call<List<Painting>> getAgeList(
            @Path("age") String age,
            @Path("author") String author);

    @GET("/api/search/{key}")
    Call<List<Painting>> search(
            @Path("key") String key);

    @GET("/cagstore/outline.json")
    Call<List<Painting>> getCagstoreOutline();

    @GET("/api/hotserach")
    Call<List<Painting>> getHotSearchKeys();
}
