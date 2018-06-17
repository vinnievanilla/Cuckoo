package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

public class SQLite {

	
	private int basketAmount = 5;
	private static String currentUser = null;

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
	
	public int getBasketAmount() {
		return basketAmount;
	}
	
	public int getRandomID(int basket) {
		Random rand = new Random();
		int randomId = 0;
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


	// ****************************************************
	// connect to database with fixed url address
	// ****************************************************
    public static Connection connect() {
    	return connect("jdbc:sqlite:src/resources/db/main.db");
    }

    
	// ****************************************************
	// connect to database with given url address
	// ****************************************************
    public static Connection connect(String dbName) {
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
    
    
	// ****************************************************
	// function returning record for given sql command
	// ****************************************************
	public static String getUniqueRecord(String sql) {
		String SQLrecord = null;

		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			SQLrecord = rs.getString(1);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return SQLrecord;
	}
	
	
	// ****************************************************
	// update record of given sql in db
	// ****************************************************
	public static void updateRecord(String sql) {
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
    

	// ****************************************************
	// debug only tool
	// print all records in database
	// ****************************************************
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
    

	// ****************************************************
	// debug only tool
	// print record with given id
	// ****************************************************
	public void showRecord(int id) {

		String sql = "SELECT ID, PL, ENG FROM main_table WHERE ID =" + id + " ";

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
	// function returning word A (currently PL word)
	// for record with given id
	// ****************************************************
	public String getWordA(int id) {
		String sql = "SELECT PL FROM main_table WHERE ID =" + id + " ";
		return getUniqueRecord(sql);
	}


	// ****************************************************
	// function returning word B (currently ENG word)
	// for record with given id
	// ****************************************************
	public String getWordB(int id) {
		String sql = "SELECT ENG FROM main_table WHERE ID =" + id + " ";
		return getUniqueRecord(sql);
	}


	// ****************************************************
	// function returning basket (container) number
	// for record with given id
	// ****************************************************
	public int getBasket(int id) {
		String sql = "SELECT BASKET FROM main_table WHERE ID =" + id + " ";
		if (getUniqueRecord(sql) != null)
			return Integer.parseInt(getUniqueRecord(sql));
		else return 0;
	}


	// ****************************************************
	// function assigning basket (container) number
	// for record with given id
	// ****************************************************
	public void setBasket(int id, boolean isWordCorrect) {

		int newBasket = getBasket(id);

		if (isWordCorrect)
			newBasket++;
		else if (newBasket != 1 && newBasket != 6)
			newBasket--;

		if (newBasket > basketAmount) // redundant ?
			newBasket = basketAmount + 1; // fikcyjny koszyk, poza zakresem

		String sql = "UPDATE main_table SET BASKET =" + newBasket + " WHERE ID =" + id + " ";
		updateRecord(sql);
	}

	
	// ****************************************************
	// Function returning db size (total number of records)
	// ***************************************************
	public int baseSize() {
		String sql = "SELECT count(id) FROM main_table";
		return Integer.parseInt(getUniqueRecord(sql));
	}


	// ****************************************************
	// Function returning db size (total number of records)
	// assigned to given container number
	// ***************************************************
	public int baseSize(int basket) {
		String sql = "SELECT count(id) FROM main_table WHERE BASKET =" + basket;
		return Integer.parseInt(getUniqueRecord(sql));
	}


	// ****************************************************
	// switch to next basket: 1->...->basketAmount->1
	// ****************************************************
	public int changeBasket(int currentBasket) {
		if (currentBasket >= basketAmount || currentBasket < 1)
			currentBasket = 1;
		else
			currentBasket++;

		return currentBasket;
	}

	
	// ****************************************************
	// debug only tool
	// ****************************************************
	public void getBasketStatus(String basketName, String user) {
		String sql = "SELECT " + basketName + " FROM user_table WHERE USER '= " + user + "' ";
		System.out.println(getUniqueRecord(sql));
	}


	// ****************************************************
	// increment amount of total correct answers of given user
	// ****************************************************
	public void setTotalCorrect(String user) {
		int totalCorrect = getTotalCorrect(user) + 1;
		String sql = "UPDATE user_table SET TOTALCORRECT =" + totalCorrect + " WHERE USER ='" + user + "' ";
		updateRecord(sql);
	}

	// ****************************************************
	// get amount of total correct answers of given user
	// ****************************************************
	public int getTotalCorrect(String user) {
		String sql = "SELECT totalCorrect FROM user_table WHERE USER = '" + user + "' ";
		return Integer.parseInt(getUniqueRecord(sql));
	}


	// ****************************************************
	// increment amount of total answers of given user
	// ****************************************************
	public void setTotalAttempt(String user) {
		int totalAttempt = getTotalAttempt(user) + 1;
		String sql = "UPDATE user_table SET TOTALATTEMPT =" + totalAttempt + " WHERE USER ='" + user + "' ";
		updateRecord(sql);
	}

	
	// ****************************************************
	// get amount of total answers of given user
	// ****************************************************
	public int getTotalAttempt(String user) {
		String sql = "SELECT totalAttempt FROM user_table WHERE USER = '" + user + "' ";
		return Integer.parseInt(getUniqueRecord(sql));
	}

	
	// ****************************************************
	// authorization function
	// ***************************************************

	public static boolean checkUser(String login, String password) {
		if (validateLogPass(login, password) == false)
			return false;

		String sql = "SELECT userID FROM user_table WHERE USER = '" + login +
				                               "' AND PASSWORD = '" + password + "' ";
		if (getUniqueRecord(sql) != null) {
//			Alert.display("Informacja", "Witaj " + login + "!");
			currentUser = login;
			return true;
		}
		else
			return false;
	}

	
	// ****************************************************
	// Create an account
	// ***************************************************
	public static boolean createAccount(String login, String password) {
		String sql1 = "SELECT userID FROM user_table WHERE USER = '" + login + "' ";
	
		if (getUniqueRecord(sql1) != null) {
//			Alert.display("Informacja", "Login istnieje!");
			return false;
		}
		
		String sql2 = "INSERT INTO user_table (user, password) VALUES('" + login + "', '" + password + "')";
		updateRecord(sql2);
//		Alert.display("Informacja", "U�ytkownik " + login + " zosta� zarejestrowany!");
		return true;
	}
	
	public static boolean validateLogPass(String login, String password) {
		if (login == null  || login == ""      ||
			password == "" || password == null ||
			login == " "   || password == " " ) {
			return false;
		}
		return true;
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String cU) {
		currentUser = cU;
	}

	public void addNewTable(String csvFile) {
		BufferedReader buffer = null;
		String line = "";

		try {
			buffer = new BufferedReader(new FileReader(csvFile));
			while ((line = buffer.readLine()) != null) {
				String[] table = line.split(";");
				String sql2 = "INSERT INTO main_table (PL, ENG) VALUES('" + table[0] + "', '" + table[1] + "')";
				updateRecord(sql2);
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			if (buffer != null) {
				try {
					buffer.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
}
	
	
	
	

