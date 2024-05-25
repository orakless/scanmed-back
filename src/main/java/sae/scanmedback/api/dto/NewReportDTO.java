package sae.scanmedback.api.dto;

public class NewReportDTO {
    int pharmacyId;
    String medecineCIP;

    public NewReportDTO() {}

    public int getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(int pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public String getMedecineCIP() {
        return medecineCIP;
    }

    public void setMedecineCIP(String medecineCIP) {
        this.medecineCIP = medecineCIP;
    }
}
