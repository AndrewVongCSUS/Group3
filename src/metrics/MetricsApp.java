/*Arthur Kharit
 * CSC 131
 * Dr. Posnett
 * Individual Project
 */

package metrics;

//import metrics.MetricsRun;//Same package, don't import
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/* User interface portion of the program. Calls the MetricsRun class,
 * which is where everything happens. The listOfIMetrics list contains
 * every single object that has been counted.
 */
public class MetricsApp {

    // in case printing would like to be done using the IMetrics
    // interface methods, set TO_PRINT_COUNTS to false
    public static boolean TO_PRINT_COUNTS = true;

    public static void main(String[] args) throws IOException {

        List<IMetrics> listOfIMetrics = new ArrayList<>();
        MetricsRun mRun = new MetricsRun();

        listOfIMetrics.addAll(mRun.run(args, TO_PRINT_COUNTS));

        // ------------------------------------------------------
        // This for loop shows how to access IMetrics.
        /*
        for(IMetrics currentIMetric : listOfIMetrics){
            System.out.println(currentIMetric.getCharacterCount());
        }
        */
    }
}
