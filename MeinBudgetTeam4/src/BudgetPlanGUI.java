import java.awt.Color;
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
	
	private JCheckBox chb31;
	
	private JRadioButton rb41, rb42, rb43, rb44 ;
	
	private JFrame w1, w2, w3, w4, w5;
	
	private JComboBox cb31, cb32, cb41, cb42;
	/**
	 * Scrollelemente, das die Tabelle umfasst
	 */
	private JScrollPane sp21;
	
	private JPanel p21;
	/**
	 * Schaltflaeche, die beim Klicken einen Dialog anzeigt
	 */
	private JButton b01, b02, b11, b12, b21, b22, b23, b24, b25, b31, b32, b41, b42;
	/**
	 * Modell der Daten
	 */
	private JLabel l01, l02, l11,l12, l13, l21, l22, l31, l32, l33, l34, l35, l41, l42 ;
	
	private JTextField eName, nName, tf31, tf32, tf33 ;
	
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
		b01 = new JButton("Login");
	    l01 = new JLabel ("Name") ;
	    l02 = new JLabel("Passwort");
		eName = new JTextField() ;
		ePw = new JPasswordField();
		b02 = new JButton("Registrierung") ;
		
		b02.setBounds(175,250,150,30);
		ePw.setBounds(300,100,100,20);
		l02.setBounds(300,80,100,20);
		l01.setBounds(100,80,100,20);
		eName.setBounds(100,100,100,20);
		b01.setBounds(175,200,150,30);
		
		getContentPane().add(b02);
		getContentPane().add(b01) ;
		getContentPane().add(eName) ;
		getContentPane().add(l01) ;
		getContentPane().add(l02);
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
		l11 = new JLabel ("Benutzername") ;
		nName = new JTextField();
		l12 = new JLabel ("neues Passwort");
		nPw1 = new JPasswordField();
		l13 = new JLabel (" Passwort wiederholen");
		nPw2 = new JPasswordField();
		b11 = new JButton("registrieren") ;
		b12 = new JButton("zurück") ;
		
		
		
		w1.setBounds(10,10,500,500) ;
		w1.setVisible(true);
		l11.setBounds(100,80,100,20);
		nName.setBounds(100,100,100,20);
		l12.setBounds(300,80,100,20);
		nPw1.setBounds(300,100,100,20);
		l13.setBounds(300,140,150,20);
		nPw2.setBounds(300,160,100,20);
		b11.setBounds(175,200,150,30);
		b12.setBounds(400,400,75,20);
		
		w1.getContentPane().setLayout(null);
	    w1.getContentPane().add(l11);
		w1.getContentPane().add(nName);
		w1.getContentPane().add(l12);
		w1.getContentPane().add(nPw1);
		w1.getContentPane().add(l13);
		w1.getContentPane().add(nPw2);
		w1.getContentPane().add(b11) ;
		w1.getContentPane().add(b12);
		
		b11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(w1,
						"Ihre Registrierung ist erfolgreich abgeschlossen!",
						"Vielen Dank", JOptionPane.PLAIN_MESSAGE);
				w1.dispose();
			}
		});
		b12.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				w1.dispose(); 
		
				
			}
			
		});
		
		
	}
	protected void initWindow2 (){
		w2 = new JFrame ();
		b21 = new JButton("neuer Posten");
		b22 = new JButton("Ausgaben") ;
		b23 = new JButton("Budgetverwaltung") ;
		b24 = new JButton("Datenverwaltung") ;
		b25 = new JButton("Optionen") ;
		l21 = new JLabel("aktuelles Budget");
		l22 = new JLabel("Letzte Posten");
		sp21 = new JScrollPane(); // letzte Posten
		p21 = new JPanel(); // aktuelles Budget
		
		
		w2.setBounds(10,10,500,500);
		w2.setVisible(true);
		b21.setBounds(50,50,150,30);
		b22.setBounds(50,100,150,30);
		b23.setBounds(50,150,150,30);
		b24.setBounds(50,200,150,30);
		b25.setBounds(50,250,150,30);
		sp21.setBounds(250,200,200,250);
		sp21.setBackground(Color.white);
		l21.setBounds(250,50,150,20);
		l22.setBounds(250,180,150,20);
		p21.setBounds(250,70,150,20);
		p21.setBackground(Color.white);
		
		w2.getContentPane().setLayout(null);
		w2.getContentPane().add(b21);
		w2.getContentPane().add(b22);
		w2.getContentPane().add(b23) ;
		w2.getContentPane().add(b24) ;
		w2.getContentPane().add(b25) ;
		w2.setTitle("Hauptmenü");
		w2.getContentPane().add(sp21);
		w2.getContentPane().add(l21);
		w2.getContentPane().add(l22);
		w2.getContentPane().add(p21);
		
		b21.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				w2.dispose();
				initWindow3();
			}
		});
		b22.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				w2.dispose();
				initWindow4();
			}
		});
	}
	protected void initWindow3 (){
		w3 = new JFrame();
		cb31 = new JComboBox();
	    cb32 = new JComboBox();
		b31 = new JButton("übernehmen");
		b32 = new JButton("zurück");
		l31 = new JLabel("Kategorie");
		l32 = new JLabel("Subkategorie");
		l33 = new JLabel("Posten");
		l34 = new JLabel("Preis");
		l35 = new JLabel("Anzahl");
		tf31 = new JTextField();
		tf32 = new JTextField();
		tf33 = new JTextField();
		chb31 = new JCheckBox("Dauerauftrag");
		
		
		w3.setBounds(10,10,500,500);
		w3.setVisible(true);
		cb31.setBounds(50,70,150,20);
		cb32.setBounds(50,140,150,20);
		b31.setBounds(50,400,150,20);
		b32.setBounds(300,400,150,20);
		l31.setBounds(50,50,150,20);
		l32.setBounds(50,120,150,20);
		l33.setBounds(300,50,150,20);
		l34.setBounds(300,120,150,20);
		l35.setBounds(300,190,150,20);
		tf31.setBounds(300,70,100,20);
		tf32.setBounds(300,140,100,20);
		tf33.setBounds(300,210,100,20);
		chb31.setBounds(50,190,150,20);
		
		w3.setTitle("Neuer Posten") ;
		w3.getContentPane().setLayout(null);
		w3.getContentPane().add(cb31);
		w3.getContentPane().add(cb32);
		w3.getContentPane().add(l31);
		w3.getContentPane().add(b31);
		w3.getContentPane().add(b32);
		w3.getContentPane().add(l32);
		w3.getContentPane().add(l33);
		w3.getContentPane().add(l34);
		w3.getContentPane().add(l35);
		w3.getContentPane().add(tf31);
		w3.getContentPane().add(tf32);
		w3.getContentPane().add(tf33);
		w3.getContentPane().add(chb31);
		
		b31.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				w3.dispose(); 
				initWindow3();
			}		
		});
		b32.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				w3.dispose(); 
				initWindow2();
			}		
		});
		
		
	}
	protected void initWindow4() {
		w4 = new JFrame("Ausgaben");
		l41 = new JLabel("Jahr") ;
		l42 = new JLabel("Monat") ;
		cb41 = new JComboBox();
		cb42 = new JComboBox();
		b41 = new JButton("visualisieren");
		b42 = new JButton("zurück");
		rb41 = new JRadioButton("Tabellenform");
		rb42 = new JRadioButton("Pie-Chart");
		rb43 = new JRadioButton("weitere Visualisierung1"); 
		rb44 = new JRadioButton("weitere Visualisierung2");
		ButtonGroup bg41 = new ButtonGroup ();
		
		
		w4.setBounds(10,10,500,500);
		w4.setVisible(true);
		l41.setBounds(50,50,100,20);
		l42.setBounds(300,50,100,20);
		cb41.setBounds(50,70,100,20);
		cb42.setBounds(300,70,100,20);
		b41.setBounds(50,400,150,20);
		b42.setBounds(300,400,150,20);
		rb41.setBounds(50,150,150,20);
		rb42.setBounds(50,200,150,20);
		rb43.setBounds(50,250,150,20);
		rb44.setBounds(50,300,150,20);
		bg41.add(rb41);
		bg41.add(rb42);
		bg41.add(rb43);
		bg41.add(rb44);
		
		w4.getContentPane().setLayout(null);
		w4.getContentPane().add(l41) ;
		w4.getContentPane().add(l42) ;
		w4.getContentPane().add(cb41) ;
		w4.getContentPane().add(cb42) ;
		w4.getContentPane().add(b41) ;
		w4.getContentPane().add(b42) ;
		w4.getContentPane().add(rb41);
		w4.getContentPane().add(rb42);
		w4.getContentPane().add(rb43);
		w4.getContentPane().add(rb44);
		
		b41.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				initWindow5();
			}		
		});
		b42.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				w4.dispose(); 
				initWindow2();
			}		
		});
	
	}
	protected void initWindow5(){ // Fenster mit Visualisierung zum austoben
		w5 = new JFrame("Visualisierung");
		
		w5.setBounds(10,10,500,500);
		w5.setVisible(true);
		
		w5.getContentPane().setLayout(null);
		
	}

	// Verhalten hinzufuegen
	public void addBehavior() {
		// registriere den ActionListener fuer den Button als anonyme Klasse
		b01.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				initWindow2();
			}

		});
		b02.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				initWindow1(); // Initialisierung des Frameinhalts
		
				
			}
			
		});
		
	}

}