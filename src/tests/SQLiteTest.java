package tests;

import org.junit.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import model.SQLite;

public class SQLiteTest {

	private SQLite testObject;
	
	@Before
	public void setup() {
		testObject = new SQLite();
	}
	
	@After
	public void cleanup() {
		testObject = null;
	}

	// test the connect() - check that connection was established
	@Test
	public void connectNoArgsTest() throws SQLException {
		Connection result = testObject.connect();
		Assert.assertNotEquals(null, result);
	}

	
	// test the connect(String dbName) - check that connection was established
	@Test
	public void connectArgsTest() throws SQLException {
		Connection result = testObject.connect("jdbc:sqlite:src/someInvalidEntry.db"); // is this really expected?!
		Assert.assertNotEquals(null, result);
	}
	
	// test the connect(String dbName) - check that connection was NOT established
	@Test
	public void invalidConnectArgsTest() throws SQLException {
		Connection result = testObject.connect("http://www.someurladdress.com");
		Assert.assertEquals(null, result);
	}

	// test the getWordA() - check that PL word is returned for valid id
	@Test
	public void validGetWordATest() {
		String result;

		// check that each entry in db has assigned valid word A
		for (int i = 1; i <= testObject.baseSize(); i++) {
			result = testObject.getWordA(i);
			Assert.assertNotEquals(null, result);
		}
	}

	// test the getWordA() - check that PL word is not returned for invalid id
	@Test
	public void invalidGetWordATest() {
		int[] wordIds = {-1, 0, testObject.baseSize() + 1};
		String result;
		
		for(int i: wordIds) {
			result = testObject.getWordA(i);
			Assert.assertEquals(null, result);
		}
	}
	
	// test the getWordB() - check that ENG word is returned for valid id
	@Test
	public void validGetWordBTest() {
		String result;

		// check that each entry in db has assigned valid word B
		for (int i = 1; i <= testObject.baseSize(); i++) {
			result = testObject.getWordB(i);
			Assert.assertNotEquals(null, result);
		}
	}

	// test the getWordB() - check that ENG word is not returned for invalid id
	@Test
	public void invalidGetWordBTest() {
		int[] wordIds = {-1, 0, testObject.baseSize() + 1};
		String result;
		
		for(int i: wordIds) {
			result = testObject.getWordB(i);
			Assert.assertEquals(null, result);
		}
	}

	// test the getBasket() - check that container number is returned for valid id
	@Test
	public void validGetBasketTest() {
		int result;

		// check that each entry in db has assigned valid container number
		for (int i = 1; i <= testObject.baseSize(); i++) {
			result = testObject.getBasket(i);
			Assert.assertNotEquals(0, result);
		}
	}
	

	// test the getBasket() - check that container number is returned for valid id
	@Test
	public void invalidGetBasketTest() {
		int[] basketIds = {-1, 0, testObject.baseSize() + 1};
		int result;
		
		for (int i: basketIds) {
			result = testObject.getBasket(i);
			Assert.assertEquals(0, result);
		}
	}
	

	// test the setBasket() - check that desired container number is assigned to db entry of given id
	@Test
	public void validSetBasketUpTest() {
		int[] id = {1, 5, testObject.baseSize()};
		int oldBasket;
		int expectedBasket;
		int newBasket;
		
		for (int i: id) {
			oldBasket = testObject.getBasket(i);
			
			if (oldBasket >= 5)
				expectedBasket = 6;
			else
				expectedBasket = oldBasket + 1;
			
			testObject.setBasket(i, true);
			newBasket = testObject.getBasket(i);
		
			Assert.assertEquals(expectedBasket, newBasket);
			if(oldBasket < 5)
				Assert.assertNotEquals(oldBasket, newBasket);
		}
	}
	
