package sae.scanmedback.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sae.scanmedback.entities.*;

import java.util.NoSuchElementException;

public interface IReportService {
    Page<Report> getAllReportsFrom(User user, int page)
            throws IndexOutOfBoundsException, UsernameNotFoundException;

    Report getReport(int id, User user)
            throws NoSuchElementException;
    void addReport(User user, Pharmacy pharmacy, Medecine medecine);
    Page<ReportStateChange> getReportHistory(Report report, int page)
            throws IndexOutOfBoundsException;
}
