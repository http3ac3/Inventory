package vlsu.inventory.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vlsu.inventory.model.Responsible;
import vlsu.inventory.repository.ResponsibleRepository;

@Controller
public class ResponsibleContoller {
    private final ResponsibleRepository responsibleRepository;

    public ResponsibleContoller(ResponsibleRepository responsibleRepository) {
        this.responsibleRepository = responsibleRepository;
    }

    @GetMapping("/responsible/info")
    public String getResponsibleInfo(@RequestParam int id, Model model) {
        Responsible responsible = responsibleRepository.getById(id);
        model.addAttribute("lastName", responsible.getLastName());
        model.addAttribute("firstName", responsible.getFirstName());
        model.addAttribute("patronymic", responsible.getPatronymic());
        model.addAttribute("phoneNumber", responsible.getPhoneNumber());
        model.addAttribute("position", responsible.getPosition());
        return "responsible-info.html";
    }
}
