package vlsu.inventory.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vlsu.inventory.model.Audience;
import vlsu.inventory.model.Equipment;
import vlsu.inventory.model.Rent;
import vlsu.inventory.model.Responsible;
import vlsu.inventory.repository.AudienceRepository;
import vlsu.inventory.repository.EquipmentRepository;
import vlsu.inventory.repository.RentRepository;
import vlsu.inventory.repository.ResponsibleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class RentController {
    private final EquipmentRepository equipmentRepository;
    private final ResponsibleRepository responsibleRepository;
    private final AudienceRepository audienceRepository;
    private final RentRepository rentRepository;
    public RentController(EquipmentRepository equipmentRepository, ResponsibleRepository responsibleRepository, AudienceRepository audienceRepository, RentRepository rentRepository) {
        this.equipmentRepository = equipmentRepository;
        this.responsibleRepository = responsibleRepository;
        this.audienceRepository = audienceRepository;
        this.rentRepository = rentRepository;
    }

    @GetMapping("/rent")
    public String getMainRent(@RequestParam int responsibleId, Model model) {
        Responsible r = responsibleRepository.getById(responsibleId);
        model.addAttribute("responsibleFullName", r.getFullName());
        model.addAttribute("responsibleId", responsibleId);
        return "renter-main.html";
    }
    @PostMapping("/rent/search")
    public String searchRent(
            @RequestParam int responsibleId,
            @RequestParam String inventoryNumber,
            Model model) {
        Equipment e = equipmentRepository.getByInventoryNumber(inventoryNumber);
        if (e.getId() < 1) {
            model.addAttribute("message", "Оборудования с таким инвентарным номером нет");
            model.addAttribute("responsibleId", responsibleId);
            Responsible r = responsibleRepository.getById(responsibleId);
            model.addAttribute("responsibleFullName", r.getFullName());
            return "renter-main.html";
        }

        if (equipmentRepository.isRented(e)) {
            model.addAttribute("message", "Оборудование уже взято кем-то другим!");
            model.addAttribute("responsibleId", responsibleId);
            Responsible r = responsibleRepository.getById(responsibleId);
            model.addAttribute("responsibleFullName", r.getFullName());
            return "renter-main.html";
        }
        Responsible r = responsibleRepository.getById(responsibleId);
        model.addAttribute("responsibleId", responsibleId);
        model.addAttribute("responsibleFullName", r.getFullName());
        model.addAttribute("inventoryNumber", inventoryNumber);
        model.addAttribute("name", e.getName());
        return "renter-take.html";
    }

    @PostMapping("/rent/new")
    public String newRent(
            @RequestParam int responsibleId,
            @RequestParam String inventoryNumber,
            @RequestParam int buildingNumber,
            @RequestParam int audienceNumber,
            Model model) {
        int audienceId = audienceRepository.getIdByNumbers(buildingNumber, audienceNumber);
        Responsible r = responsibleRepository.getById(responsibleId);
        Equipment e = equipmentRepository.getByInventoryNumber(inventoryNumber);
        if (audienceId < 1) {
            model.addAttribute("responsibleId", responsibleId);
            model.addAttribute("responsibleFullName", r.getFullName());
            model.addAttribute("inventoryNumber", inventoryNumber);
            model.addAttribute("name", e.getName());
            model.addAttribute("message", "Такой аудитории не существует!");
            return "renter-take.html";
        }
        Audience a = audienceRepository.getById(audienceId);
        Rent rent = new Rent();
        rent.setResponsible(r);
        rent.setAudience(a);
        rent.setEquipment(e);
        rent.setStartRentDateTime(LocalDateTime.now());
        rentRepository.addRent(rent);
        return "redirect:/rented?responsibleId=" + r.getId();
    }

    @GetMapping("/rented")
    public String rentedGet(@RequestParam int responsibleId, Model model) {
        List<Rent> rents = rentRepository.getAllByResponsibleId(responsibleId);
        Responsible r = responsibleRepository.getById(responsibleId);
        model.addAttribute("responsibleFullName", r.getFullName());
        model.addAttribute("responsibleId", responsibleId);
        model.addAttribute("rents", rents);
        return "renter-now.html";
    }

    @PostMapping("/rented")
    public String returnEquipment(@RequestParam int rentId, @RequestParam int responsibleId) {
        rentRepository.returnById(rentId);
        return "redirect:/rented?responsibleId=" + responsibleId;
    }

    @GetMapping("/rented-now")
    public String rentedNowGet(Model model) {
        List<Rent> rents = rentRepository.getRentedNow();
        model.addAttribute("rents", rents);
        return "equipment-rented-now.html";
    }

    @GetMapping("/rent/info")
    public String getRentInfo(@RequestParam int rentId, Model model) {
        Rent rent = rentRepository.getById(rentId);
        model.addAttribute("rentId", rentId);
        model.addAttribute("equipmentName", rent.getEquipment().getName());
        model.addAttribute("equipmentId", rent.getEquipment().getId());
        model.addAttribute("responsibleId", rent.getResponsible().getId());
        model.addAttribute("responsibleFullName", rent.getResponsible().getFullName());
        model.addAttribute("startRentDateTime", rent.getStartRentDateTime());
        model.addAttribute("endRentDateTime", rent.getEndRentDateTime());
        model.addAttribute("buildingNumber", rent.getAudience().getBuildingNumber());
        model.addAttribute("audienceNumber", rent.getAudience().getAudienceNumber());
        return "rent-info.html";
    }
}
