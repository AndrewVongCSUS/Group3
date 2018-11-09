/*Arthur Kharit
 * CSC 131
 * Dr. Posnett
 * Individual Project
 */

package metrics;

//import IMetrics;//Same package, don't import

import java.io.*;


/* ------------------------------------------------------------------------------
 * Each Metrics object is made up of a FileCounts and SourceFileCounts class,
 * along with an AllCounts object. The AllCounts object stores all of the counts
 * for the File. Essentially, each Metrics object handles one file, and stores
 * all data for that file.
 * The AllCounts object is passed around between the FileCounts and SourceFileCounts
 * objects. Each one returns their respective updated AllCounts objects after
 * they've completed the necessary arithmetic.
 *
 * This class also implements IMetrics, and returns all the corresponding
 * data by pulling from the AllCounts object.
 */

public class Metrics implements IMetrics {
    private File file;
    private AllCounts counts = new AllCounts();
    private boolean isSource;

    Metrics(File file, WhatToPrint whatToPrint) throws IOException {
        FileCounts fileCounts = new FileCounts();
        counts = fileCounts.doEverything(file, counts);
        this.file = file;
        String extension = getExtension(file);

        if (fileIsSource(extension)){
            SourceFileCounts sourceFileCounts = new SourceFileCounts();
            counts = sourceFileCounts.doEverything(file, counts);
            if(whatToPrint.printHalstead) {
                HalsteadMeasures halstead = new HalsteadMeasures();
                counts = halstead.doEverything(file, counts);
            }
        }
    }

    AllCounts getCounts(){return counts;}

    protected String getFileName(){
        return file.getName();
    }

    // Gets file extension by cutting out everything after the period
    // of the file input and putting it into a string.
    private String getExtension(File file){
        String extension = null;
        String fileName = file.getName();
        isSource = false;
        // get file extension
        for (int i = fileName.length() - 1; i >= 0; i--) {
            if (fileName.charAt(i) == '.') {
                extension = fileName.substring(i);
                break;
            }
        }
        return extension;
    }

    private boolean fileIsSource(String extension){
        switch (extension) {
            case ".c" : case ".java" : case ".cpp" : case ".h" : case ".hpp" :{
                isSource = true;
                break;
            }
            default: {
                isSource = false;
                break;
            }
        }
        return isSource;
    }

    // ------------------------------------------------------------------------
    // IMetrics implementations

    // sets the file path of file, returns true if file is valid, false if not
    public boolean setPath(String path) {
        try{
            file = new File(path);
            BufferedReader test = new BufferedReader(new FileReader(file));
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
    // Returns true if file is a source file
    public boolean isSource(){ return       isSource; }

    // -------------------------------
    // basic counts for any file
    public int getLineCount()           {return counts.lineCount; }
    public int getWordCount()           {return counts.wordCount;}
    public int getCharacterCount()      {return counts.charCount;}

    // -------------------------------
    // source code line and comment counts
    public int getSourceLineCount()     {return counts.sourceCount;}
    public int getCommentLineCount()    {return counts.commentCount;}

    // -------------------------------
    // Halstead metrics
    public int getHalsteadn1()          {return counts.numDistinctOperators;}
    public int getHalsteadn2()          {return counts.numDistinctOperands;}
    public int getHalsteadN1()          {return counts.totalOperators;}
    public int getHalsteadN2()          {return counts.totalOperands;}
    public int getHalsteadVocabulary(){
        return (int)Math.round(counts.programVocab);
    }
    public int getHalsteadProgramLength() {
        return (int)Math.round(counts.programLength);
    }
    public int getHalsteadCalculatedProgramLenght() {
        return (int)Math.round(counts.calcProgramLength);
    }
    public int getHalsteadVolume()      {return (int)Math.round(counts.volume);}
    public int getHalsteadDifficulty()  {return (int)Math.round(counts.difficulty);}
    public int getHalsteadEffort()      {return (int)Math.round(counts.effort);}
    public int getHalsteadTime()        {return (int)Math.round(counts.timeToProgram);}
    public int getHalsteadBugs()        {return (int)Math.round(counts.numberOfBugs);}
}
