package vlsu.inventory.model;

import java.time.LocalDateTime;

public class Rent {
    private int id;
    private Responsible responsible;
    private Equipment equipment;
    private Audience audience;
    private LocalDateTime startRentDateTime;
    private LocalDateTime endRentDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public LocalDateTime getStartRentDateTime() {
        return startRentDateTime;
    }

    public void setStartRentDateTime(LocalDateTime startRentDateTime) {
        this.startRentDateTime = startRentDateTime;
    }

    public LocalDateTime getEndRentDateTime() {
        return endRentDateTime;
    }

    public void setEndRentDateTime(LocalDateTime endRentDateTime) {
        this.endRentDateTime = endRentDateTime;
    }
}
