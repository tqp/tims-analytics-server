package com.timsanalytics.main.beans;

public class ServerSidePaginationRequest {
    private String nameFilter;
    private String stateFilter;
    private String sortColumn;
    private String sortDirection;
    private int pageIndex;
    private int pageSize;
    private Person advancedFilters;

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public String getStateFilter() {
        return stateFilter;
    }

    public void setStateFilter(String stateFilter) {
        this.stateFilter = stateFilter;
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
