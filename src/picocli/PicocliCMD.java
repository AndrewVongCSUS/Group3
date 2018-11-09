package com.picocli;

import java.io.File;
import java.util.List;

public class PicocliCMD{
    @CommandLine.Option(names = {"-l", "--lines"}, description = "print the line count of a file")
    private boolean countLines = false;
    @CommandLine.Option(names = {"-w", "--words"}, description = "print the word count of a file")
    private boolean countWords = false;
    @CommandLine.Option(names = {"-c", "--characters"}, description = "print the character count of a file")
    private boolean countChars = false;
    @CommandLine.Option(names ={"-s", "--sourcelines"}, description = "print the number of source lines of a file")
    private boolean countSourceLines = false;
    @CommandLine.Option(names = {"-C", "--commentlines"}, description = "print the number of comment lines of a file")
    private boolean countCommentLines = false;
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this message")
    private boolean displayHelp = false;
    @CommandLine.Option(names = {"-H"}, description = "calculate complexity using Halstead Measures")
    private boolean displayHalstead = false;
    @CommandLine.Parameters(paramLabel = "FILE", description="file(s) to be counted")
    private List<File> files;

    public boolean toDisplayHelp(){ return displayHelp; }

    public List<File> getFilesList(){ return files; }

    public boolean toCountLines(){ return countLines; }
    public boolean toCountWords(){ return countWords; }
    public boolean toCountChars(){ return countChars; }
    public boolean toCountSource(){return countSourceLines; }
    public boolean toCountComments(){return countCommentLines; }
    public boolean toCountHalstead(){return displayHalstead;}

}
