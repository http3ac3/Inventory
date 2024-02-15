package vlsu.inventory.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vlsu.inventory.model.*;
import vlsu.inventory.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class EquipmentController {
    private final EquipmentRepository equipmentRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ResponsibleRepository responsibleRepository;
    private final AudienceRepository audienceRepository;

    private final RentRepository rentRepository;

    public EquipmentController(EquipmentRepository equipmentRepository, CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository, ResponsibleRepository responsibleRepository, AudienceRepository audienceRepository, RentRepository rentRepository) {
        this.equipmentRepository = equipmentRepository;
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.responsibleRepository = responsibleRepository;
        this.audienceRepository = audienceRepository;
        this.rentRepository = rentRepository;
    }
    @GetMapping("/equipment")
    public String equipmentInfo(@RequestParam int id, Model model) {
        Equipment equipment = equipmentRepository.getById(id);

        model.addAttribute("equipmentId", id);

        model.addAttribute("inventoryNumber", equipment.getInventoryNumber());
        model.addAttribute("name", equipment.getName());
        model.addAttribute("wearRate", equipment.getWearRate());
        model.addAttribute("generalWear", equipment.getGeneralWear());
        model.addAttribute("buildingNumber", equipment.getAudience().getBuildingNumber());
        model.addAttribute("audienceNumber", equipment.getAudience().getAudienceNumber());
        model.addAttribute("commissioningDate", equipment.getCommissioningDate());
        model.addAttribute("decommissioningDate", equipment.getDecommissioningDate());
        model.addAttribute("commissioningActNumber", equipment.getCommissioningActNumber());
        model.addAttribute("decommissioningActNumber", equipment.getDecommissioningActNumber());
        model.addAttribute("initialCost", equipment.getInitialCost());
        model.addAttribute("residualCost", equipment.getResidualCost());
        model.addAttribute("categories", categoryRepository.getAll());
        model.addAttribute("subcategories", subcategoryRepository.getAll());
        model.addAttribute("responsibleList", responsibleRepository.getAll());
        model.addAttribute("description", equipment.getDescription());

        model.addAttribute("categoryId",
                equipment.getSubcategory().getCategory().getId());
        model.addAttribute("subcategoryId",
                equipment.getSubcategory().getId());
        model.addAttribute("responsibleId",
                equipment.getResponsible().getId());

        return "equipment-info.html";
    }

    @GetMapping("/equipment/new")
    public String getNewEquipment(Model model) {
        model.addAttribute("categories",
                categoryRepository.getAll());
        model.addAttribute("subcategories",
                subcategoryRepository.getAll());
        model.addAttribute("responsibleList",
                responsibleRepository.getAll());
        return "equipment-new.html";
    }

    @PostMapping("/equipment/new")
    public String addEquipment(
            @RequestParam String inventoryNumber,
            @RequestParam String name,
            @RequestParam BigDecimal wearRate,
            @RequestParam int buildingNumber,
            @RequestParam int audienceNumber,
            @RequestParam LocalDate commissioningDate,
            @RequestParam Integer commissioningActNumber,
            @RequestParam BigDecimal initialCost,
            @RequestParam String description,
            @RequestParam int responsibleId,
            @RequestParam int subcategoryId
            ) {
        Equipment equipment = new Equipment();
        equipment.setInventoryNumber(inventoryNumber);
        equipment.setName(name);
        equipment.setWearRate(wearRate);
        equipment.setCommissioningDate(commissioningDate);
        equipment.setCommissioningActNumber(commissioningActNumber);
        equipment.setInitialCost(initialCost);
        equipment.setResidualCost(initialCost);
        equipment.setDescription(description);

        Responsible responsible = new Responsible();
        responsible.setId(responsibleId);
        equipment.setResponsible(responsible);

        int audienceIdByNumbers = audienceRepository.getIdByNumbers(buildingNumber, audienceNumber);
        if (audienceIdByNumbers == -1) {
            audienceRepository.addAudience(buildingNumber, audienceNumber);
        }
        Audience audience = audienceRepository.getById(
                audienceRepository.getIdByNumbers(buildingNumber, audienceNumber));
        equipment.setAudience(audience);

        Subcategory subcategory = subcategoryRepository.getById(subcategoryId);
        equipment.setSubcategory(subcategory);

        equipmentRepository.addEquipment(equipment);
        return "redirect:/main";
    }

    @GetMapping("/equipment/delete")
    public String deleteEquipment(@RequestParam int id) {
        equipmentRepository.deleteById(id);
        return "redirect:/main";
    }

    @PostMapping("/equipment/edit")
    public String editEquipment(
            @RequestParam int equipmentId,
            @RequestParam String inventoryNumber,
            @RequestParam String name,
            @RequestParam BigDecimal wearRate,
            @RequestParam LocalDate commissioningDate,
            @RequestParam int commissioningActNumber,
            @RequestParam BigDecimal initialCost,
            @RequestParam BigDecimal generalWear,
            @RequestParam BigDecimal residualCost,
            @RequestParam(required = false) LocalDate decommissioningDate,
            @RequestParam String decommissioningActNumber,
            @RequestParam String description,
            @RequestParam int responsibleId,
            @RequestParam int buildingNumber,
            @RequestParam int audienceNumber,
            @RequestParam int subcategoryId
    ) {
        Equipment equipment = new Equipment();
        equipment.setId(equipmentId);
        equipment.setInventoryNumber(inventoryNumber);
        equipment.setName(name);
        equipment.setWearRate(wearRate);
        equipment.setCommissioningDate(commissioningDate);
        equipment.setCommissioningActNumber(commissioningActNumber);
        equipment.setInitialCost(initialCost);
        equipment.setGeneralWear(generalWear);
        equipment.setResidualCost(residualCost);
        equipment.setDecommissioningDate(decommissioningDate);
        equipment.setDecommissioningActNumber(decommissioningActNumber.isEmpty() ? null : Integer.parseInt(decommissioningActNumber));
        equipment.setDescription(description);

        Responsible responsible = new Responsible();
        responsible.setId(responsibleId);
        equipment.setResponsible(responsible);

        int audienceIdByNumbers = audienceRepository.getIdByNumbers(buildingNumber, audienceNumber);
        if (audienceIdByNumbers == -1) {
            audienceRepository.addAudience(buildingNumber, audienceNumber);
        }
        Audience audience = audienceRepository.getById(
                audienceRepository.getIdByNumbers(buildingNumber, audienceNumber));
        equipment.setAudience(audience);

        Subcategory subcategory = subcategoryRepository.getById(subcategoryId);
        equipment.setSubcategory(subcategory);

        equipmentRepository.editByInventoryNumber(equipment);
        return "redirect:/main";
    }

    @GetMapping("/history")
    public String rentHistory(@RequestParam int id, Model model) {
        Equipment equipment = equipmentRepository.getById(id);
        List<Rent> rents = rentRepository.getRentHistory(equipment);
        model.addAttribute("rents", rents);
        model.addAttribute("equipmentName",equipment.getName());
        model.addAttribute("equipmentId", id);
        return "equipment-rent-history.html";
    }
}
