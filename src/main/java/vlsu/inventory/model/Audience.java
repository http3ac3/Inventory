package vlsu.inventory.model;

public class Audience {
    private int id;
    private int buildingNumber;
    private int audienceNumber;
    private String fullAudience;

    public String getFullAudience() {
        return fullAudience;
    }

    public void setFullAudience() {
        this.fullAudience = audienceNumber + "-" + buildingNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(int buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public int getAudienceNumber() {
        return audienceNumber;
    }

    public void setAudienceNumber(int audienceNumber) {
        this.audienceNumber = audienceNumber;
    }
}
