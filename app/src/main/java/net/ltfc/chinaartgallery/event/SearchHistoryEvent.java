package net.ltfc.chinaartgallery.event;

import net.ltfc.chinaartgallery.model.db.SearchSuggestion;

import java.util.List;

/**
 * Created by zack on 2016/1/29.
 */
public class SearchHistoryEvent {
    private List<SearchSuggestion> searchSuggestionList;

    public SearchHistoryEvent(List<SearchSuggestion> searchSuggestionList) {
        this.searchSuggestionList = searchSuggestionList;
    }

    public List<SearchSuggestion> getSearchSuggestionList() {
        return searchSuggestionList;
    }
}
