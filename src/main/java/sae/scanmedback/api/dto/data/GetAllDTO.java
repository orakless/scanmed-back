package sae.scanmedback.api.dto.data;

public class GetAllDTO {
    int page;

    public GetAllDTO(int page) {
        this.page = page;
    };

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
