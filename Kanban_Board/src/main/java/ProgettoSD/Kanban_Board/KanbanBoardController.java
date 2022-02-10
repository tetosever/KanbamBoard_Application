package ProgettoSD.Kanban_Board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.DataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KanbanBoardController {
	
		private static final String driverClassName = "com.mysql.cj.jdbc.Driver";
		private static final String url = "jdbc:mysql://localhost:3306/ProgettoSD";
		private static final String dbUsername = ""; //necessario inserire lo stesso username delle application.properties
		private static final String dbPassword = ""; //necessario inserire la stessa password delle application.properties
    
		private static DataSource dataSource = getDataSource();
		JdbcTemplate template = new JdbcTemplate(dataSource);
		
		/*
		 * questo metodo lo uso per settare il database workbench
		 */
		public static DriverManagerDataSource getDataSource() {
			 
			  DriverManagerDataSource dataSource = new DriverManagerDataSource();
			 
			  dataSource.setDriverClassName(driverClassName);
			 
			  dataSource.setUrl(url);
			 
			  dataSource.setUsername(dbUsername);
			 
			  dataSource.setPassword(dbPassword);
			 
			  return dataSource;
			}
		
		/*
		 * Dentro a questo metodo devo far si che venga letta e visualizzata la pagina html della board
		 */
		@RequestMapping(value="/board/", method=RequestMethod.GET)
		public String getBoardPage() {
			
			String content = "";
		    try {
		        BufferedReader in = new BufferedReader(new FileReader("target/classes/Board.html"));
		        String str;
		        while ((str = in.readLine()) != null) {
		            content +=str;
		        }
		        in.close();
		    } catch (IOException e) {
		    	return "Failed";
		    }
		    
		    return content;
		}
		
		/*
		 * Prendo la pagina html contente il form per aggiungere le colonne 
		 */
		@RequestMapping(value="/board/createColumn/", method=RequestMethod.GET)
		public String AddColumnFormPage() {
			
			String content = "";
		    try {
		        BufferedReader in = new BufferedReader(new FileReader("target/classes/addColumnPage.html"));
		        String str;
		        while ((str = in.readLine()) != null) {
		            content +=str;
		        }
		        in.close();
		    } catch (IOException e) {
		    	return "Failed";
		    }
		    
		    return content;
		}
		
		/*
		 * In questo metodo aggiungo alla base dati la colonna
		 */
		@RequestMapping(value="/board/createColumn/", method=RequestMethod.POST)
		public boolean AddColumn(HttpServletRequest request) {
			
			String title = request.getParameter("titleCol");
			String state = request.getParameter("typeState");
			
			String sql = "INSERT INTO columns(title, state) VALUES(?, ?)";
	        if(template.update(sql, title, state) > 0)
				return true;
	        return false;
		}
		
		/*
		 * devo crere un indirizzo che permetta di riempire la tabella con le colonne create prese dal db
		 */
		@RequestMapping(value="/board/getColumn/", method=RequestMethod.GET)
		public List<Colonna> getColumns() {
			List<Colonna> listColonne;
			String sql = "SELECT * FROM columns WHERE state LIKE 'in corso';";
			listColonne = template.query(sql, new ColumnMapper()); // dovrebbe tornare una lista di oggetti JSON
			return listColonne;
		}
		
		/*
		 * devo crere un indirizzo che permetta di riempire la tabella con le colonne create prese dal db
		 */
		@RequestMapping(value="/board/getFiledColumn/", method=RequestMethod.GET)
		public List<Colonna> getFiledColumns() {
			List<Colonna> listColonne;
			String sql = "SELECT * FROM columns WHERE state LIKE 'archiviato';";
			listColonne = template.query(sql, new ColumnMapper()); // dovrebbe tornare una lista di oggetti JSON
			return listColonne;
		}
		
		/*
		 * Prendo la pagina html contente il form per aggiungere le colonne 
		 */
		@RequestMapping(value="/board/modifyColumn", method=RequestMethod.GET)
		public String ModifyColumn() {
			
			String content = "";
		    try {
		        BufferedReader in = new BufferedReader(new FileReader("target/classes/modifyColumnPage.html"));
		        String str;
		        while ((str = in.readLine()) != null) {
		            content +=str;
		        }
		        in.close();
		    } catch (IOException e) {
		    	return "Failed";
		    }
		    
		    return content;
		}
		
		/*
		 * questo metodo serve per modificare i dati di una colonna
		 */
		@RequestMapping(value="/board/modifyColumn/", method=RequestMethod.PUT)
		public boolean ModifyColumn(HttpServletRequest request) {
			
			String title = request.getParameter("titleColNew");
			String oldTitle = request.getParameter("titleCol");
			String state = request.getParameter("typeStateNew");
			
			String sql = "UPDATE columns SET title=?, state=? WHERE title= ?;";
			
			if(template.update(sql, title, state, oldTitle)>0)
				return true;
			return false;
		}

		/*
		 * In questo metodo aggiungo alla base dati la colonna
		 */
		@RequestMapping(value="/board/deleteColumn/", method=RequestMethod.POST)
		public boolean DeleteColumn(HttpServletRequest request) {
			
			String title = request.getParameter("titleCol");
			String sql = "DELETE FROM columns WHERE title LIKE(?);";
			
	        if(template.update(sql, title) > 0)
				return true;
	        return false;
		}
		
		/*
		 * In questo metodo aggiungo alla base dati la colonna
		 */
		@RequestMapping(value="/board/changeStateColumn/", method=RequestMethod.POST)
		public boolean ChangeStateColumn(HttpServletRequest request) {
			
			String title = request.getParameter("titleCol");
			String sql = "UPDATE columns SET state='in corso' WHERE title=?;";
			
	        if(template.update(sql, title) > 0)
				return true;
	        return false;
		}
		
		/*
		 * questo metodo mi restituisce la pagina per la creazione dei tile
		 */
		@RequestMapping(value="/board/createTile/", method=RequestMethod.GET)
		public String AddTileFormPage() {
			
			String content = "";
		    try {
		        BufferedReader in = new BufferedReader(new FileReader("target/classes/addTilePage.html"));
		        String str;
		        while ((str = in.readLine()) != null) {
		            content +=str;
		        }
		        in.close();
		    } catch (IOException e) {
		    	return "Failed";
		    }
		    
		    return content;
		}
		
		/*
		 * questo metodo serve per aggiungere al database un nuovo oggetto tile
		 */
		@RequestMapping(value="/board/createTile/", method=RequestMethod.POST)
		public boolean AddTile(HttpServletRequest request) {
			
			String title = request.getParameter("title");
			String author = request.getParameter("author");
			String messageType = request.getParameter("messageType");
			String content = request.getParameter("content");
			String columns = request.getParameter("column");
			
			String sql = "INSERT INTO tile(title, author, messageType, content, columns) VALUES(?, ?, ?, ?, ?);";
			
	        if(template.update(sql, title, author, messageType, content, columns) > 0)
				return true;
	        return false;
	        
		}
		
		/*
		 * questo metodo serve per andare a prendere tutti i tiles presenti nel mio database
		 */
		@RequestMapping(value="/board/getTile/", method=RequestMethod.GET)
		public List<Tile> getTiles() {
			
			List<Tile> listTiles;
			String sql = "SELECT * FROM tile;";
			listTiles = template.query(sql, new TileMapper()); // dovrebbe tornare una lista di oggetti JSON
			return listTiles;
			
		}
		
		@RequestMapping(value="/board/deleteTile/", method=RequestMethod.POST)
		public boolean DeleteTile(HttpServletRequest request) {
			
			String id = request.getParameter("idTile");
			String sql = "DELETE FROM tile WHERE id=(?);";
			
	        if(template.update(sql, id) > 0)
				return true;
	        return false;
		}
		
		/*
		 * Prendo la pagina html contente il form per aggiungere le colonne 
		 */
		@RequestMapping(value="/board/modifyTile", method=RequestMethod.GET)
		public String ModifyTilePage() {
			
			String content = "";
		    try {
		        BufferedReader in = new BufferedReader(new FileReader("target/classes/modifyTilePage.html"));
		        String str;
		        while ((str = in.readLine()) != null) {
		            content +=str;
		        }
		        in.close();
		    } catch (IOException e) {
		    	return "Failed";
		    }
		    
		    return content;
		}
		
		/*
		 * questo metodo serve per modificare i dati di una colonna
		 */
		@RequestMapping(value="/board/modifyTile/", method=RequestMethod.PUT)
		public boolean ModifyTile(HttpServletRequest request) {
			
			int id = Integer.parseInt(request.getParameter("id"));
			String title = request.getParameter("newTitle");
			String author = request.getParameter("newAuthor");
			String messageType = request.getParameter("newMessageType");
			String content = request.getParameter("newContent");
			String column = request.getParameter("newColumn");
			
			String sql = "UPDATE tile SET title=?, author=?, messageType=?, content=?, columns=? WHERE id=?;";
			
			if(template.update(sql, title, author, messageType, content, column, id)>0)
				return true;
			return false;
		}
		
		/*
		 * 
		 */
		@RequestMapping(value="/board/filedColumns/", method=RequestMethod.GET)
		public String getBoardFiledPage() {
			
			String content = "";
		    try {
		        BufferedReader in = new BufferedReader(new FileReader("target/classes/BoardFiled.html"));
		        String str;
		        while ((str = in.readLine()) != null) {
		            content +=str;
		        }
		        in.close();
		    } catch (IOException e) {
		    	return "Failed";
		    }
		    
		    return content;
		}
		
}


