package tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import tacos.model.Ingredient;

@Repository
public class IngredientRepositoryImpl implements IngredientRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	public IngredientRepositoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate=jdbcTemplate;
	}

	@Override
	public Iterable<Ingredient> findAll() {
		return jdbcTemplate.query(
				 "select id,ingredientId, name, type from Ingredient",
				 this::mapRowToIngredient);
	}

	@Override
	public Optional<Ingredient> findById(String id) {
		List<Ingredient> results = jdbcTemplate.query(
				 "select id,ingredientId, name, type from Ingredient where ingredientId=?",
				 this::mapRowToIngredient,
				 id);
		return results.size()==0?Optional.empty():Optional.of(results.get(0));
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbcTemplate.update(
				 "insert into Ingredient (ingredientId, name, type) values (?, ?, ?)",
				 ingredient.getIngredientId(),
				 ingredient.getName(),
				 ingredient.getType().toString());
		return ingredient;
	}
	
	private Ingredient mapRowToIngredient(ResultSet row,int rowNum) throws SQLException{
		return new Ingredient(
				row.getLong("id"),
				row.getString("ingredientId"), 
				row.getString("name"), 
				Ingredient.Type.valueOf(row.getString("type")));
	}

}
