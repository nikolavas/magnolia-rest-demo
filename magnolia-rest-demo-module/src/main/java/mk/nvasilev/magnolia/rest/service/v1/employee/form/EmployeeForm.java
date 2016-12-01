package mk.nvasilev.magnolia.rest.service.v1.employee.form;

import org.hibernate.validator.constraints.NotEmpty;
import javax.ws.rs.FormParam;

/**
 * EmployeeForm
 */
public class EmployeeForm {

    @FormParam("firstName")
    @NotEmpty(message = "First name should not be empty.")
    private String firstName;

    @FormParam("lastName")
    @NotEmpty(message = "Last name should not be empty.")
    private String lastName;

    @FormParam("gender")
    @NotEmpty(message = "Gender should not be empty.")
    private String gender;

    @FormParam("dateOfBirth")
    @NotEmpty(message = "Date of birth should not be empty.")
    private String dateOfBirth;

    @FormParam("department")
    private String department;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
