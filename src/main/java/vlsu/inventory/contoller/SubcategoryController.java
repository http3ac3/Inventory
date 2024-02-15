package vlsu.inventory.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vlsu.inventory.model.Subcategory;
import vlsu.inventory.repository.CategoryRepository;
import vlsu.inventory.repository.SubcategoryRepository;

import java.util.List;

@Controller
public class SubcategoryController {
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    public SubcategoryController(CategoryRepository categoryRepository, SubcategoryRepository subcategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @GetMapping("/subcategory")
    public String subcategoryGet(@RequestParam int id, Model model) {
        List<Subcategory> subcategories = subcategoryRepository.getAllByCategory(
                categoryRepository.getById(id)
        );
        model.addAttribute("subcategories", subcategories);
        return "subcategories.html";
    }

}
