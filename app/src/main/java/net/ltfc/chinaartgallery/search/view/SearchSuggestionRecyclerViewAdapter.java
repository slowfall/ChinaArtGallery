package net.ltfc.chinaartgallery.search.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.base.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zack on 2016/1/29.
 */
public class SearchSuggestionRecyclerViewAdapter extends RecyclerView.Adapter<SearchSuggestionRecyclerViewAdapter.ViewHolder>
        implements View.OnClickListener {
    private final String listType;
    private List<String> searchSuggestionList = new ArrayList<>();
    private OnRecyclerViewItemClick itemClickListener;

    public SearchSuggestionRecyclerViewAdapter(String listType, final OnRecyclerViewItemClick itemClickListener) {
        this.listType = listType;
        this.itemClickListener = itemClickListener;
    }

    public void setSearchSuggestionList(List<String> searchSuggestionList) {
        this.searchSuggestionList.clear();
        this.searchSuggestionList.addAll(searchSuggestionList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_search_suggestion_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item = searchSuggestionList.get(position);
        holder.content.setText(holder.item);
        if (Constants.NAMED_SEARCH_HISTORY.equals(listType)) {
            holder.icon.setImageResource(R.drawable.ic_search_history);
        } else if (Constants.NAMED_SEARCH_HOT.equals(listType)) {
            holder.icon.setImageResource(R.drawable.ic_search_hot);
        }
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return searchSuggestionList.size();
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, this);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.icon)
        ImageView icon;
        String item;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + content.getText() + "'";
        }
    }
}
