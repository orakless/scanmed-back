package sae.scanmedback.entities;

import jakarta.persistence.*;
import sae.scanmedback.entities.subtypes.ReportState;

import java.sql.Timestamp;

@Entity
@IdClass(ReportStateChangesId.class)
@Table(name = "ReportsStateChanges")
public class ReportStateChanges {
    @Id
    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Id
    @Column(name = "action_date", nullable = false)
    private Timestamp actionDate;

    @Column(name = "old_state")
    private ReportState oldState;

    @Column(name = "new_state")
    private ReportState newState;

    public ReportStateChanges() {}

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Timestamp getActionDate() {
        return actionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        this.actionDate = actionDate;
    }

    public ReportState getOldState() {
        return oldState;
    }

    public void setOldState(ReportState oldState) {
        this.oldState = oldState;
    }

    public ReportState getNewState() {
        return newState;
    }

    public void setNewState(ReportState newState) {
        this.newState = newState;
    }
}
