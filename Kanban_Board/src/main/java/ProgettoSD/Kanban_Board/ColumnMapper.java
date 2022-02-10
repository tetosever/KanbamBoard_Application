package ProgettoSD.Kanban_Board;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ColumnMapper implements RowMapper<Colonna>{

	@Override
	public Colonna mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Colonna col = new Colonna();
        col.setTitle(rs.getString("title"));
        col.setState(rs.getString("state"));
        
		return col;
	}
	
}
