package vlsu.inventory.repository;

import org.springframework.stereotype.Repository;
import vlsu.inventory.model.Audience;
import vlsu.inventory.model.Equipment;
import vlsu.inventory.model.Responsible;
import vlsu.inventory.model.Subcategory;
import vlsu.inventory.service.ConnectionService;
import vlsu.inventory.utility.RequestUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EquipmentRepository {
    private final ConnectionService connectionService;
    private final ResponsibleRepository responsibleRepository;
    private final AudienceRepository audienceRepository;
    private final SubcategoryRepository subcategoryRepository;

    public EquipmentRepository(ConnectionService connectionService, ResponsibleRepository responsibleRepository, AudienceRepository audienceRepository, SubcategoryRepository subcategoryRepository) {
        this.connectionService = connectionService;
        this.responsibleRepository = responsibleRepository;
        this.audienceRepository = audienceRepository;
        this.subcategoryRepository = subcategoryRepository;
    }
    public List<Equipment> getAll() {
        try (Connection connection = connectionService.getConnection()){
            ResultSet set = RequestUtility.SelectQuery(connection, "SELECT * FROM equipment;");
            List<Equipment> equipmentList = new ArrayList<>();
            while (set.next()) {
                Equipment equipment = new Equipment();
                equipment.setId(set.getInt("id"));
                equipment.setInventoryNumber(set.getString("inventory_number"));
                equipment.setName(set.getString("name"));
                equipment.setWearRate(set.getBigDecimal("wear_rate"));
                equipment.setCommissioningDate(set.getObject("commissioning_date", LocalDate.class));
                equipment.setCommissioningActNumber(set.getInt("commissioning_act_number"));
                equipment.setInitialCost(set.getBigDecimal("initial_cost"));
                equipment.setGeneralWear(set.getBigDecimal("general_wear"));
                equipment.setResidualCost(set.getBigDecimal("residual_cost"));
                equipment.setDecommissioningDate(set.getObject("decommissioning_date", LocalDate.class));
                equipment.setDecommissioningActNumber(set.getInt("decommissioning_act_number"));
                equipment.setDescription(set.getString("description"));

                Responsible responsible = responsibleRepository.getById(set.getInt("responsible_id"));
                responsible.setFullName();
                equipment.setResponsible(responsible);

                Audience audience = audienceRepository.getById(set.getInt("audience_id"));
                audience.setFullAudience();
                equipment.setAudience(audience);

                Subcategory subcategory = subcategoryRepository.getById(set.getInt("subcategory_id"));
                equipment.setSubcategory(subcategory);

                equipmentList.add(equipment);
            }
            return equipmentList;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public Equipment getById(int id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM equipment WHERE id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();

            Equipment equipment = new Equipment();
            while (set.next()) {
                equipment.setId(set.getInt("id"));
                equipment.setInventoryNumber(set.getString("inventory_number"));
                equipment.setName(set.getString("name"));
                equipment.setWearRate(set.getBigDecimal("wear_rate"));
                equipment.setCommissioningDate(set.getObject("commissioning_date", LocalDate.class));
                equipment.setCommissioningActNumber(set.getInt("commissioning_act_number"));
                equipment.setInitialCost(set.getBigDecimal("initial_cost"));
                equipment.setGeneralWear(set.getBigDecimal("general_wear"));
                equipment.setResidualCost(set.getBigDecimal("residual_cost"));
                equipment.setDecommissioningDate(set.getObject("decommissioning_date", LocalDate.class));
                equipment.setDecommissioningActNumber(set.getInt("decommissioning_act_number"));
                equipment.setDescription(set.getString("description"));

                Responsible responsible = responsibleRepository.getById(set.getInt("responsible_id"));
                responsible.setFullName();
                equipment.setResponsible(responsible);

                Audience audience = audienceRepository.getById(set.getInt("audience_id"));
                audience.setFullAudience();
                equipment.setAudience(audience);

                Subcategory subcategory = subcategoryRepository.getById(set.getInt("subcategory_id"));
                equipment.setSubcategory(subcategory);
            }
            return equipment;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void addEquipment(Equipment e) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO equipment VALUE (NULL, ?, ?, ?, ?, ?, ?, 0, ?, NULL, NULL, ?, ?, ?, ?)"
            );
            statement.setString(1, e.getInventoryNumber());
            statement.setString(2, e.getName());
            statement.setBigDecimal(3, e.getWearRate());
            statement.setObject(4, e.getCommissioningDate());
            statement.setInt(5, e.getCommissioningActNumber());
            statement.setBigDecimal(6, e.getInitialCost());
            statement.setBigDecimal(7, e.getInitialCost());
            statement.setString(8, e.getDescription());
            statement.setInt(9, e.getResponsible().getId());
            statement.setInt(10, e.getAudience().getId());
            statement.setInt(11, e.getSubcategory().getId());
            statement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void deleteById(int id) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM equipment WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void editByInventoryNumber(Equipment e) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE equipment SET " +
                            "inventory_number = ?, " +
                            "name = ?, " +
                            "wear_rate = ?, " +
                            "commissioning_date = ?, " +
                            "commissioning_act_number = ?, " +
                            "initial_cost = ?, " +
                            "general_wear = ?, " +
                            "residual_cost = ?, " +
                            "decommissioning_date = ?, " +
                            "decommissioning_act_number = ?, " +
                            "description = ?, " +
                            "responsible_id = ?, " +
                            "audience_id = ?, " +
                            "subcategory_id = ? WHERE id = ?"
            );
            statement.setString(1, e.getInventoryNumber());
            statement.setString(2, e.getName());
            statement.setBigDecimal(3, e.getWearRate());
            statement.setObject(4, e.getCommissioningDate());
            statement.setInt(5, e.getCommissioningActNumber());
            statement.setBigDecimal(6, e.getInitialCost());
            statement.setBigDecimal(7, e.getGeneralWear());
            statement.setBigDecimal(8, e.getResidualCost());
            statement.setObject(9, e.getDecommissioningDate());
            statement.setObject(10, e.getDecommissioningActNumber());
            statement.setString(11, e.getDescription());
            statement.setInt(12, e.getResponsible().getId());
            statement.setInt(13, e.getAudience().getId());
            statement.setInt(14, e.getSubcategory().getId());
            statement.setInt(15, e.getId());
            System.out.println(statement);
            statement.executeUpdate();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Equipment getByInventoryNumber(String inventoryNumber) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM equipment " +
                    "WHERE inventory_number = ?");
            statement.setString(1, inventoryNumber);
            ResultSet set = statement.executeQuery();
            Equipment equipment = new Equipment();
            while (set.next()) {
                equipment.setId(set.getInt("id"));
                equipment.setInventoryNumber(set.getString("inventory_number"));
                equipment.setName(set.getString("name"));
                equipment.setWearRate(set.getBigDecimal("wear_rate"));
                equipment.setCommissioningDate(set.getObject("commissioning_date", LocalDate.class));
                equipment.setCommissioningActNumber(set.getInt("commissioning_act_number"));
                equipment.setInitialCost(set.getBigDecimal("initial_cost"));
                equipment.setGeneralWear(set.getBigDecimal("general_wear"));
                equipment.setResidualCost(set.getBigDecimal("residual_cost"));
                equipment.setDecommissioningDate(set.getObject("decommissioning_date", LocalDate.class));
                equipment.setDecommissioningActNumber(set.getInt("decommissioning_act_number"));
                equipment.setDescription(set.getString("description"));

                Responsible responsible = responsibleRepository.getById(set.getInt("responsible_id"));
                responsible.setFullName();
                equipment.setResponsible(responsible);

                Audience audience = audienceRepository.getById(set.getInt("audience_id"));
                audience.setFullAudience();
                equipment.setAudience(audience);

                Subcategory subcategory = subcategoryRepository.getById(set.getInt("subcategory_id"));
                equipment.setSubcategory(subcategory);
            }
            return equipment;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public String getNameByInventoryNumber(String inventoryNumber) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM equipment " +
                    "WHERE inventory_number = ?");
            statement.setString(1, inventoryNumber);
            ResultSet set = statement.executeQuery();
            String name = "";
            while (set.next()) {
                name = set.getString("name");
            }
            return name;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean isRented(Equipment e) {
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT count(1) FROM equipment e " +
                            "JOIN rent r ON r.equipment_id = ? " +
                            "WHERE r.end_rent_datetime IS NULL"
            );
            statement.setInt(1, e.getId());
            ResultSet set = statement.executeQuery();
            int count = 0;
            while (set.next()) {
                count = set.getInt("count(1)");
            }
            if (count != 0) {
                return true;
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public List<Equipment> getAllBySubcategory(int id) {
        try (Connection connection = connectionService.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM equipment WHERE subcategory_id = ?");
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            List<Equipment> equipmentList = new ArrayList<>();
            while (set.next()) {
                Equipment equipment = new Equipment();
                equipment.setId(set.getInt("id"));
                equipment.setInventoryNumber(set.getString("inventory_number"));
                equipment.setName(set.getString("name"));
                equipment.setWearRate(set.getBigDecimal("wear_rate"));
                equipment.setCommissioningDate(set.getObject("commissioning_date", LocalDate.class));
                equipment.setCommissioningActNumber(set.getInt("commissioning_act_number"));
                equipment.setInitialCost(set.getBigDecimal("initial_cost"));
                equipment.setGeneralWear(set.getBigDecimal("general_wear"));
                equipment.setResidualCost(set.getBigDecimal("residual_cost"));
                equipment.setDecommissioningDate(set.getObject("decommissioning_date", LocalDate.class));
                equipment.setDecommissioningActNumber(set.getInt("decommissioning_act_number"));
                equipment.setDescription(set.getString("description"));

                Responsible responsible = responsibleRepository.getById(set.getInt("responsible_id"));
                responsible.setFullName();
                equipment.setResponsible(responsible);

                Audience audience = audienceRepository.getById(set.getInt("audience_id"));
                audience.setFullAudience();
                equipment.setAudience(audience);

                Subcategory subcategory = subcategoryRepository.getById(set.getInt("subcategory_id"));
                equipment.setSubcategory(subcategory);

                equipmentList.add(equipment);
            }
            return equipmentList;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

}
