package metrics;

import java.util.List;

/* -----------------------------------------------------------------------------
 * This class deals with anything related to the total counts of the files that
 * are displayed after everything else. Stores its own AllCounts object and
 * simply uses the variables in that class to stores the corresponding totals.
 */
class FileCountTotals{
    private AllCounts totalCounts = new AllCounts();

    public FileCountTotals(List<Metrics> listOfFileMetrics) {
        super();
        calcTotals(listOfFileMetrics);
    }

    private void calcTotals(List<Metrics> listOfFileMetrics){
        for (Metrics currentFileMetric : listOfFileMetrics) {
            totalCounts.lineCount += currentFileMetric.getCounts().lineCount;
            totalCounts.wordCount += currentFileMetric.getCounts().wordCount;
            totalCounts.charCount += currentFileMetric.getCounts().charCount;
            totalCounts.sourceCount += currentFileMetric.getCounts().sourceCount;
            totalCounts.commentCount += currentFileMetric.getCounts().commentCount;
            totalCounts.totalOperators += currentFileMetric.getCounts().totalOperators;
            totalCounts.totalOperands += currentFileMetric.getCounts().totalOperands;
            totalCounts.numDistinctOperators += currentFileMetric.getCounts().numDistinctOperators;
            totalCounts.numDistinctOperands += currentFileMetric.getCounts().numDistinctOperands;
            totalCounts.programVocab += currentFileMetric.getCounts().programVocab;
            totalCounts.programLength += currentFileMetric.getCounts().programLength;
            totalCounts.calcProgramLength += currentFileMetric.getCounts().calcProgramLength;
            totalCounts.volume += currentFileMetric.getCounts().volume;
            totalCounts.difficulty += currentFileMetric.getCounts().difficulty;
            totalCounts.effort += currentFileMetric.getCounts().effort;
            totalCounts.timeToProgram += currentFileMetric.getCounts().timeToProgram;
            totalCounts.numberOfBugs += currentFileMetric.getCounts().numberOfBugs;
        }
    }

    AllCounts getTotals(){return totalCounts;}


}