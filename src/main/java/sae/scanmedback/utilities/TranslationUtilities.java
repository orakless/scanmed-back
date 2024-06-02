package sae.scanmedback.utilities;

import sae.scanmedback.entities.subtypes.ReportState;

public class TranslationUtilities {
    public static String translateReportState(ReportState state) {
        String translated = "État inconnu";
        switch (state) {
            case submitted -> {
                translated = "Soumis";
            }
            case rejected -> {
                translated = "Rejetté";
            }
            case resupplying -> {
                translated = "En cours de réapprovisionnement";
            }
            case resupplied -> {
                translated = "Réapprovisionné";
            }
        }
        return translated;
    }
}
