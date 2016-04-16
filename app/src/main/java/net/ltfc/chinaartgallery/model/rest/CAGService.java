package net.ltfc.chinaartgallery.model.rest;

import net.ltfc.chinaartgallery.model.entities.Painting;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by zack on 2016/1/12.
 */
public interface CAGService {
    @GET("/api/{category}")
    Observable<List<Painting>> getPaintingList(
            @Path("category") String category);

    @GET("/api/age/{age}/{author}")
    Observable<List<Painting>> getAgeList(
            @Path("age") String age,
            @Path("author") String author);

    @GET("/api/search/{key}")
    Observable<List<Painting>> search(
            @Path("key") String key);

    @GET("/cagstore/outline.json")
    Observable<List<Painting>> getCagstoreOutline();

    @GET("/api/hotsearch")
    Observable<List<String>> getHotSearchKeys();
}
