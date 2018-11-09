package metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* -----------------------------------------------------------------------------
 * Calculates all Halstead values. Has a List of operators that I specified,
 * which isn't the best way to do this but I'm not sure of a better one.
 * Decided not to use StreamTokenizer because it was too fragile.
 *
 * Has an AllCounts object passed to it when called by the SourceFileCounts
 * class, and returns the updated AllCounts object when done.
 *
 * NOTE: The way I count operators/operands is by no means perfect. The program
 *       misses some operators and operands. I'm not sure how to efficiently
 *       make this better. I didn't use StreamTokenizer because it is too
 *       fragile for my liking.
 */
class HalsteadMeasures {
    private AllCounts counts = new AllCounts();

    // not the best way, but I'm not sure how else to do this.
    private final String[] OPERATORS = new String[]
            {"for", "int ", "while", "private ", "public ", "static", "double", "boolean", "if", "switch",
                    "case", "void", "String ", "List", "return ", "class ", "new ", "static", "else", "try",
                    "catch", "protected", "throws", "import ", "extends", "system", "{", "}",
                    "=", "+", "-", "*", "/", ";", "||", "<", ">", ":", "!", "%", "++", "--", "&&",
                    "#include ", "printf", "scanf", "cout", "cin", "main", "throws "};


    AllCounts doEverything(File file, AllCounts c) throws IOException {
        counts = c;
        BufferedReader fileBuff = new BufferedReader(new FileReader(file));
        calculateOperatorsOperands(fileBuff);
        calcProgVocab();
        calcProgramLengths();
        calcVolume();
        calcDifficulty();
        calcVolume();
        calcEffort();
        calcTimeToWrite();
        calcNumberOfBugs();
        return counts;
    }

    // ---------------------------------------------------
    // Calculation methods
    private void calcProgVocab(){counts.programVocab = counts.numDistinctOperators + counts.numDistinctOperands;}
    private void calcVolume(){counts.volume = counts.programLength * (Math.log(counts.programVocab)/Math.log(2));}
    private void calcEffort()       {counts.effort = counts.difficulty * counts.volume;}
    private void calcTimeToWrite()  {counts.timeToProgram = counts.effort/18;}
    private void calcNumberOfBugs() {counts.numberOfBugs = counts.volume/3000;}
    private void calcProgramLengths(){
        counts.programLength = counts.totalOperators + counts.totalOperands;
        counts.calcProgramLength = counts.numDistinctOperators*(Math.log(counts.numDistinctOperators) /
                Math.log(2)) + counts.numDistinctOperands*(Math.log(counts.numDistinctOperands) / Math.log(2));
    }
    private void calcDifficulty() {
        counts.difficulty = (counts.numDistinctOperators / 2.0) +
                (double) (counts.totalOperands / counts.numDistinctOperands);
    }
    // ---------------------------------------------------


    // iterates through fileBuff line-by-line, ignoring comment lines/sections
    private void calculateOperatorsOperands(BufferedReader fileBuff) throws IOException {
        int numberOfOpFound;
        List<String> foundOperators = new ArrayList<>();
        List<String> foundOperands = new ArrayList<>();
        String currentLine;
        while ((currentLine = fileBuff.readLine()) != null) {
            currentLine = currentLine.trim();

            fileBuff = skipComments(fileBuff, currentLine);
            if (currentLine.length() == 0) { } // skip empty lines

            // counts operands/operators by checking a list of operators
            // and replacing it with a unique string of characters to allow
            // for easy splitting.
            else {
                for (String operatorToCheck : OPERATORS) {
                    numberOfOpFound = (currentLine.length() -
                            currentLine.replace(operatorToCheck, "").length()) / operatorToCheck.length();
                    if (numberOfOpFound > 0) {
                        currentLine = currentLine.replace(operatorToCheck, " _ "); // used this as it's unique
                        if (!(foundOperators.contains(operatorToCheck))) {
                            foundOperators.add(operatorToCheck);
                        }
                    }
                    counts.totalOperators += numberOfOpFound;
                }
                String[] leftOverLine = currentLine.split(" _ ");
                for (String operand : leftOverLine) {
                    if (!(foundOperands.contains(operand))) {
                        foundOperands.add(operand);
                    }
                }
                counts.totalOperands += leftOverLine.length;
            }
        }
        counts.numDistinctOperators += foundOperators.size();
        counts.numDistinctOperands += foundOperands.size();
    }

    // skips comments and comment sections as these aren't part of Halstead metrics
    private BufferedReader skipComments(BufferedReader fileBuff, String currentLine) throws IOException {
        while (currentLine.startsWith("/*")) { // comment section
            while (!currentLine.contains("*/")) {
                currentLine = fileBuff.readLine().trim();
            }
            currentLine = fileBuff.readLine().trim();
        }
        while (currentLine.startsWith("//")) { // comment line
            currentLine = fileBuff.readLine().trim();
        }
        return fileBuff;
    }
}

