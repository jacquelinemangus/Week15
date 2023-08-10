package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;

@Data
@NoArgsConstructor 
public class PetStoreCustomer {
	private Long customerId;
	private String customerFirstname;
	private String customerLastname;
	private String customerEmail;

	
	public PetStoreCustomer (Customer customer) {
		customerId = customer.getCustomerId();
		customerFirstname = customer.getCustomerFirstname();
		customerLastname = customer.getCustomerLastname();
		customerEmail = customer.getCustomerEmail();
	
		
	}
}


