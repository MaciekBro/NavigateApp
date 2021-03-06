package com.maciekbro.navigateapp.model.dto;

import java.util.Map;

/**
 * Created by RENT on 2017-04-10.
 */

public class SearchParamsDto{

    private String searchType;
    private Map<String,String> querys;

    public SearchParamsDto(String searchType, Map<String, String> querys) {
        this.searchType = searchType;
        this.querys = querys;
    }

    public SearchParamsDto() {
    }

    public String getSearchType() {
        return searchType;
    }

    public Map<String, String> getQuerys() {
        return querys;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public void setQuerys(Map<String, String> querys) {
        this.querys = querys;
    }
}
