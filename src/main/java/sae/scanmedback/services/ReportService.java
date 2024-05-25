package sae.scanmedback.services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sae.scanmedback.entities.*;
import sae.scanmedback.entities.subtypes.ReportState;
import sae.scanmedback.repositories.ReportRepository;
import sae.scanmedback.repositories.ReportStateChangeRepository;
import sae.scanmedback.repositories.paginated.ReportPaginatedRepository;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Transactional
public class ReportService implements IReportService {
    private final ReportPaginatedRepository reportPaginatedRepository;
    private final ReportRepository reportRepository;
    private final ReportStateChangeRepository reportStateChangeRepository;

    public ReportService(ReportPaginatedRepository reportPaginatedRepository,
                         ReportRepository reportRepository,
                         ReportStateChangeRepository reportStateChangeRepository) {
        this.reportPaginatedRepository = reportPaginatedRepository;
        this.reportRepository = reportRepository;
        this.reportStateChangeRepository = reportStateChangeRepository;
    }


    @Override
    public Page<Report> getAllReportsFrom(User user, int page)
        throws IndexOutOfBoundsException, UsernameNotFoundException {
        if (page < 0)
            throw new IndexOutOfBoundsException("PIN;Page index negative");

        return reportPaginatedRepository.findAllByUser(user, PageRequest.of(page, 5));
    }

    @Override
    public void addReport(User user, Pharmacy pharmacy, Medecine medecine) {
        Report report = new Report();
        ReportStateChange initialState = new ReportStateChange();
        Timestamp date = new Timestamp(new Date().getTime());

        report.setUser(user);
        report.setPharmacy(pharmacy);
        report.setMedecine(medecine);
        report.setSubmissionDate(date);
        report = reportRepository.saveAndFlush(report);

        initialState.setReport(report);
        initialState.setActionDate(date);
        initialState.setNewState(ReportState.submitted);
        reportStateChangeRepository.save(initialState);
    }
}
