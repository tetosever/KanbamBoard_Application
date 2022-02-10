package ProgettoSD.Kanban_Board;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TileMapper implements RowMapper<Tile>{

	@Override
	public Tile mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Tile tile = new Tile();
		
		tile.setID(rs.getInt("ID"));
		tile.setTitle(rs.getString("title"));
		tile.setAuthor(rs.getString("author"));
		tile.setMessageType(rs.getString("messageType"));
		tile.setContent(rs.getString("content"));
		tile.setColumns(rs.getString("columns"));
		
		return tile;
	}

}
