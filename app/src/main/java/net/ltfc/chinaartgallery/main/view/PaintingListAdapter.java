package net.ltfc.chinaartgallery.main.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.model.entities.Painting;
import net.ltfc.chinaartgallery.base.model.rest.CAGServerURL;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zack on 2016/1/10.
 */
public class PaintingListAdapter extends RecyclerView.Adapter<PaintingListAdapter.ViewHolder> {
    private Context context;
    private List<Painting> paintingList = new ArrayList<>();

    @Inject
    public PaintingListAdapter() {
    }

    public void setPaintingList(List<Painting> paintingList) {
        this.paintingList.clear();
        this.paintingList.addAll(paintingList);
        notifyDataSetChanged();
    }

    @Override
    public PaintingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_main_tab_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        this.context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(PaintingListAdapter.ViewHolder holder, int position) {
        Painting painting = paintingList.get(position);
        Glide.with(context)
                .load(CAGServerURL.getPaintingThumbnialURL(painting.get_id()))
                .into(holder.imageView);
        holder.paintingNameText.setText(painting.getPaintingName());
        holder.ageText.setText(painting.getAge());
        holder.authorText.setText(painting.getAuthor());
        holder.resourceLevelText.setText(painting.getResourceLevel());
    }

    @Override
    public int getItemCount() {
        return paintingList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView)
        ImageView imageView;
        @Bind(R.id.paintingNameText)
        TextView paintingNameText;
        @Bind(R.id.ageText)
        TextView ageText;
        @Bind(R.id.authorText)
        TextView authorText;
        @Bind(R.id.resourceLevelText)
        TextView resourceLevelText;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
