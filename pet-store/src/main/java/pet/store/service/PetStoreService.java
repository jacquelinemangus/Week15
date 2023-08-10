package pet.store.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.LinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
	
	
	 

	@Autowired
	private PetStoreDao petStoreDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private CustomerDao customerDao;

	private PetStore findOrCreatePetStore(Long petStoreId) {
		

		if (Objects.isNull(petStoreId)) {
			return new PetStore();
		} else {
			return findPetStoreById(petStoreId);
		}
	}
	private Customer findCustomerById(Long petStoreId, Long customerId) {
            Customer customer = customerDao.findById(customerId)
            		.orElseThrow(() -> new NoSuchElementException("customer Id does not exist"));
            
            boolean found = false;
            for (PetStore petstore : customer.getPetStores()) {
            	if  (petstore.getPetStoreId()==petStoreId) {
            		found = true; 
            		break;
            		
            	}
            	
            		
            		
            }
          if (!found)  {
        	  throw new IllegalArgumentException("this customer is not a memeber of this pet store");
          }
          return customer;
	}
	
	private PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + " does not exist."));
	}
	

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId  = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		copyPetStoreFields(petStore, petStoreData);
		return new PetStoreData(petStoreDao.save(petStore));
		
	}
	 @Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
		Long employeeId = petStoreEmployee.getEmployeeId();
		PetStore petStore = findPetStoreById(petStoreId);
		Employee employee = findOrCreateEmployee(petStoreId, employeeId);	
		 copyEmployeeFields(employee, petStoreEmployee);
		 
		 employee.setPetStore(petStore);
		 petStore.getEmployees().add(employee);
	     
		
	      Employee dbEmployee = employeeDao.save(employee);
	           return new PetStoreEmployee(dbEmployee);
	 }

		private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
			employee.setEmployeeFirstname(petStoreEmployee.getEmployeeFirstname());
			employee.setEmployeeLastname(petStoreEmployee.getEmployeeLastname());
			employee.setEmployeeId(petStoreEmployee.getEmployeeId());
			employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
			employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
		}
		
	

	private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
			if(Objects.isNull(employeeId)) {
				return new Employee();
		}
			else {
				return findEmployeeById(petStoreId, employeeId);
			}
	}
	 

	 Employee findEmployeeById(Long petStoreId, Long employeeId) {
		Employee employee = employeeDao
				.findById(employeeId).orElseThrow(() -> new NoSuchElementException());
		
		if (employee.getPetStore().getPetStoreId() != petStoreId) {
			throw new IllegalArgumentException("this employee is not a memeber of this pet store");
		}
		return employee;
		
	}


	
	
	
	
	
		
	
	 void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		 petStore.setPetStoreId(petStoreData.getPetStoreId());
		 petStore.setPetStoreName(petStoreData.getPetStoreName());
		  petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
		 petStore.setPetStoreCity(petStoreData.getPetStoreCity());
		  petStore.setPetStoreState(petStoreData.getPetStoreState());
		 petStore.setPetStoreZip(petStoreData.getPetStoreZip());
		 petStore.setPetStorePhone(petStoreData.getPetStorePhone());

		
		
	}

	
	@Transactional(readOnly = false)
	public void deletePetStoreById(Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}
	
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petstore = petStoreDao.findAll();
		List<PetStoreData> result = new LinkedList<>();
		
		for(PetStore petStore : petstore) {
			PetStoreData psd = new PetStoreData(petStore);
					
					psd.getCustomers().clear();
			        psd.getEmployees().clear();
			        
			        result.add(psd); 
			        
		}
		return result;
	}
	@Transactional
	public PetStoreData retrievePetStoreById(Long petStoreId) {
		PetStore petstore = findPetStoreById(petStoreId);
		return new PetStoreData(petstore);
		
	}
	 }

	 



