package chatbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("pizzaservice")
public class PizzaServiceImpl {

	@Autowired
	PizzaMapper mapper;
	
	public void insertPizza(PizzaDTO dto) {
		mapper.insertPizza(dto);
	}
}
