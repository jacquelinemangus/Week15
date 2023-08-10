package pet.store;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
public class PetStoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(PetStoreApplication.class, args);

	}

}

