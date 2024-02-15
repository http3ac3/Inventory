package vlsu.inventory.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vlsu.inventory.model.Equipment;
import vlsu.inventory.repository.EquipmentRepository;

@Controller
public class SearchController {
    private final EquipmentRepository equipmentRepository;

    public SearchController(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping("/search")
    public String searchGet() {
        return "search.html";
    }

    @PostMapping("/search")
    public String search(
            @RequestParam String number,
            Model model) {
        Equipment equipment = equipmentRepository.getByInventoryNumber(number);
        if (equipment.getId() < 1) {
            model.addAttribute("message", "Оборудование не найдено");
        }
        else {
            model.addAttribute("message", equipment.getName());
            model.addAttribute("equipmentId", equipment.getId());
        }
        return "search.html";

    }
}
