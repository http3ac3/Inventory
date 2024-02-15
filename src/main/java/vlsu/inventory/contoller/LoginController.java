package vlsu.inventory.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import vlsu.inventory.model.Responsible;
import vlsu.inventory.repository.ResponsibleRepository;
import vlsu.inventory.service.LoginService;

@Controller
public class LoginController {
    private final LoginService loginService;
    private final ResponsibleRepository responsibleRepository;

    public LoginController(LoginService loginService, ResponsibleRepository responsibleRepository) {
        this.loginService = loginService;
        this.responsibleRepository = responsibleRepository;
    }

    @GetMapping("/")
    public String getLogin() {
        return "login.html";
    }

    @PostMapping("/")
    public String login(String username, String password, Model model) {
        String role = loginService.auth(username, password);
        if (role == null) {
            model.addAttribute("message", "Имя пользователя и/или пароль неверны!\n" +
                    "Попробуйте еще раз!");
            return "login.html";
        }

        if (role.equals("`renter`@`%`")) {
            Responsible responsible = responsibleRepository.getByLogin(username, password);
            model.addAttribute("responsibleFullName", responsible.getFullName());
            model.addAttribute("responsibleId", responsible.getId());
            return "renter-main.html";
        }
        else {
            return "redirect:/main";
        }
    }
}
