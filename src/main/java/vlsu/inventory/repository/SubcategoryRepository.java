package vlsu.inventory.repository;

import org.springframework.stereotype.Repository;
import vlsu.inventory.model.Category;
import vlsu.inventory.model.Subcategory;
import vlsu.inventory.service.ConnectionService;
import vlsu.inventory.utility.RequestUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubcategoryRepository {
    private final ConnectionService connectionService;
    private final CategoryRepository categoryRepository;

    public SubcategoryRepository(ConnectionService connectionService, CategoryRepository categoryRepository) {
        this.connectionService = connectionService;
        this.categoryRepository = categoryRepository;
    }

    public Subcategory getById(int id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM subcategory WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            Subcategory subcategory = new Subcategory();
            while (set.next()) {
                subcategory.setId(set.getInt("id"));
                subcategory.setName(set.getString("name"));

                Category category = categoryRepository.getById(set.getInt("category_id"));
                subcategory.setCategory(category);
            }
            return subcategory;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<Subcategory> getAll() {
        try (Connection connection = connectionService.getConnection()) {
            ResultSet set = RequestUtility.SelectQuery(connection, "SELECT * FROM subcategory");
            List<Subcategory> subcategories = new ArrayList<>();
            while (set.next()) {
                Subcategory subcategory = new Subcategory();
                subcategory.setId(set.getInt("id"));
                subcategory.setName(set.getString("name"));
                subcategory.setCategory(categoryRepository.getById(set.getInt("category_id")));
                subcategories.add(subcategory);
            }
            return subcategories;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<Subcategory> getAllByCategory(Category category) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM subcategory WHERE category_id = ?"
            );
            statement.setInt(1, category.getId());
            ResultSet set = statement.executeQuery();
            List<Subcategory> subcategories = new ArrayList<>();
            while (set.next()) {
                Subcategory subcategory = new Subcategory();
                subcategory.setId(set.getInt("id"));
                subcategory.setName(set.getString("name"));
                subcategory.setCategory(categoryRepository.getById(set.getInt("category_id")));
                subcategories.add(subcategory);
            }
            return subcategories;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
