package vlsu.inventory.repository;

import org.springframework.stereotype.Repository;
import vlsu.inventory.model.Audience;
import vlsu.inventory.model.Equipment;
import vlsu.inventory.model.Rent;
import vlsu.inventory.model.Responsible;
import vlsu.inventory.service.ConnectionService;
import vlsu.inventory.utility.RequestUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RentRepository {
    private final ConnectionService connectionService;
    private final ResponsibleRepository responsibleRepository;
    private final AudienceRepository audienceRepository;
    private final EquipmentRepository equipmentRepository;
    public RentRepository(ConnectionService connectionService, ResponsibleRepository responsibleRepository, AudienceRepository audienceRepository, EquipmentRepository equipmentRepository) {
        this.connectionService = connectionService;
        this.responsibleRepository = responsibleRepository;
        this.audienceRepository = audienceRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public Rent getById(int id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rent WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            Rent rent = new Rent();
            while (set.next()) {
                rent.setId(set.getInt("id"));

                Responsible responsible = responsibleRepository.getById(
                        set.getInt("responsible_id"));
                rent.setResponsible(responsible);

                Audience audience = audienceRepository.getById(
                        set.getInt("audience_id")
                );
                rent.setAudience(audience);

                Equipment equipment = equipmentRepository.getById(
                        set.getInt("equipment_id")
                );
                rent.setEquipment(equipment);

                rent.setStartRentDateTime(
                        set.getObject("start_rent_datetime", LocalDateTime.class));

                rent.setEndRentDateTime(
                        set.getObject("end_rent_datetime", LocalDateTime.class)
                );
            }
            return rent;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void addRent(Rent rent) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO rent VALUE (NULL, ?, ?, ?, ?, NULL)");
            statement.setInt(1, rent.getResponsible().getId());
            statement.setInt(2, rent.getEquipment().getId());
            statement.setInt(3, rent.getAudience().getId());
            statement.setObject(4, LocalDateTime.now());
            statement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Rent> getAllByResponsibleId(int id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM rent " +
                    "WHERE responsible_id = ? AND end_rent_datetime IS NULL");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            List<Rent> rents = new ArrayList<>();
            while (set.next()) {
                Rent rent = new Rent();
                rent.setId(set.getInt("id"));

                Responsible responsible = responsibleRepository.getById(
                        set.getInt("responsible_id"));
                rent.setResponsible(responsible);

                Audience audience = audienceRepository.getById(
                        set.getInt("audience_id")
                );
                rent.setAudience(audience);

                Equipment equipment = equipmentRepository.getById(
                        set.getInt("equipment_id")
                );
                rent.setEquipment(equipment);

                rent.setStartRentDateTime(
                        set.getObject("start_rent_datetime", LocalDateTime.class));

                rents.add(rent);
            }
            return rents;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void returnById(int id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE rent SET end_rent_datetime = ? WHERE id = ?"
            );
            statement.setString(1, LocalDateTime.now()
                    .toString().replace("T", " "));
            statement.setInt(2, id);
            statement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Rent> getRentHistory(Equipment e) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM rent r " +
                            "WHERE r.equipment_id = ?"
            );
            statement.setInt(1, e.getId());
            ResultSet set = statement.executeQuery();
            List<Rent> rents = new ArrayList<>();
            while (set.next()) {
                Rent rent = new Rent();
                rent.setId(set.getInt("id"));

                Responsible responsible = responsibleRepository.getById(
                        set.getInt("responsible_id"));
                rent.setResponsible(responsible);

                Audience audience = audienceRepository.getById(
                        set.getInt("audience_id")
                );
                rent.setAudience(audience);

                Equipment equipment = equipmentRepository.getById(
                        set.getInt("equipment_id")
                );
                rent.setEquipment(equipment);

                rent.setStartRentDateTime(
                        set.getObject("start_rent_datetime", LocalDateTime.class));

                rent.setEndRentDateTime(
                        set.getObject("end_rent_datetime", LocalDateTime.class));
                rents.add(rent);
            }
            return rents;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public List<Rent> getRentedNow() {
        try (Connection connection = connectionService.getConnection()) {
            ResultSet set = RequestUtility.SelectQuery(connection, "SELECT * FROM rent WHERE end_rent_datetime IS NULL");
            List<Rent> rents = new ArrayList<>();
            while (set.next()) {
                Rent rent = new Rent();
                rent.setId(set.getInt("id"));

                Responsible responsible = responsibleRepository.getById(
                        set.getInt("responsible_id"));
                rent.setResponsible(responsible);

                Audience audience = audienceRepository.getById(
                        set.getInt("audience_id")
                );
                rent.setAudience(audience);

                Equipment equipment = equipmentRepository.getById(
                        set.getInt("equipment_id")
                );
                rent.setEquipment(equipment);

                rent.setStartRentDateTime(
                        set.getObject("start_rent_datetime", LocalDateTime.class));

                rent.setEndRentDateTime(
                        set.getObject("end_rent_datetime", LocalDateTime.class));
                rents.add(rent);
            }
            return rents;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