	// test the setBasket() - check that desired container number is assigned to db entry of given id
	@Test
	public void validSetBasketDownTest() {
		int[] id = {1, 10, testObject.baseSize()};
		int oldBasket;
		int expectedBasket;
		int newBasket;
		
		for (int i: id) {
			oldBasket = testObject.getBasket(i);
			
			if (oldBasket == 1)
				expectedBasket = 1;
			else if (oldBasket == 6)
				expectedBasket = 6;
			else
				expectedBasket = oldBasket - 1;
			
			testObject.setBasket(i, false);
			newBasket = testObject.getBasket(i);
		
			Assert.assertEquals(expectedBasket, newBasket);
			if(oldBasket != 1 && oldBasket != 6)
				Assert.assertNotEquals(oldBasket, newBasket);
		}
	}

	// test the setBasket() - check that container number is not assigned to db entry of invalid id
	@Test
	public void invalidIdSetBasketTest() {
		int[] id = {-1, 0, testObject.baseSize() + 1};
		int oldBasket;
		int newBasket;
		
		for (int i: id) {
			oldBasket = testObject.getBasket(i);
			testObject.setBasket(i, false);
			newBasket = testObject.getBasket(i);
		
			Assert.assertEquals(0, oldBasket);
			Assert.assertEquals(0, newBasket);
		}
	}
	
	// test the setBasketAmount() - check that only values 2, 3, 4, 5 can be set
	@Test
	public void setBasketAmountTest() {
		int[] givenAmounts    = {-1, 0, 1, 2, 3, 4, 5, 6, 100};
		int[] expectedAmounts = { 2, 2, 2, 2, 3, 4, 5, 5,   5};
		
		for (int i = 0; i < givenAmounts.length; i++) {
			testObject.setBasketAmount(givenAmounts[i]);
			Assert.assertEquals(expectedAmounts[i], testObject.getBasketAmount());
		}
		
	}

	// test the baseSizeTest() - check that size of db is returned
	@Test
	public void baseSizeTest() {
		int result = testObject.baseSize();
		Assert.assertEquals(40, result);
	}

	// test the baseSizeTest() - check that sum of records for all containers gives 40
	@Test
	public void baseSizeArgsTest() {
		int result = 0;
		for(int i = 0; i < 6; i++) {
			result += testObject.baseSize(i + 1);
		}
		Assert.assertEquals(40, result);
	}
	
	// test the changeBasket(currentBasket)
	@Test
	public void changeBasketTest() {
		System.out.println("BasketAmount = " + testObject.getBasketAmount());
		int[] basketIds   = {-1, 0, 1, 2, 3, 4, 5, 7, 100};
		int[] expectedIds = { 1, 1, 2, 3, 4, 5, 1, 1, 1};
		
		int result;
		
		for(int i = 0; i < basketIds.length; i++) {
			result = testObject.changeBasket(basketIds[i]);
			Assert.assertEquals(expectedIds[i], result);
		}
	}
	
	// test getTotalCorrect() and setTotalCorrect()
	@Test
	public void setgetTotalCorrectTest() {
		int oldCorrect;
		int newCorrect;
		
		for (int i = 0; i < 5; i++) {
			oldCorrect = testObject.getTotalCorrect("admin");
			
			testObject.setTotalCorrect("admin");
			newCorrect = testObject.getTotalCorrect("admin");
			
			Assert.assertEquals(oldCorrect + 1, newCorrect);
		}
	}
	
	// test getTotalAttempt() and setTotalAttempt()
	@Test
	public void setgetTotalAttemptTest() {
		int oldAttempt;
		int newAttempt;
		
		for (int i = 0; i < 5; i++) {
			oldAttempt = testObject.getTotalAttempt("admin");
			
			testObject.setTotalAttempt("admin");
			newAttempt = testObject.getTotalAttempt("admin");
			
			Assert.assertEquals(oldAttempt + 1, newAttempt);
		}
	}
	
	@Test
	public void getRandomIdTest() {
		// tbd...
	}
	

}
