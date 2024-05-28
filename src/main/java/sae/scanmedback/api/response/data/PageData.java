package sae.scanmedback.api.response.data;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageData<T> {
    List<T> items;
    int totalPageNumber;
    int currentPage;



    boolean isLastPage;

    public PageData(Page<T> page) {
        this.items = page.getContent();
        this.totalPageNumber = page.getTotalPages();
        this.currentPage = page.getPageable().getPageNumber();
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
