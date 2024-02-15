package vlsu.inventory.model;

public class Responsible {
    private int id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private String phoneNumber;
    private String position;

    private String fullName;
    public String getFullName() {return fullName;}

    public void setFullName() {
        this.fullName =  lastName + " " + firstName.charAt(0) + ". " + patronymic.charAt(0) + ".";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
