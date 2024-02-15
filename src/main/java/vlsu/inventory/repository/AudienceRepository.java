package vlsu.inventory.repository;

import org.springframework.stereotype.Repository;
import vlsu.inventory.model.Audience;
import vlsu.inventory.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class AudienceRepository {
    private final ConnectionService connectionService;
    public AudienceRepository(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }
    public Audience getById(int id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM audience WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            Audience audience = new Audience();
            while (set.next()) {
                audience.setId(set.getInt("id"));
                audience.setBuildingNumber(set.getInt("building_number"));
                audience.setAudienceNumber(set.getInt("audience"));
                audience.setFullAudience();
            }
            return audience;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public int getIdByNumbers(int buildingNumber, int audienceNumber) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM audience " +
                    "WHERE building_number = ? AND audience = ?");
            statement.setInt(1, buildingNumber);
            statement.setInt(2, audienceNumber);
            ResultSet set = statement.executeQuery();
            int id = -1;
            while (set.next()) {
                id = set.getInt("id");
            }
            return id;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }

    public void addAudience(int buildingNumber, int audienceNumber) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO audience VALUE " +
                    "(NULL, ?, ?)");
            statement.setInt(1, buildingNumber);
            statement.setInt(2, audienceNumber);
            statement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
