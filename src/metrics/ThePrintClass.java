package com.metrics;

import java.text.DecimalFormat;

/* -----------------------------------------------------------------------------
 * This class does all of the printing. Prints the header upon initilization,
 * only printing the header if certain conditions are met, and only printing
 * a header if its corresponding count is to be printed.
 * Stores its own AllCounts object to make printing easier and require less
 * data passing.
 */
class ThePrintClass{
    private AllCounts countsToPrint;
    private boolean headerPrinted = false;

    // Prints the header if source exists OR if Halstead is requested
    public ThePrintClass(WhatToPrint whatToPrint, FileCountTotals fcTotals){
        if((whatToPrint.sourceFileExists && (whatToPrint.printSourceLines ||
                whatToPrint.printCommentLines || whatToPrint.printHalstead))){
            printHeader(whatToPrint, fcTotals);
        }
    }

    void printEverything(Metrics fileToProcess, WhatToPrint whatToPrint, FileCountTotals fcTotals){
        String fileName = fileToProcess.getFileName();
        countsToPrint = fileToProcess.getCounts();
        printCounts(whatToPrint, fileName, fcTotals);
    }

    // if there is a better way to do this please tell me
    // this entire function makes me cry, I tried to make it as neat as possible
    // the number in each math.max is the length of the corresponding headerTitle
    private void printCounts(WhatToPrint whatToPrint, String label, FileCountTotals fcTotals) {
        if(whatToPrint.printLines)
            printFormatted("lines", countToString(fcTotals.getTotals().lineCount),
                    countToString(countsToPrint.lineCount));
        if(whatToPrint.printWords)
            printFormatted("words", countToString(fcTotals.getTotals().wordCount),
                    countToString(countsToPrint.wordCount));
        if(whatToPrint.printChars)
            printFormatted("chars", countToString(fcTotals.getTotals().charCount),
                    countToString(countsToPrint.charCount));
        if(whatToPrint.sourceFileExists) {
            if (whatToPrint.printSourceLines)
                printFormatted("sLines", countToString(fcTotals.getTotals().sourceCount),
                        countToString(countsToPrint.sourceCount));
            if (whatToPrint.printCommentLines)
                printFormatted("cLines", countToString(fcTotals.getTotals().commentCount),
                        countToString(countsToPrint.commentCount));
            if (whatToPrint.printHalstead) {
                printFormatted("disOptor", countToString(fcTotals.getTotals().numDistinctOperators),
                        countToString(countsToPrint.numDistinctOperators));
                printFormatted("totOptor", countToString(fcTotals.getTotals().totalOperators),
                        countToString(countsToPrint.totalOperators));
                printFormatted("disOpand", countToString(fcTotals.getTotals().numDistinctOperands),
                        countToString(countsToPrint.numDistinctOperands));
                printFormatted("totOpand", countToString(fcTotals.getTotals().totalOperands),
                        countToString(countsToPrint.totalOperands));
                printFormatted("vocab", countToString(fcTotals.getTotals().programVocab),
                        countToString(countsToPrint.programVocab));
                printFormatted("length", countToString(fcTotals.getTotals().programLength),
                        countToString(countsToPrint.programLength));
                printFormatted("calcLength", countToString(fcTotals.getTotals().calcProgramLength),
                        countToString(countsToPrint.calcProgramLength));
                printFormatted("volume", countToString(fcTotals.getTotals().volume),
                        countToString(countsToPrint.volume));
                printFormatted("difficulty", countToString(fcTotals.getTotals().difficulty),
                        countToString(countsToPrint.difficulty));
                printFormatted("effort", countToString(fcTotals.getTotals().effort),
                        countToString(countsToPrint.effort));
                printFormatted("timeToProgram", countToString(fcTotals.getTotals().timeToProgram),
                        countToString(countsToPrint.timeToProgram));
                printFormatted("bugs", countToString(fcTotals.getTotals().numberOfBugs),
                        countToString(countsToPrint.numberOfBugs));
            }
        }
        System.out.println("  " + label);
    }

    void printTotals(FileCountTotals fcTotals, WhatToPrint whatToPrint){
        countsToPrint = fcTotals.getTotals();
        printCounts(whatToPrint, "totals", fcTotals);
    }

    // prints valueToPrint with spaces in front of it. The amount of spaces is
    // the greater length between header and total, + 2
    private void printFormatted(String header, String total, String valueToPrint){
        if(!headerPrinted){
            int spacesToPrint = String.valueOf(total).length() + 2;
            System.out.print(String.format("%" + spacesToPrint + "s", valueToPrint));
        }
        else{
            int spacesToPrint = Math.max(header.length() + 2,
                    String.valueOf(total).length() + 2);
            System.out.print(String.format("%" + spacesToPrint + "s", valueToPrint));
        }
    }
    private void printFormattedHeader(String header, String total){
        int spacesToPrint = Math.max(header.length() + 2,
                String.valueOf(total).length() + 2);
        System.out.print(String.format("%" + spacesToPrint + "s", header));
    }

    // converts int to string, or returns empty string if countToConvert = 0
    private String countToString(int countToConvert){
        if(countToConvert == 0){
            return "";
        }
        else{
            return (String.valueOf(countToConvert));
        }
    }
    // converts double to string, or returns empty string if countToConvert = 0
    private String countToString(double countToConvert){
        if(countToConvert == 0){
            return "";
        }
        else{
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            return (numberFormat.format(countToConvert));
        }
    }



    // Prints the column labels based on whatToPrint values
    // I know, I know... it's ugly... but it works
    // Math.max(lengthOfHeader + 2, lengthOfCorrespondingTotal + 2)
    private void printHeader(WhatToPrint whatToPrint, FileCountTotals fcTotals) {
        if (whatToPrint.printLines)
            printFormattedHeader("lines", countToString(fcTotals.getTotals().lineCount));
        if (whatToPrint.printWords)
            printFormattedHeader("words", countToString(fcTotals.getTotals().wordCount));
        if (whatToPrint.printChars)
            printFormattedHeader("chars", countToString(fcTotals.getTotals().charCount));
        if (whatToPrint.printSourceLines)
            printFormattedHeader("sLines", countToString(fcTotals.getTotals().sourceCount));
        if (whatToPrint.printCommentLines)
            printFormattedHeader("cLines", countToString(fcTotals.getTotals().commentCount));

        if (whatToPrint.printHalstead){
            printFormattedHeader("disOptor", countToString(fcTotals.getTotals().numDistinctOperators));
            printFormattedHeader("totOptor", countToString(fcTotals.getTotals().totalOperators));
            printFormattedHeader("disOpand", countToString(fcTotals.getTotals().numDistinctOperands));
            printFormattedHeader("totOpand", countToString(fcTotals.getTotals().totalOperands));
            printFormattedHeader("vocab", countToString(fcTotals.getTotals().programVocab));
            printFormattedHeader("length", countToString(fcTotals.getTotals().programLength));
            printFormattedHeader("calcLength", countToString(fcTotals.getTotals().calcProgramLength));
            printFormattedHeader("volume", countToString(fcTotals.getTotals().volume));
            printFormattedHeader("difficulty", countToString(fcTotals.getTotals().difficulty));
            printFormattedHeader("effort", countToString(fcTotals.getTotals().effort));
            printFormattedHeader("timeToProgram", countToString(fcTotals.getTotals().timeToProgram));
            printFormattedHeader("bugs", countToString(fcTotals.getTotals().numberOfBugs));
        }
        headerPrinted = true;
        System.out.println("");
    }
}

