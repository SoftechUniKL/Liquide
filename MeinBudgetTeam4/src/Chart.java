import javax.swing.JFrame;
import javax.swing.JTabbedPane;
 
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
 
public class Chart extends JFrame
{

	private static final long serialVersionUID = 1L;

	public Chart()
    {
        // Titel des Frames
        setTitle("Budgetplaner - Charts");
 
        // Pie Chart Daten für Verarbeitung
        DefaultPieDataset pieDataset = getPieDataset();
 
        // Balken Chart Daten für Verarbeitung
        DefaultCategoryDataset barDataset = getBarDataset();
 
        
        // 3D PieChart
        JFreeChart pieChart3D = ChartFactory.createPieChart3D(
                "3D Pie", pieDataset, true, true, true);
 
        // Balken Chart
        JFreeChart barChartVertical3D = ChartFactory.createBarChart3D(
                "Balkendiagramm", "Monate", "Ausgaben", barDataset,
                PlotOrientation.VERTICAL, true, true, true);


        // ChartPanel mit dem vorhandenen 3D Tortendiagramm erstellen
        ChartPanel pieChartPanel3D = new ChartPanel(pieChart3D);
 
        // ChartPanel mit dem vorhandenen vertikalen 3D Balkendiagramm erstellen
        ChartPanel barChartVerticalPanel3D = new ChartPanel(barChartVertical3D);

 
        // Registerkarten erstellen
        JTabbedPane tabber = new JTabbedPane();
 
        // 2.Registerkarte: 3D Tortendigramm
        tabber.add("PieChart3D", pieChartPanel3D);
 
        // 3.Registerkarte: 3D vertikales Balkendiagramm
        tabber.add("BarChart3D - Vertikal", barChartVerticalPanel3D);
 
        // Registerkarten dem JFrame übergeben
        add(tabber);
 
        // Java Anwendung beim schließen beenden
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Applikation sichtbar schalten
        setVisible(true);
        // automatische Größenanpassung
        pack();
    }
 
    //Daten für BalkenChart
    private DefaultCategoryDataset getBarDataset()
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(461, "Januar", "");
        dataset.addValue(591, "Februar", "");
        dataset.addValue(274, "März", "");
        dataset.addValue(591, "April", "");
        dataset.addValue(541, "Mai", "");
        dataset.addValue(344, "Juni", "");
        dataset.addValue(591, "Juli", "");
        dataset.addValue(344, "August", "");
        dataset.addValue(591, "September", "");
        dataset.addValue(119, "Oktober", "");
        dataset.addValue(183, "November", "");
        dataset.addValue(175, "Dezember", "");
 
        return dataset;
    }
 
    //Daten für PieChart
    private DefaultPieDataset getPieDataset()
    {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Einkommen", 32);
        dataset.setValue("TestKosten", 12);
        dataset.setValue("Fixe Kosten", 10);
        dataset.setValue("Lebensmittel", 8);
        dataset.setValue("Sonstiges", 1);
 
        return dataset;
    }
 
    public static void main(String[] args)
    {
        // Frame erstellen
        new Chart();
    }
}
