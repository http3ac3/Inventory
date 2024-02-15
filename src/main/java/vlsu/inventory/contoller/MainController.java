package vlsu.inventory.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vlsu.inventory.model.Equipment;
import vlsu.inventory.repository.EquipmentRepository;
import vlsu.inventory.service.ConnectionService;

import java.util.List;

@Controller
public class MainController {
    private final ConnectionService connectionService;
    private final EquipmentRepository equipmentRepository;

    public MainController(ConnectionService connectionService, EquipmentRepository equipmentRepository) {
        this.connectionService = connectionService;
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping("/main")
    public String getMain(@RequestParam(required = false) Integer subcategoryId, Model model) {
        List<Equipment> equipmentList;
        if (subcategoryId != null) {
            equipmentList = equipmentRepository.getAllBySubcategory(subcategoryId);
        }
        else {
            equipmentList = equipmentRepository.getAll();
        }
        model.addAttribute("equipment", equipmentList);
        return "main_page.html";
    }


}
