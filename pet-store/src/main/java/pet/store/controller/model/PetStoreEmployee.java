package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

@Data
@NoArgsConstructor 
public class PetStoreEmployee {
	private Long employeeId;
	private String employeeFirstname;
	private String employeeLastname;
	private String employeePhone;
	private String employeeJobTitle;
	
	public PetStoreEmployee (Employee employee) {
		employeeId = employee.getEmployeeId();
		employeeFirstname = employee.getEmployeeFirstname();
		employeeLastname = employee.getEmployeeLastname();
		employeePhone = employee.getEmployeePhone();
		employeeJobTitle = employee.getEmployeeJobTitle();
		
		} 
}
