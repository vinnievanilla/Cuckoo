package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class SQLite {

	
	private int basketAmount = 5;

	// ****************************************************
	// set number of baskets
	// ****************************************************
	public void setBasketAmount(int amount) {

		if (amount < 2)
			amount = 2;
		if (amount > 5)
			amount = 5;
		basketAmount = amount;
	}
	
	public int getRandomID(int basket) {
		Random rand = new Random();

		int randomId = rand.nextInt(baseSize(basket));
		ArrayList<Integer> BasketIdList = new ArrayList<Integer>();

		String sql = "SELECT ID FROM main_table WHERE BASKET =" + basket;

		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				BasketIdList.add(rs.getInt("ID"));
			}
			randomId = BasketIdList.get(rand.nextInt(BasketIdList.size()));

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return randomId;
	}


		
	
// 	****************************************************
//	    connect to database with fixed url address
// 	****************************************************
    public Connection connect() {
    	return connect("jdbc:sqlite:src/resources/db/main.db");
    }


// 	****************************************************
// 	    connect to database with given url address
// 	****************************************************
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
    

// 	****************************************************
//    debug only tool
//    print all records in database
// 	****************************************************
    public void selectAll(){
        String sql = "SELECT ID, PL, ENG, BASKET FROM main_table";
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement(); // przelozenie string na sql
             ResultSet rs    = stmt.executeQuery(sql)){ // konwersja sql
            
            // loop through the result set
            while (rs.next()) { // mapowanie wyniku, potrzebne do wyswietlenia
                System.out.println(rs.getInt("ID") +  "\t" + 
                                   rs.getString("PL") + "\t" +
                                   rs.getString("ENG") + "\t" +
                                   //rs.getString("USER") + "\t" +
                                   rs.getInt("BASKET"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());        
        }
    
    }
    

// 	****************************************************
//    debug only tool
//    print record with given id
// 	****************************************************
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


//	****************************************************
//	function returning word A (currently PL word)
//	for record with given id
//	****************************************************
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


//****************************************************
//function returning word B (currently ENG word)
//for record with given id
//****************************************************
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


//	****************************************************
//	function returning basket (container) number
//	for record with given id
//	****************************************************
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

//	****************************************************
//	function assigning basket (container) number
//	for record with given id
//	****************************************************
	public void setBasket(int id, boolean isWordCorrect) {

		int recordId = id;

		int newBasket = getBasket(id);

		if (isWordCorrect)
			newBasket++;
		else if (newBasket != 1)
			newBasket--;

		if (newBasket > basketAmount + 1)
			newBasket = basketAmount + 1; // fikcyjny koszyk, poza zakresem

		String sql = "UPDATE main_table SET BASKET =" + newBasket + " WHERE ID =" + recordId + " ";

		try (Connection conn = this.connect();

				Statement stmt = conn.createStatement();

				ResultSet rs = stmt.executeQuery(sql)) {

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		}

	}

//	****************************************************
// 	authorization function
// 	***************************************************

public static boolean checkUser(String login, String password) {
	// podajemy jako parametry login i password, je�eli znadjdziemy takiego
	// u�ytkownika w bazie zwracamy true, jak nie to false
	return true;
}

// 	****************************************************
// 	Create an account
// 	***************************************************
public static void createAccount(String login, String password) {
	// podaj� login i password, nale�y doda� u�ytkownika do nowej tabeli user. Jak
	// b�dzie ok to informacje, �e konto zosta�o utworzone, jak nie to info, �e ju�
	// jest taki login.
}

// ****************************************************
// Function returning db size (total number of records)
// ***************************************************
public int baseSize(){
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

	public int baseSize(int basket) {

		int dbSize = 0;

		String sql = "SELECT count(id) FROM main_table WHERE BASKET =" + basket;

		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			dbSize = rs.getInt("count(id)");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbSize;
	}

	public int changeBasket(int currentBasket) {
		if (currentBasket > basketAmount)
			currentBasket = 1;
		else
			currentBasket++;

		return currentBasket;
	}

public void getBasketStatus(String bName, String usr){
	
	String basketName = bName;
	String user = usr;
	
    String sql = "SELECT " + basketName + " FROM user_table WHERE USER '= " +user+ "' ";
    
    try (Connection conn = this.connect();
         Statement stmt  = conn.createStatement(); 
         ResultSet rs    = stmt.executeQuery(sql)){
     	
        // loop through the result set
        while (rs.next()) { 
            System.out.println(rs.getInt(basketName));
                   }
    } catch (SQLException e) {
        System.out.println(e.getMessage());        
    }

}

public void setTotalCorrect(String usr, int total) {

	String user = usr;
	int totalCorrect = total;
	
	
		String sql = "UPDATE user_table SET TOTALCORRECT =" + totalCorrect + " WHERE USERID ='" + user + "' ";

	try (Connection conn = this.connect();

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sql)) {

	} catch (SQLException e) {

		System.out.println(e.getMessage());

	}

}






}
	
	
	
	

