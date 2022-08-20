package ProgettoSD.Kanban_Board;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@SpringBootApplication
public class KanbanBoardApplication {
    
	public static void main(String[] args) {
		
		SpringApplication.run(KanbanBoardApplication.class, args);
		System.out.println("Hello World");
		
	}

	
	
}
