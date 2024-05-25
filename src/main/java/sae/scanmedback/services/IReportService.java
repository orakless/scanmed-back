package sae.scanmedback.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sae.scanmedback.entities.*;

public interface IReportService {
    Page<Report> getAllReportsFrom(User user, int page)
            throws IndexOutOfBoundsException, UsernameNotFoundException;
    void addReport(User user, Pharmacy pharmacy, Medecine medecine);

}
