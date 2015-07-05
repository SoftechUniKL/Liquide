/**
 * 
 */


import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Shahin
 *
 */
public class BudgetPlanModelTest {
	
	private Connection dbTestConnection;
	
	BudgetPlanModel testModel = new BudgetPlanModel();
	
	@Before
	public void setup()
	{
	   try {
		this.dbTestConnection = DriverManager.getConnection("jdbc:h2:mem:testCase", "sa", null);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		fail("SQL Exception thrown");
		e.printStackTrace();
	}
	}

	/**
	 * Test method for {@link BudgetPlanModel#BudgetPlanModel()}.
	 */
	@Test
	public void testBudgetPlanModel() {
		fail("Not yet implemented");
	}
	
	/**
	 * Test method for {@link BudgetPlanModel#setConnection(java.sql.Connection)}.
	 */
	@Test
	public void testSetConnection() {
		assertNull(testModel.getConnection());
		testModel.setConnection(dbTestConnection);
		assertEquals(testModel.getConnection(), dbTestConnection);
	}
	
	/**
	 * Test method for {@link BudgetPlanModel#getConnection()}.
	 */
	@Test
	public void testGetConnection() {
		testModel.setConnection(dbTestConnection);
		assertEquals(testModel.getConnection(), dbTestConnection);
		testModel.setConnection(null);
	}

	/**
	 * Test method for {@link BudgetPlanModel#closeConnection()}.
	 */
	@Test
	public void testCloseConnection() {
		testModel.setConnection(dbTestConnection);
		try {
			testModel.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("SQL Exception thrown");
		}
		try {
			assertTrue(testModel.getConnection().isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("SQL Exception thrown");
		}
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
				testModel.registerUser("test", "test");
				Method method = testModel.getClass().getDeclaredMethod("validateUser", String.class, String.class);
				method.setAccessible(true);
				method.invoke(testModel, "test","test"); //hier sollte keine Exception geworfen werden
				method.invoke(testModel, "existiertnicht", "existiertnicht"); //Hier sollte Exception geworfen werden.
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("ClassNotFoundException");
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("SQLException");
			} 
	}

	/**
	 * Test method for {@link BudgetPlanModel#initiateDatabase(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testInitiateDatabase() {
		try {
			testModel.registerUser("test", "test");
			testModel.initiateDatabase("test", "test");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
			testModel.registerUser("test", "test");
			testModel.initiateDatabase("test", "test");
			testModel.insert_Kategorie("TestKategorie");
			String[] testWerte = {"Allgemein", "TestKategorie"};
			String a[] = testModel.return_Kategorien();
			assertEquals(Arrays.toString(a), Arrays.toString(testWerte));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("SQLException");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("ClassNotFoundException");
		}
	}

	/**
	 * Test method for {@link BudgetPlanModel#insert_Subkategorie(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testInsert_Subkategorie() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#update_User(double)}.
	 */
	@Test
	public void testUpdate_User() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#insert_Posten(java.lang.String, java.lang.String, java.lang.String, double, int, java.lang.String, int)}.
	 */
	@Test
	public void testInsert_PostenStringStringStringDoubleIntStringInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#insert_Posten(java.lang.String, int, int, double, int, java.lang.String, int)}.
	 */
	@Test
	public void testInsert_PostenStringIntIntDoubleIntStringInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#return_Kategorien()}.
	 */
	@Test
	public void testReturn_Kategorien() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#return_Subkategorien(java.lang.String)}.
	 */
	@Test
	public void testReturn_Subkategorien() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#getBudget()}.
	 */
	@Test
	public void testGetBudget() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#DROP_ALL()}.
	 */
	@Test
	public void testDROP_ALL() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#deletePosten(int)}.
	 */
	@Test
	public void testDeletePosten() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#deleteKategorie(int)}.
	 */
	@Test
	public void testDeleteKategorieInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#deleteKategorie(java.lang.String)}.
	 */
	@Test
	public void testDeleteKategorieString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#deleteSubkategorie(int)}.
	 */
	@Test
	public void testDeleteSubkategorieInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#deleteSubkategorie(java.lang.String, int)}.
	 */
	@Test
	public void testDeleteSubkategorieStringInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#deletePostenCondition(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeletePostenConditionStringString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#deletePostenCondition(java.lang.String, int)}.
	 */
	@Test
	public void testDeletePostenConditionStringInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link BudgetPlanModel#transcribe()}.
	 */
	@Test
	public void testTranscribe() {
		fail("Not yet implemented");
	}

}
