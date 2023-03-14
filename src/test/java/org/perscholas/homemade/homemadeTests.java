package org.perscholas.homemade;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.perscholas.homemade.dao.ChefRepoI;
import org.perscholas.homemade.dao.ProductRepoI;
import org.perscholas.homemade.models.Chef;
import org.perscholas.homemade.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class homemadeTests {
	@Autowired
	ProductRepoI productRepoI;
	@Autowired
	ChefRepoI chefRepoI;

	static List<Product> expected() {
		Product p1=new Product("Pasta","Italian",10.00,LocalDate.of(2023,04,23));
		p1.setId(1);
		Product p2=new Product("Chicken Burger","American",20.00, LocalDate.of(2023,04,23));
		p2.setId(2);
		Product p3=new Product("Chicken Sandwich","American",15.00,LocalDate.of(2023,04,26));
		p3.setId(3);
		Product p4=new Product("Samosa","Indian",10.00,LocalDate.of(2023,04,23));
		p4.setId(4);
		Product p5=new Product("Salad","American",10.00,LocalDate.of(2023,04,27));
		p5.setId(5);

		List<Product> P = new ArrayList<>();
		P.add(p1);
		P.add(p2);
		P.add(p3);
		P.add(p4);
		P.add(p5);

		return P;

	}

	static List<Chef> chefExpected(){
		Chef chef = new Chef("James", "james@gmail.com", "password", "555-555-5555", "434 Oak Ave", "Troy", "Michigan", "12345");
		Chef c1 = new Chef("John Smith","johnsmith@example.com", "Password","555-555-5555","123 Main St","Dallas","Texas","45678");
		Chef c2 = new Chef("Sarah Johnson", "sjohnson@example.com","Password","555-123-4567","456 Oak Ave","Troy", "Michigan","23467");
		Chef c3 = new Chef(" Michael Chen","mchen@example.com","Password","555-867-5309","789 Elm St","Dallas", "Texas","46788");
		Chef c4 = new Chef("Emily Lee","elee@example.com","Password","555-444-5555","321 Maple Rd","Troy", "Michigan","24677");

		chef.setId(1);
		c1.setId(2);
		c2.setId(3);
		c3.setId(4);
		c4.setId(5);

		List<Chef> C = new ArrayList<>();
		C.add(chef);
		C.add(c1);
		C.add(c2);
		C.add(c3);
		C.add(c4);
		return C;
	}

	@Test
	void findAllTest(){
 		assertEquals(expected(),productRepoI.findAll());
	}

	@ParameterizedTest
	@ValueSource(ints={1,2,3,4,5})
	void findByIDTest(int id){
		List<Product> P = expected();
		assertEquals(P.get(id-1),productRepoI.findById(id));
	}
	@ParameterizedTest
	@ValueSource(ints={1,2,3,4,5})
	void chefFindByIDTest(int id){
		List<Chef> C = chefExpected();
		assertEquals(C.get(id-1).hashCode(),chefRepoI.findById(id).get().hashCode());
	}

}
