/**
 * 
 */


import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Shahin
 *
 */
public class BudgetPlanModelTest {
	
	//private static Connection dbTestConnection;
	
	static BudgetPlanModel testModel = new BudgetPlanModel();
	/**
	 * Vordefinierte Datenbankdaten die mithilfe der H2-Konsole erstellt wurden werden kopiert.
	 * 
	 * Allgemein: Bleibt bestehen. Subkategorien werden gelöscht.
	 * TestKategorie1: wird gelöscht
	 * Testkategorie2: wird gelöscht
	 * TestKategorie3: wird nie gelöscht. Muss immer enthalten sein.
	 */
	
	@BeforeClass
	public static void setup()
	{
	   try {
		   Path src = Paths.get("./data/dbProfile/BudgetPlanerTestDaten.mv.db");
		   Path out = Paths.get("./data/dbProfile/BudgetPlanerDaten_test.mv.db");
		   Files.copy(src, out, StandardCopyOption.REPLACE_EXISTING);
		//testModel.registerUser("test", "test");
		testModel.initiateDatabase("test", "test");
	} catch (SQLException | ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		fail("SQL Exception thrown");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		fail("IOException");
	}
	}
	
	@AfterClass
	public static void tearDown() throws Exception //Wird nach jedem einzelnen Test aufgerufen. Da die Zugriffszeiten von Java allerdings schneller sind als die von H2 wird DB ggf. noch benutzt, weshab ein Löschen erfolglos ist.
	{
		testModel.DROP_ALL();
		testModel.closeConnection();
	}


