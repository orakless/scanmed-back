package sae.scanmedback.api.dto;

import sae.scanmedback.entities.subtypes.ReportState;

public class StateChangeDTO {
    ReportState newState;

    public ReportState getNewState() {
        return newState;
    }

    public void setNewState(ReportState newState) {
        this.newState = newState;
    }
}
