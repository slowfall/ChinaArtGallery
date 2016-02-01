package net.ltfc.chinaartgallery.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ltfc.chinaartgallery.R;
import net.ltfc.chinaartgallery.ui.views.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by zack on 2016/1/29.
 */
public class SearchSuggestionRecyclerViewAdapter extends RecyclerView.Adapter<SearchSuggestionRecyclerViewAdapter.ViewHolder> {
    private final SearchView mListener;
    private List<String> searchSuggestionList = new ArrayList<>();

    public SearchSuggestionRecyclerViewAdapter(SearchView listener) {
        mListener = listener;
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSearchItemClick(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchSuggestionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.content)
        TextView content;
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
