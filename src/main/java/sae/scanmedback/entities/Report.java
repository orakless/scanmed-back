package sae.scanmedback.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Reports")
public class Report {
    @Id
    @Column(unique = true, nullable = false)
    @SequenceGenerator(name = "report_seq", sequenceName = "report_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "pharmacy_id", nullable = false)
    private Pharmacy pharmacy;

    @ManyToOne
    @JoinColumn(name = "medecine_CIP", nullable = false)
    private Medecine medecine;

    @Column(name = "submission_date")
    private Timestamp submissionDate;

    public Report() {}

    public int getId() {
        return id;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Medecine getMedecine() {
        return medecine;
    }

    public void setMedecine(Medecine medecine) {
        this.medecine = medecine;
    }

    public Timestamp getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(Timestamp submissionDate) {
        this.submissionDate = submissionDate;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
