package vlsu.inventory.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Equipment {
    private int id;
    private String inventoryNumber;
    private String name;
    private BigDecimal wearRate;
    private LocalDate commissioningDate;
    private int commissioningActNumber;
    private BigDecimal initialCost;
    private BigDecimal generalWear;
    private BigDecimal residualCost;
    private LocalDate decommissioningDate;
    private Integer decommissioningActNumber;
    private String description;
    private Responsible responsible;
    private Audience audience;
    private Subcategory subcategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWearRate() {
        return wearRate;
    }

    public void setWearRate(BigDecimal wearRate) {
        this.wearRate = wearRate;
    }

    public LocalDate getCommissioningDate() {
        return commissioningDate;
    }

    public void setCommissioningDate(LocalDate commissioningDate) {
        this.commissioningDate = commissioningDate;
    }

    public int getCommissioningActNumber() {
        return commissioningActNumber;
    }

    public void setCommissioningActNumber(int commissioningActNumber) {
        this.commissioningActNumber = commissioningActNumber;
    }

    public BigDecimal getInitialCost() {
        return initialCost;
    }

    public void setInitialCost(BigDecimal initialCost) {
        this.initialCost = initialCost;
    }

    public BigDecimal getGeneralWear() {
        return generalWear;
    }

    public void setGeneralWear(BigDecimal generalWear) {
        this.generalWear = generalWear;
    }

    public BigDecimal getResidualCost() {
        return residualCost;
    }

    public void setResidualCost(BigDecimal residualCost) {
        this.residualCost = residualCost;
    }

    public LocalDate getDecommissioningDate() {
        return decommissioningDate;
    }

    public void setDecommissioningDate(LocalDate decommissioningDate) {
        this.decommissioningDate = decommissioningDate;
    }

    public Integer getDecommissioningActNumber() {
        return decommissioningActNumber;
    }

    public void setDecommissioningActNumber(Integer decommissioningActNumber) {
        this.decommissioningActNumber = decommissioningActNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }
}
