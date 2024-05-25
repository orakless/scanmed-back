package sae.scanmedback.entities;

import java.io.Serializable;
import java.sql.Timestamp;

public class ReportStateChangeId implements Serializable {
    private Report report;

    private Timestamp actionDate;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (!(object instanceof ReportStateChangeId otherId)) return false;

        return otherId.report == this.report
                && otherId.actionDate.equals(this.actionDate);
    }

    @Override
    public int hashCode() {
        return report.hashCode() + actionDate.hashCode();
    }
}
