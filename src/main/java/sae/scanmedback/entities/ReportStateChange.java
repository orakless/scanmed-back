package sae.scanmedback.entities;

import jakarta.persistence.*;
import sae.scanmedback.entities.subtypes.ReportState;

import java.sql.Timestamp;

@Entity
@IdClass(ReportStateChangeId.class)
@Table(name = "reportsstatechanges")
public class ReportStateChange {
    @Id
    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private Report report;

    @Id
    @Column(name = "action_date", nullable = false)
    private Timestamp actionDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "old_state")
    private ReportState oldState;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_state", nullable = false)
    private ReportState newState;

    public ReportStateChange() {}

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
