package chatbot;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper		//매퍼 파일이야, @MapperScan
@Repository //객체 생성, @ComponentScan
public interface PizzaMapper {
	void insertPizza(PizzaDTO dto);
}
