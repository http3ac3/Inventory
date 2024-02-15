package vlsu.inventory.repository;

import org.springframework.stereotype.Repository;
import vlsu.inventory.model.Category;
import vlsu.inventory.service.ConnectionService;
import vlsu.inventory.utility.RequestUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryRepository {
    private final ConnectionService connectionService;

    public CategoryRepository(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }
    public Category getById(int id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM category WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            Category category = new Category();
            while (set.next()) {
                category.setId(set.getInt("id"));
                category.setName(set.getString("name"));
            }
            return category;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<Category> getAll() {
        try (Connection connection = connectionService.getConnection()) {
            ResultSet set = RequestUtility.SelectQuery(connection, "SELECT * FROM category");
            List<Category> categories = new ArrayList<>();
            while (set.next()) {
                Category category = new Category();
                category.setId(set.getInt("id"));
                category.setName(set.getString("name"));
                categories.add(category);
            }
            return categories;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
