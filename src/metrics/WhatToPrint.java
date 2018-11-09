package metrics;


import com.picocli.PicocliCMD;
import java.util.List;

/* -----------------------------------------------------------------------------
 * Manages the Boolean variables that determine what is printed at
 * the end of the program, based on what Picocli gets from input.
 */
class WhatToPrint{
    protected boolean printLines, printWords, printChars, printSourceLines, printCommentLines, printHalstead;
    protected boolean sourceFileExists = false;

    public WhatToPrint(){
        printLines = printWords = printChars = printSourceLines = printCommentLines = printHalstead = true;
    }

    protected void setValues(PicocliCMD pico){
        printLines = pico.toCountLines();
        printWords = pico.toCountWords();
        printChars = pico.toCountChars();
        printSourceLines = pico.toCountSource();
        printCommentLines = pico.toCountComments();
        printHalstead = pico.toCountHalstead();
    }

    // Checks if a source file exists and if counting source
    // lines and comments is required
    protected void toPrintSourceCounts(List<Metrics> listOfFileMetrics) {
        if (printSourceLines || printCommentLines || printHalstead) {
            for (Metrics currentFileMetric : listOfFileMetrics) {
                if (currentFileMetric.isSource()) {
                    sourceFileExists = true;
                    break;
                }
            }
        }
    }
}

