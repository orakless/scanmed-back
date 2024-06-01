package sae.scanmedback.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sae.scanmedback.api.response.data.ReportStateChangeDTO;
import sae.scanmedback.entities.*;
import sae.scanmedback.entities.subtypes.ReportState;

import java.util.NoSuchElementException;

public interface IReportService {
    Page<Report> getAllReportsFrom(User user, int page)
            throws IndexOutOfBoundsException, UsernameNotFoundException;
    Page<Report> getAllReportsFromPharmacy(Pharmacy pharmacy, int page)
            throws IndexOutOfBoundsException, NoSuchElementException;
    Report getReport(int id, User user)
            throws NoSuchElementException;
    Report getReportById(int id)
            throws NoSuchElementException;
    void addReport(User user, Pharmacy pharmacy, Medecine medecine);
    Page<ReportStateChange> getReportHistory(Report report, int page)
            throws IndexOutOfBoundsException;
    ReportStateChange getLastChange(Report report)
            throws NoSuchElementException;
    void changeState(Report report, ReportState newState);
}
