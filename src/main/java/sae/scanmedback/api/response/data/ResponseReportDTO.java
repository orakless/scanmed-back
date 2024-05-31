package sae.scanmedback.api.response.data;

import sae.scanmedback.entities.Report;
import sae.scanmedback.entities.subtypes.ReportState;

import java.sql.Timestamp;

public class ResponseReportDTO {
    Timestamp submissionDate;
    int id;
    int pharmacyId;
    String CIP;
    ReportState currentState;

    public ResponseReportDTO(Report report, ReportState currentState) {
        this.submissionDate = report.getSubmissionDate();
        this.id = report.getId();
        this.pharmacyId = report.getPharmacy().getId();
        this.CIP = report.getMedecine().getCIP();
        this.currentState = currentState;
    }

    public Timestamp getSubmissionDate() {
        return submissionDate;
    }

    public int getId() {
        return id;
    }

    public int getPharmacyId() {
        return pharmacyId;
    }

    public String getCIP() {
        return CIP;
    }

    public ReportState getCurrentState() {
        return currentState;
    }
}
