package sae.scanmedback.api.response.data;

import java.util.List;

public class PageData<T> {
    List<T> items;
    int totalPageNumber;
    int currentPage;



    boolean isLastPage;

    public PageData(List<T> items, int totalPageNumber, int currentPage) {
        this.items = items;
        this.totalPageNumber = totalPageNumber;
        this.currentPage = currentPage;
        this.isLastPage = totalPageNumber - 1 == currentPage;
    }

    public List<T> getItems() {
        return items;
    }
    public int getTotalPageNumber() {
        return totalPageNumber;
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public boolean isLastPage() {
        return isLastPage;
    }
}
