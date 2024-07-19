package tacos;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tacos.data.IngredientRepository;
import tacos.model.Ingredient;
import tacos.model.Ingredient.Type;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}
	
	@Bean
	public ApplicationRunner dataLoader(IngredientRepository repo) {
	 return args -> {
	 repo.save(new Ingredient(1l,"FLTO", "Flour Tortilla", Type.WRAP));
	 repo.save(new Ingredient(2l,"COTO", "Corn Tortilla", Type.WRAP));
	 repo.save(new Ingredient(3l,"GRBF", "Ground Beef", Type.PROTEIN));
	 repo.save(new Ingredient(4l,"CARN", "Carnitas", Type.PROTEIN));
	 repo.save(new Ingredient(5l,"TMTO", "Diced Tomatoes", Type.VEGGIES));
	 repo.save(new Ingredient(6l,"LETC", "Lettuce", Type.VEGGIES));
	 repo.save(new Ingredient(7l,"CHED", "Cheddar", Type.CHEESE));
	 repo.save(new Ingredient(8l,"JACK", "Monterrey Jack", Type.CHEESE));
	 repo.save(new Ingredient(9l,"SLSA", "Salsa", Type.SAUCE));
	 repo.save(new Ingredient(10l,"SRCR", "Sour Cream", Type.SAUCE));
	 };

}}
