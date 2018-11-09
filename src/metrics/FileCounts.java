package metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/* ------------------------------------------------------------------------------
 * Each FileCounts object contains an AllCounts object that is passed to it from
 * the FileProcessor class.
 * The FileCounts class only counts the 3 basic counts: lines, words, characters.
 */
class FileCounts{
    private AllCounts counts = new AllCounts();

    AllCounts doEverything(File file, AllCounts c) throws IOException {
        counts = c;
        BufferedReader fileBuff = new BufferedReader(new FileReader(file));
        calcCounts(fileBuff);
        return counts;
    }


    // Goes through file character by character and increments the necessary counts
    private void calcCounts(BufferedReader fileBuff) throws IOException {
        int READ_AHEAD_LIMIT = 50000000;
        String currentLine = null;
        String[] words = null;
        fileBuff.mark(READ_AHEAD_LIMIT);
        while ((currentLine = fileBuff.readLine()) != null){
            counts.lineCount++;
            words = currentLine.split("\\s+");
            counts.wordCount += words.length; // words
            // loop to deal with occasional empty space in words array after split
            for (String word : words) {
                if (word.length() == 0)
                    counts.wordCount--;
            }
        }
        fileBuff.reset();
        fileBuff.mark(READ_AHEAD_LIMIT);
        while (fileBuff.read() != -1)
            counts.charCount++;
        fileBuff.reset();
    }
}