	/**
	 * Test method for {@link BudgetPlanModel#BudgetPlanModel()}.
	 */
	@Test
	public void testBudgetPlanModel() {
		//Leerer Konstruktor nicht testbar
		//fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link BudgetPlanModel#setConnection(java.sql.Connection)}.
	 */
	@Test
	public void testSetConnection() {
	//	wird implizit getestet
	//	assertEquals(testModel.getConnection(), dbTestConnection);
	}
	
	/**
	 * Test method for {@link BudgetPlanModel#getConnection()}.
	 */
	@Test
	public void testGetConnection() {
		assertNotNull(testModel.getConnection());
	}

	/**
	 * Test method for {@link BudgetPlanModel#closeConnection()}.
	 */
	@Test
	public void testCloseConnection() {
		//Muss nicht getestet werden, da sich im Zweifel H2 um das Connection-Management kümmert
	}

	/**
	 * Test method for {@link BudgetPlanModel#registerUser(java.lang.String, java.lang.String)}.
	 * @throws SQLException 
	 * @throws InvocationTargetException 
	 * @throws ClassNotFoundException 
	 */
	@Test (expected=InvocationTargetException.class)
	public void testRegisterUser() throws InvocationTargetException {
			try {
				Method method = testModel.getClass().getDeclaredMethod("validateUser", String.class, String.class);
				method.setAccessible(true);
				method.invoke(testModel, "test","test"); //hier sollte keine Exception geworfen werden
				method.invoke(testModel, "existiertnicht", "existiertnicht"); //Hier sollte Exception geworfen werden.
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("ClassNotFoundException");
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("SecurityException");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("IllegalAccessException");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("IllegalArgumentException");
			}
	}

	/**
	 * Test method for {@link BudgetPlanModel#initiateDatabase(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testInitiateDatabase() {
//		try {
//			//testModel.registerUser("test", "test");
//			testModel.initiateDatabase("test", "test");
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail(e.getCause().getMessage());
//		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#insert_Kategorie(java.lang.String)}.
	 */
	@Test
	public void testInsert_Kategorie() {
		try {
			String newKateg = "Testkategorie";
			String[] prevKateg = testModel.return_Kategorien();
			testModel.insert_Kategorie(newKateg);
			String[] postKateg = testModel.return_Kategorien();
			assertTrue(prevKateg.length != postKateg.length);
			assertFalse(newKateg.equals(postKateg));
			assertTrue(Arrays.asList(testModel.return_Kategorien()).contains(newKateg));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("SQLException");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#insert_Subkategorie(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testInsert_Subkategorie() {
		try {
		String subK = "TestSubkategorie1";
		testModel.insert_Subkategorie(subK, "Testkategorie4");
		String [] subKArray = {subK};
		assertEquals(Arrays.toString(testModel.return_Subkategorien("Testkategorie4")), Arrays.toString(subKArray));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#update_User(double)}.
	 */
	@Test
	public void testUpdate_User() {
		try {
			double prevBudget = testModel.getBudget();
			double postBudget = 117.92;
			testModel.update_User(postBudget);
			assertFalse(prevBudget == postBudget);
			assertEquals(postBudget, testModel.getBudget(), 0.00); //Da Budget auf keiner Kalkulation basiert ist Delta 0.0
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
		
	}

	/**
	 * Test method for {@link BudgetPlanModel#insert_Posten(java.lang.String, java.lang.String, java.lang.String, double, int, java.lang.String, int)}.
	 */
	@Test
	public void testInsert_PostenStringStringStringDoubleIntStringInt() { 
		try {
			testModel.insert_Posten("TestPosten", "Allgemein", "-", 42.42, 3, "Ich bin ein Testposten", 3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#insert_Posten(java.lang.String, int, int, double, int, java.lang.String, int)}.
	 */
	@Test
	public void testInsert_PostenStringIntIntDoubleIntStringInt() {
		try {
			testModel.insert_Posten("Testposten", 1	, 1, 24.24, 9, "Auch ich bin ein Testposten", 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#return_Kategorien()}.
	 */
	@Test
	public void testReturn_Kategorien() {
		try {
		assertNotNull(testModel.return_Kategorien());
		assertTrue(Arrays.asList(testModel.return_Kategorien()).contains("TestKategorie3"));
		//assertEquals(Arrays.toString(testModel.return_Kategorien()), "[Allgemein]");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#return_Subkategorien(java.lang.String)}.
	 */
	@Test
	public void testReturn_Subkategorien() {
		try {
			assertNotNull(testModel.return_Subkategorien("Allgemein"));
			assertTrue(Arrays.asList(testModel.return_Subkategorien("Allgemein")).contains("-"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Exception thrown");
			}
	}

	/**
	 * Test method for {@link BudgetPlanModel#getBudget()}.
	 */
	@Test
	public void testGetBudget() {
		try {
			assertNotNull(testModel.getBudget());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#DROP_ALL()}.
	 */
//	@Test (expected=SQLException.class)
//	public void testDROP_ALL() throws SQLException {
//			testModel.DROP_ALL();
//			testModel.getBudget();
//	}

	/**
	 * Test method for {@link BudgetPlanModel#deletePosten(int)}.
	 */
	@Test
	public void testDeletePosten() { //Problem: Großer Haufen von Interdependenzen...
		try {
			testModel.insert_Posten("TestLoeschen", 1, 1, 17.17, 2, "Dieser Testposten soll erfolgreich gelöscht werden", 1);
			ArrayList<Posten> testPosten = testModel.transcribe();
			int prevSize = testPosten.size();
			int id;
			for(int i = 0; i < prevSize; i++) {
				if(testPosten.get(i).getBezeichnung().equals("TestLoeschen")) {
					id = testPosten.get(i).getProdukt_ID();
					testModel.deletePosten(id);
				}
			}
			testPosten = testModel.transcribe();
			int postSize = testPosten.size();
			assertFalse(prevSize == postSize); //TODO: Hier wird eigentlich nur getestet, ob gelöscht wird. nicht was
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#deleteKategorie(int)}.
	 */
	@Test
	public void testDeleteKategorieInt() throws SQLException {
			String[] prevKategorie = testModel.return_Kategorien();
			testModel.deleteKategorie(2); //TestKategorie1
			String[] postKategorie = testModel.return_Kategorien();
			assertFalse(Arrays.toString(postKategorie).equals(Arrays.toString(prevKategorie)));
			assertFalse(prevKategorie.length == postKategorie.length);
			assertTrue(Arrays.asList(testModel.return_Kategorien()).contains("TestKategorie3"));			
	}

	/**
	 * Test method for {@link BudgetPlanModel#deleteKategorie(java.lang.String)}.
	 */
	@Test
	public void testDeleteKategorieString() {
		try {
			String[] prevKategorie = testModel.return_Kategorien();
			testModel.deleteKategorie("TestKategorie2");
			String[] postKategorie = testModel.return_Kategorien();
			assertTrue(Arrays.asList(testModel.return_Kategorien()).contains("TestKategorie3"));
			assertFalse(prevKategorie.length == postKategorie.length);
			assertFalse(Arrays.toString(prevKategorie).equals(Arrays.toString(postKategorie)));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#deleteSubkategorie(int)}.
	 */
	@Test
	public void testDeleteSubkategorieInt() {
		try {
			String[] prevSubkategorie = testModel.return_Subkategorien("Allgemein");
			testModel.deleteSubkategorie(15); //TestSubkategorie1 der Kategorie Allgemein
			String[] postSubkategorie = testModel.return_Subkategorien("Allgemein");
			assertFalse(prevSubkategorie.length == postSubkategorie.length);
			assertFalse(Arrays.toString(prevSubkategorie).equals(Arrays.toString(postSubkategorie)));
			assertTrue(Arrays.asList(testModel.return_Subkategorien("Allgemein")).contains("-"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#deleteSubkategorie(java.lang.String, int)}.
	 */
	@Test
	public void testDeleteSubkategorieStringInt() {
		try {
			String[] prevSubkategorie = testModel.return_Subkategorien("Allgemein");
			testModel.deleteSubkategorie("TestSubkategorie2", 1);
			String[] postSubkategorie = testModel.return_Subkategorien("Allgemein");
			assertFalse(prevSubkategorie.length == postSubkategorie.length);
			assertFalse(Arrays.toString(prevSubkategorie).equals(Arrays.toString(postSubkategorie)));
			assertTrue(Arrays.asList(testModel.return_Subkategorien("Allgemein")).contains("-"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#deletePostenCondition(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeletePostenConditionStringString() {
		try {
			testModel.insert_Posten("TestpostenCondition", "Allgemein", "-", 12, 2, "Testposten für delete mit Condition", 3);
			testModel.insert_Posten("TestpostenCondition", "Allgemein", "-", 12, 2, "Testposten für delete mit Condition", 3);
			testModel.insert_Posten("TestpostenNoCondition", "Allgemein", "-", 12, 2, "Testposten für delete mit Condition", 3);
			int prevSize = testModel.transcribe().size();
			testModel.deletePostenCondition("Bezeichnung", "TestpostenCondition");
			int postSize = testModel.transcribe().size();
			assertFalse(prevSize == postSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#deletePostenCondition(java.lang.String, int)}.
	 */
	@Test
	public void testDeletePostenConditionStringInt() {
		try {
			testModel.insert_Posten("TestpostenInt", "Allgemein", "-", 12, 2, "Testposten für delete mit Condition", 3);
			testModel.insert_Posten("TestpostenInt", "Allgemein", "-", 12, 2, "Testposten für delete mit Condition", 3);
			testModel.insert_Posten("TestpostenInt", "Allgemein", "-", 12, 2, "Testposten für delete mit Condition", 3);
			int prevSize = testModel.transcribe().size();
			testModel.deletePostenCondition("Kategorie", 1);
			int postSize = testModel.transcribe().size();
			assertFalse(prevSize == postSize);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#transcribe()}.
	 */
	@Test
	public void testTranscribe() {
		try {
			testModel.insert_Posten("Testposten", 1, 1, 12, 2, "Testposten für transcribe", 3);
			assertNotNull(testModel.transcribe());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

}
