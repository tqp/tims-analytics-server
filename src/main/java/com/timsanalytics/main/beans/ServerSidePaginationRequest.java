package com.timsanalytics.main.beans;

public class ServerSidePaginationRequest {
    private String filter;
    private String sortColumn;
    private String sortDirection;
    private int pageIndex;
    private int pageSize;
    private Person advancedFilters;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Person getAdvancedFilters() {
        return advancedFilters;
    }

    public void setAdvancedFilters(Person advancedFilters) {
        this.advancedFilters = advancedFilters;
    }
}
