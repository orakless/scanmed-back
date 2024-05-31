package sae.scanmedback.utilities;

import org.springframework.data.domain.Page;
import sae.scanmedback.api.response.data.PageData;
import sae.scanmedback.api.response.data.ReportStateChangeDTO;
import sae.scanmedback.api.response.data.ResponseReportDTO;
import sae.scanmedback.entities.Report;
import sae.scanmedback.entities.ReportStateChange;
import sae.scanmedback.services.IDataService;
import sae.scanmedback.services.IReportService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ConversionUtilities {
    public static PageData<ReportStateChangeDTO> getReportSCPageData(Page<ReportStateChange> page) {
        List<ReportStateChangeDTO> items = new ArrayList<>();

        List<ReportStateChange> oldItems = page.getContent();

        for (ReportStateChange report: oldItems) {
            items.add(new ReportStateChangeDTO(report));
        }

        return new PageData<>(items, page.getTotalPages(), page.getPageable().getPageNumber());
    }

    public static PageData<ResponseReportDTO> getReportPageData(Page<Report> page, IReportService reportService)
        throws NoSuchElementException {
        List<ResponseReportDTO> items = new ArrayList<>();
        List<Report> oldItems = page.getContent();

        for (Report report: oldItems) {
            items.add(new ResponseReportDTO(report, reportService.getLastChange(report).getNewState()));
        }

        return new PageData<>(items, page.getTotalPages(), page.getPageable().getPageNumber());
    }
}
