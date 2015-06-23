

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.Rotation;

public class PieChart3D extends ApplicationFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PieChart3D(final String title) {

        super(title);
        final PieDataset dataset = createSampleDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
	
	   private PieDataset createSampleDataset() {
	        
	        final DefaultPieDataset result = new DefaultPieDataset();
	        result.setValue("Einkommen", new Double(43.2));
	        result.setValue("Variable Kosten", new Double(10.0));
	        result.setValue("Fixe Kosten", new Double(17.5));
	        result.setValue("Essen", new Double(32.5));
	        result.setValue("Andere Ausgaben", new Double(1.0));
	        return result;
	        
	   }
	   
	   
	   private JFreeChart createChart(final PieDataset dataset) {
	        
	        final JFreeChart chart = ChartFactory.createPieChart3D(
	            "Piechart Budget",  // chart title
	            dataset,                // data
	            true,                   // include legend
	            true,
	            false
	        );

	        final PiePlot3D plot = (PiePlot3D) chart.getPlot();
	        plot.setStartAngle(290);
	        plot.setDirection(Rotation.CLOCKWISE);
	        plot.setForegroundAlpha(0.5f);
	        plot.setNoDataMessage("No data to display");
	        plot.setLabelGenerator(null);
	        return chart;
	        
	    }
	   
	   public static void main(final String[] args) {

	        final PieChart3D myClass = new PieChart3D("Pie Chart ");
	        myClass.pack();
	        RefineryUtilities.centerFrameOnScreen(myClass);
	        myClass.setVisible(true);

	    }

	}
