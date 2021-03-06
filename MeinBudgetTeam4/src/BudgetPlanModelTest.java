



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.AfterClass;
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
	 * Allgemein: Bleibt bestehen. Subkategorien werden gel�scht.
	 * TestKategorie1: wird gel�scht
	 * Testkategorie2: wird gel�scht
	 * TestKategorie3: wird nie gel�scht. Muss immer enthalten sein.
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
		e.printStackTrace();
		fail("SQL Exception thrown");
	} catch (IOException e) {
		e.printStackTrace();
		fail("IOException");
	}
	}
	
	@AfterClass
	public static void tearDown() throws Exception //Wird nach jedem einzelnen Test aufgerufen. Da die Zugriffszeiten von Java allerdings schneller sind als die von H2 wird DB ggf. noch benutzt, weshab ein L�schen erfolglos ist.
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
		//Muss nicht getestet werden, da sich im Zweifel H2 um das Connection-Management k�mmert
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
				e.printStackTrace();
				fail("ClassNotFoundException");
			} catch (SecurityException e) {
				e.printStackTrace();
				fail("SecurityException");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				fail("IllegalAccessException");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				fail("IllegalArgumentException");
			}
	}

	/**
	 * Test method for {@link BudgetPlanModel#initiateDatabase(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testInitiateDatabase() {
		try {
			testModel.initiateDatabase("test", "test");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			fail(e.getCause().getMessage());
		}
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
			ArrayList<Posten> before = testModel.transcribe();
			testModel.insert_Posten("TestPosten", "Allgemein", "-", 42.42, 3, "Ich bin ein Testposten", 3);
			ArrayList<Posten> after = testModel.transcribe();
			assertTrue(before.size() != after.size());
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
			ArrayList<Posten> before = testModel.transcribe();
			testModel.insert_Posten("Testposten", 1	, 1, 24.24, 9, "Auch ich bin ein Testposten", 1);
			ArrayList<Posten> after = testModel.transcribe();
			assertTrue(before.size() != after.size());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}
	/**
	 * Test method for {@link BudgetPlanModel#insert_Posten(java.lang.String, java.lang.String, java.lang.String, double, int, java.lang.String, int, Timestamp)}.
	 */
	@Test
	public void testInsert_PostenStringStringStringDoubleIntStringIntTimestamp() {
		try {
			Date heute = new Date();
			ArrayList<Posten> before = testModel.transcribe();
		    Timestamp sqlheute = new Timestamp(heute.getTime());
			testModel.insert_Posten("TestPostenDatum", "Allgemein", "-", 42.99, 3, "Ich bin ein Testposten mit Timestamp", 3, sqlheute);
			ArrayList<Posten> after = testModel.transcribe();
			assertTrue(before.size() != after.size());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
		
	}
	
	/**
	 * Test method for {@link BudgetPlanModel#insert_Posten(java.lang.String, int, int, double, int, java.lang.String, int, Timestamp)}.
	 */
	@Test
	public void testInsert_PostenStringIntIntDoubleIntStringIntTimestamp() {
		try {
			Date heute = new Date();
			ArrayList<Posten> before = testModel.transcribe();
		    Timestamp sqlheute = new Timestamp(heute.getTime());
			testModel.insert_Posten("Testposten mit Timestamp", 1	, 1, 24.99, 9, "Auch ich bin ein Testposten mit Timestamp", 1, sqlheute);
			ArrayList<Posten> after = testModel.transcribe();
			assertTrue(before.size() != after.size());
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
		} catch (SQLException e) {
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
	public void testDeletePosten() { //Problem: Gro�er Haufen von Interdependenzen...
		try {
			testModel.insert_Posten("TestLoeschen", 1, 1, 17.17, 2, "Dieser Testposten soll erfolgreich gel�scht werden", 1);
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
			assertFalse(prevSize == postSize); //TODO: Hier wird eigentlich nur getestet, ob gel�scht wird. nicht was
		} catch (SQLException e) {
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
			testModel.insert_Posten("TestpostenCondition", "Allgemein", "-", 12, 2, "Testposten f�r delete mit Condition", 3);
			testModel.insert_Posten("TestpostenCondition", "Allgemein", "-", 12, 2, "Testposten f�r delete mit Condition", 3);
			testModel.insert_Posten("TestpostenNoCondition", "Allgemein", "-", 12, 2, "Testposten f�r delete mit Condition", 3);
			int prevSize = testModel.transcribe().size();
			testModel.deletePostenCondition("Bezeichnung", "TestpostenCondition");
			int postSize = testModel.transcribe().size();
			assertFalse(prevSize == postSize);
		} catch (SQLException e) {
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
			testModel.insert_Posten("TestpostenInt", "Allgemein", "-", 12, 2, "Testposten f�r delete mit Condition", 3);
			testModel.insert_Posten("TestpostenInt", "Allgemein", "-", 12, 2, "Testposten f�r delete mit Condition", 3);
			testModel.insert_Posten("TestpostenInt", "Allgemein", "-", 12, 2, "Testposten f�r delete mit Condition", 3);
			int prevSize = testModel.transcribe().size();
			testModel.deletePostenCondition("Kategorie", 1);
			int postSize = testModel.transcribe().size();
			assertFalse(prevSize == postSize);
		} catch (SQLException e) {
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
			testModel.insert_Posten("Testposten", 1, 1, 12, 2, "Testposten f�r transcribe", 3);
			assertNotNull(testModel.transcribe());
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Exception thrown");
		}
	}

}
