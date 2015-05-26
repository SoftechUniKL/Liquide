import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Graphische Benutzeroberflaeche des BudgetPlaners
 * 
 */
public class BudgetPlanGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	/**
	 * Tabelle mit Uebersicht der Ausgaben
	 */
	private JTable table;
	
	private JFrame w1;
	/**
	 * Scrollelemente, das die Tabelle umfasst
	 */
	private JScrollPane scrollpane;
	/**
	 * Schaltflaeche, die beim Klicken einen Dialog anzeigt
	 */
	private JButton login, registry, register,back1;
	/**
	 * Modell der Daten
	 */
	private JLabel name,pw,nNamel,nPw1l, nPw2l ;
	
	private JTextField eName, nName ;
	
	private JPasswordField ePw, nPw1, nPw2 ;
	
	private BudgetPlanModel budget;

	/**
	 * Konstruktor fuer die GUI.
	 * 
	 * Hier wird das Hauptfenster initialisiert und aktiviert.
	 * 
	 * @param budget
	 *            Modell der Daten
	 */
	public BudgetPlanGUI(BudgetPlanModel budget) {
		super("Loginfenster");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		this.budget = budget;
		initWindow(); // Initialisierung des Frameinhalts
		addBehavior(); // Verhalten der GUI Elemente dieses Frames
		setBounds(10, 10, 500, 500); // Groesse des Frames
		setVisible(true); // Frame wird sichtbar
	}

	// Initialisieren des Fensters
	protected void initWindow() {
		login = new JButton("Login");
	    name = new JLabel ("Name") ;
	    pw = new JLabel("Passwort");
		eName = new JTextField() ;
		ePw = new JPasswordField();
		registry = new JButton("Registrierung") ;
		
		registry.setBounds(175,250,150,30);
		ePw.setBounds(300,100,100,20);
		pw.setBounds(300,80,100,20);
		name.setBounds(100,80,100,20);
		eName.setBounds(100,100,100,20);
		login.setBounds(175,200,150,30);
		
		getContentPane().add(registry);
		getContentPane().add(login) ;
		getContentPane().add(eName) ;
		getContentPane().add(name) ;
		getContentPane().add(pw);
		getContentPane().add(ePw);
		

		/*// Tabelle mit Uebersicht der Ausgaben
		Object[][] data = new Object[budget.ausgaben.size()][3];
		int i = 0;
		for (Posten p : budget.ausgaben) {
			data[i][0] = new SimpleDateFormat("dd/MM/yyyy")
					.format(p.getDatum());
			data[i][1] = p.getBezeichnung();
			data[i][2] = String.format("%.2f", p.getBetrag());
			i++;
		}

		table = new JTable(data, new Object[] { "Datum", "Bezeichnung",
				"Betrag" });
		scrollpane = new JScrollPane(table);

		// Kreisdiagramm
		DefaultPieDataset pd = new DefaultPieDataset();
		for (Posten p : budget.ausgaben) {
			pd.setValue(p.getBezeichnung(), p.getBetrag());
		}
		JFreeChart pie = ChartFactory.createPieChart("Ausgaben", pd);
		ChartPanel panel = new ChartPanel(pie);

		// Button
		button = new JButton("TestButton!");

		// Elemente dem Fenster hinzufuegen:
		getContentPane().add(scrollpane);
		getContentPane().add(panel);
		getContentPane().add(button);
		// Berechnet Layout mit geringstem Platzbedarf
		pack();*/
	}
	protected void initWindow1() {
		w1 = new JFrame();
		nNamel = new JLabel ("Benutzername") ;
		nName = new JTextField();
		nPw1l = new JLabel ("neues Passwort");
		nPw1 = new JPasswordField();
		nPw2l = new JLabel (" Passwort wiederholen");
		nPw2 = new JPasswordField();
		register = new JButton("registrieren") ;
		back1 = new JButton("zurück") ;
		
		
		w1.setBounds(10,10,500,500) ;
		w1.setVisible(true);
		nNamel.setBounds(100,80,100,20);
		nName.setBounds(100,100,100,20);
		nPw1l.setBounds(300,80,100,20);
		nPw1.setBounds(300,100,100,20);
		nPw2l.setBounds(300,140,150,20);
		nPw2.setBounds(300,160,100,20);
		register.setBounds(175,200,150,30);
		back1.setBounds(400,400,75,20);
		
		w1.getContentPane().setLayout(null);
	    w1.getContentPane().add(nNamel);
		w1.getContentPane().add(nName);
		w1.getContentPane().add(nPw1l);
		w1.getContentPane().add(nPw1);
		w1.getContentPane().add(nPw2l);
		w1.getContentPane().add(nPw2);
		w1.getContentPane().add(register) ;
		w1.getContentPane().add(back1);
		
		register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(w1,
						"Ihre Registrierung ist erfolgreich abgeschlossen!",
						"Vielen Dank", JOptionPane.PLAIN_MESSAGE);
				w1.dispose();
			}
		});
		back1.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				w1.dispose(); 
		
				
			}
			
		});
		
		
	}

	// Verhalten hinzufuegen
	public void addBehavior() {
		// registriere den ActionListener fuer den Button als anonyme Klasse
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(BudgetPlanGUI.this,
						"Sie sollten Ihre Finanzplanung ueberdenken!",
						"Hinweis", JOptionPane.PLAIN_MESSAGE);
			}

		});
		registry.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				initWindow1(); // Initialisierung des Frameinhalts
		
				
			}
			
		});
		
	}

}