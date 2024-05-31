package sae.scanmedback.api.response.data;

import sae.scanmedback.entities.ReportStateChange;
import sae.scanmedback.entities.subtypes.ReportState;

import java.sql.Timestamp;

public class ReportStateChangeDTO {
    private final Timestamp actionDate;
    private final ReportState oldState;
    private final ReportState newState;

    public ReportStateChangeDTO(ReportStateChange rsc) {
        this.actionDate = rsc.getActionDate();
        this.oldState = rsc.getOldState();
        this.newState = rsc.getNewState();
    }

    public ReportState getNewState() {
        return newState;
    }

    public ReportState getOldState() {
        return oldState;
    }

    public Timestamp getActionDate() {
        return actionDate;
    }
}
