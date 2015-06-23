
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart extends ApplicationFrame
{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public LineChart( String applicationTitle , String chartTitle )
   {
      super(applicationTitle);
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Monat","Einkommen",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset( )
   {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
      dataset.addValue( 15 , "einkommen" , "Januar" );
      dataset.addValue( 30 , "einkommen" , "februar" );
      dataset.addValue( 60 , "einkommen" ,  "März" );
      dataset.addValue( 120 , "einkommen" , "April" );
      dataset.addValue( 240 , "einkommen" , "Mai" );
      dataset.addValue( 250 , "einkommen" , "Juni" );
      dataset.addValue( 300 , "einkommen" , "Juli" );
      dataset.addValue( 400 , "einkommen" , "August" );
      dataset.addValue( 300 , "einkommen" , "September" );
      dataset.addValue( 450 , "einkommen" , "Oktober" );
      dataset.addValue( 350 , "einkommen" , "November" );
      dataset.addValue( 300 , "einkommen" , "Dezember" );
      return dataset;
   }
   public static void main( String[ ] args ) 
   {
      LineChart chart = new LineChart(
      "Liniendiagramm" ,
      "Einkommen pro Monat");

      chart.pack( );
      RefineryUtilities.centerFrameOnScreen( chart );
      chart.setVisible( true );
   }
}
