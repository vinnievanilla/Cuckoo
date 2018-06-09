package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite {

	   
    public static void connectTest() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:db/main.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public static void connectTest(String dbName) {
        Connection conn = null;
        try {
            // db parameters
            String url = dbName;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
  
    public Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/resources/db/main.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conected to DB");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Not conected to DB");
        }
        return conn;
    }
    
    public Connection connect(String dbName) {
        // SQLite connection string
        String url = dbName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Conected to DB " + dbName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Not conected to DB");
        }
        return conn;
    }
    
    
    
    
    
    
    // funkcje, których będziemy używali
    
    public void selectAll(){
        String sql = "SELECT ID, PL, ENG, USER, BASKET FROM main_table";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("ID") +  "\t" + 
                                   rs.getString("PL") + "\t" +
                                   rs.getString("ENG") + "\t" +
                                   rs.getString("USER") + "\t" +
                                   rs.getInt("BASKET"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());        
        }
    
    }
    

// ****************************************************

// Funkcja zwracająca rekord z DB o podanym ID

// ****************************************************

public void showRecord(int id) {

	int recordId = id;

	String sql = "SELECT ID, PL, ENG FROM main_table WHERE ID =" + recordId + " ";

	try (Connection conn = this.connect();

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql)) {

		// loop through the result set

		while (rs.next()) {

			System.out.println(rs.getInt("ID") + "\t" +

					rs.getString("PL") + "\t" +

					rs.getString("ENG") + "\t");

		}

	} catch (SQLException e) {

		System.out.println(e.getMessage());

	}

}

// ****************************************************

// Funkcja zwracająca wyraz A dla rekordu o danym ID

// ***************************************************

public String getWordA(int id) {

	int recordId = id;

	String wordA = null;

	String sql = "SELECT PL FROM main_table WHERE ID =" + recordId + " ";

	try (Connection conn = this.connect();

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql)) {

		wordA = rs.getString("PL");

	} catch (SQLException e) {

		System.out.println(e.getMessage());

	}

	return wordA;

}

// ****************************************************

// Funkcja zwracająca wyraz B dla rekordu o danym ID

// ***************************************************

public String getWordB(int id) {

	int recordId = id;

	String wordB = null;

	String sql = "SELECT ENG FROM main_table WHERE ID =" + recordId + " ";

	try (Connection conn = this.connect();

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql)) {

		wordB = rs.getString("ENG");

	} catch (SQLException e) {

		System.out.println(e.getMessage());

	}

	return wordB;

}

// ****************************************************

// Funkcja zwracająca numer koszyka dla rekordu o danym ID

// ***************************************************

public int getBasket(int id) {

	int recordId = id;

	int basket = 0;

	String sql = "SELECT BASKET FROM main_table WHERE ID =" + recordId + " ";

	try (Connection conn = this.connect();

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql)) {

		basket = rs.getInt("BASKET");

	} catch (SQLException e) {

		System.out.println(e.getMessage());

	}

	return basket;

}

// ****************************************************

// Funkcja ustawiająca numer koszyka dla rekordu o danym ID

// ***************************************************

public void setBasket(int id, int basket) {

	int recordId = id;

	int newBasket = basket;

	String sql = "UPDATE main_table SET BASKET =" + newBasket + " WHERE ID =" + recordId + " ";

	try (Connection conn = this.connect();

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql)) {

	} catch (SQLException e) {

		System.out.println(e.getMessage());

	}

}

// ****************************************************
// Funkcja autoryzacji
// ***************************************************

public static boolean checkUser(String login, String password) {
	// podajemy jako parametry login i password, je�eli znadjdziemy takiego
	// u�ytkownika w bazie zwracamy true, jak nie to false
	return true;
}

// ****************************************************
// Tworzenie konta
// ***************************************************

public static void createAccount(String login, String password) {
	// podaj� login i password, nale�y doda� u�ytkownika do nowej tabeli user. Jak
	// b�dzie ok to informacje, �e konto zosta�o utworzone, jak nie to info, �e ju�
	// jest taki login.
}

// ****************************************************
// Funkcja zwracająca rozmiar bazy danych
// ***************************************************

public int baseSize(){
    	//funkcja zwraca nam ile jest s��wek w bazie, bym wiedzia� z jakiego zakresu funkcja random ma losowa�
    	
	int dbSize = 0;

	String sql = "SELECT count(id) FROM main_table";
	
	try (Connection conn = this.connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
		dbSize = rs.getInt("count(id)");

	} catch (SQLException e) {
		System.out.println(e.getMessage());
	}
	return dbSize;
}

// public static void main(String[] args) {
// // TODO Auto-generated method stub
//
// SQLite get = new SQLite();
// System.out.println(get.baseSize());
//
//
// }
	
}
	
	
	
	

