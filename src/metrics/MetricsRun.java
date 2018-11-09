package metrics;

import com.picocli.PicocliCMD;
import com.picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/* ------------------------------------------------------------------------------
 * Handles the input, and calls the Metrics class to do all of the counting.
 * Stores a List of Metrics objects, then returns it to the MetricsApp main
 * method.
 */
public class MetricsRun{
    public List<Metrics> run(String[] args, boolean toPrintCounts) throws IOException {
        List<Metrics> listOfFileMetrics = new ArrayList<>();
        PicocliCMD pico = new PicocliCMD();

        // args.length == 0 -> nothing was inputted
        if (args.length == 0 || pico.toDisplayHelp()) {
            CommandLine.usage(pico, System.out);
            System.exit(0);
        }

        WhatToPrint whatToPrint = new WhatToPrint();

        new CommandLine(pico).parse(args);
        List<File> files = pico.getFilesList();

        // when options given, set whatToPrint values to picocli values, otherwise all stay true
        if (args[0].charAt(0) == '-')
            whatToPrint.setValues(pico);

        files = checkForWildCards(files); // because java doesn't do it for me :(

        // create Metrics object which counts the current file, then adds it to a list
        for (File file : files) {
            Metrics fileToProcess = new Metrics(file, whatToPrint);
            listOfFileMetrics.add(fileToProcess);
        }

        whatToPrint.toPrintSourceCounts(listOfFileMetrics);
        FileCountTotals fcTotals = new FileCountTotals(listOfFileMetrics);


        if(toPrintCounts) {
            ThePrintClass print = new ThePrintClass(whatToPrint, fcTotals);
            // prints each file's Metrics
            for (Metrics fileToPrint : listOfFileMetrics) {
                print.printEverything(fileToPrint, whatToPrint, fcTotals);
            }

            // prints totals only if there is more than 1 file that printed
            if (files.size() > 1)
                print.printTotals(fcTotals, whatToPrint);
        }
        return listOfFileMetrics;
    }

    /* Checks input for wildcards, the * character.
     * Found the DirectoryStream solution online, link below.
     * https://stackoverflow.com/a/31685610
     */
    private List<File> checkForWildCards(List<File> files) {
        int initialListSize = files.size();
        for (int i = 0; i < initialListSize; i++) {
            File file = files.get(i);
            String dir = "/";
            if (file.getParent() != null)
                dir = file.getParent(); // directory of input

            // Checks entire parent directory of file for files that meet wildcard
            if (file.getName().contains("*")) {
                try (DirectoryStream<Path> dirStream =
                             Files.newDirectoryStream(Paths.get(dir), file.getName())) {
                    files.remove(i);
                    i--; // because of remove() operation
                    for (Path p : dirStream) {
                        File temp = new File(p.toString());
                        files.add(temp);
                    }
                } catch (Exception e) {
                    System.out.println("Some error occurred in checkForWildCards");
                }
            }
        }

        // if wildcards are entered but aren't valid, the list is empty
        if (files.size() == 0) {
            System.out.println("No files matching that wildcard");
            System.exit(0);
        }
        files = removeDuplicates(files);

        return files;
    }

    // My system for checking for wildcards will cause duplicates in the List of
    // Files if there are more than one similar wildcard inputted. IE: essay*.txt ess*.txt
    private List<File> removeDuplicates(List<File> files) {
        int i = 0;
        int j = 1;
        while(i < files.size()) {
            while(j < files.size()) {
                if (files.get(i).getName().equals(files.get(j).getName())) {
                    files.remove(j);
                }
                j++;
            }
            i++;
            j = i + 1;
        }
        return files;
    }
}

