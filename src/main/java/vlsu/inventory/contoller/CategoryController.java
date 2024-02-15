package vlsu.inventory.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vlsu.inventory.model.Category;
import vlsu.inventory.repository.CategoryRepository;

import java.util.List;

@Controller
public class CategoryController {
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    public String getCategories(Model model) {
        List<Category> categories = categoryRepository.getAll();
        model.addAttribute("categories", categories);
        return "categories.html";
    }
}
