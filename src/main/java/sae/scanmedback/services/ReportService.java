package sae.scanmedback.services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sae.scanmedback.entities.*;
import sae.scanmedback.entities.subtypes.ReportState;
import sae.scanmedback.repositories.ReportRepository;
import sae.scanmedback.repositories.ReportStateChangeRepository;
import sae.scanmedback.repositories.paginated.ReportPaginatedRepository;
import sae.scanmedback.repositories.paginated.ReportStateChangePaginatedRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ReportService implements IReportService {
    private final ReportPaginatedRepository reportPaginatedRepository;
    private final ReportRepository reportRepository;
    private final ReportStateChangePaginatedRepository reportStateChangePaginatedRepository;
    private final ReportStateChangeRepository reportStateChangeRepository;
    public ReportService(ReportPaginatedRepository reportPaginatedRepository,
                         ReportRepository reportRepository,
                         ReportStateChangePaginatedRepository reportStateChangePaginatedRepository,
                         ReportStateChangeRepository reportStateChangeRepository) {
        this.reportPaginatedRepository = reportPaginatedRepository;
        this.reportRepository = reportRepository;
        this.reportStateChangePaginatedRepository = reportStateChangePaginatedRepository;
        this.reportStateChangeRepository = reportStateChangeRepository;
    }


    @Override
    public Page<Report> getAllReportsFrom(User user, int page)
        throws IndexOutOfBoundsException, UsernameNotFoundException {
        if (page < 0)
            throw new IndexOutOfBoundsException("PIN;Page index negative");

        return reportPaginatedRepository.findAllByUser(user, PageRequest.of(page, 5, Sort.by("submissionDate").descending()));
    }

    @Override
    public Page<Report> getAllReportsFromPharmacy(Pharmacy pharmacy, int page) throws IndexOutOfBoundsException, NoSuchElementException {
        if (page < 0)
            throw new IndexOutOfBoundsException("PIN;Page index negative");

        return reportPaginatedRepository.findAllByPharmacy(pharmacy, PageRequest.of(page, 5, Sort.by("submissionDate").descending()));
    }

    @Override
    public Report getReport(int id, User user)
        throws NoSuchElementException {
        Optional<Report> report = reportRepository.findByIdAndUser(id, user);

        if (report.isEmpty())
            throw new NoSuchElementException("INF;Could not find the report");

        return report.get();
    }

    @Override
    public Report getReportById(int id)
        throws NoSuchElementException {
        Optional<Report> report = reportRepository.findById(id);

        if (report.isEmpty())
            throw new NoSuchElementException("INF;Could not find the report");

        return report.get();
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

    @Override
    public Page<ReportStateChange> getReportHistory(Report report, int page)
            throws IndexOutOfBoundsException {
        if (page < 0)
            throw new IndexOutOfBoundsException("PIN;Page index negative");

        return reportStateChangePaginatedRepository.findAllByReport(report, PageRequest.of(page, 5, Sort.by("actionDate").descending()));
    }

    @Override
    public ReportStateChange getLastChange(Report report) throws NoSuchElementException {
        Optional<ReportStateChange> rsc = reportStateChangeRepository.findTopByReportOrderByActionDateDesc(report);

        if (rsc.isEmpty())
            throw new NoSuchElementException("INF;Could not find any state change.");

        return rsc.get();
    }

    @Override
    public void changeState(Report report, ReportState newState) {
        ReportStateChange newStateChange = new ReportStateChange();

        newStateChange.setNewState(newState);
        newStateChange.setActionDate(new Timestamp(new Date().getTime()));
        newStateChange.setOldState(this.getLastChange(report).getNewState());
        newStateChange.setReport(report);

        reportStateChangeRepository.save(newStateChange);
    }
}
