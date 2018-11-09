package metrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*------------------------------------------------------------------------------
 * Calculates the values for a source file's number of sourceLines and
 * commentLines. Takes an AllCounts object, increments sourceLines and
 * commentLines counts, then returns the updated AllCounts object.
 * */
class SourceFileCounts{
    private AllCounts counts = new AllCounts();

    AllCounts doEverything(File file, AllCounts c) throws IOException {
        this.counts = c;
        processSourceFile(file);
        return counts;
    }

    /*
     * Method for processing SLOC. Ideas taken from Vu Nguyen, Sophia Deeds-Rubin, Thomas Tan,
     * and Barry Boehm in their paper, "A SLOC Counting Standard".
     *
     * I did not go as deep as they do in their article as that's out of the scope of this
     * program, however I learned a great deal about what is considered "source code".
     *
     * Goes through file and essentially counts everything but source code. So, counts
     * all comments and brackets and ignores empty spaces. Once all of that is done,
     * source lines are incremented.
     *
     * The paper can be found here:
     * http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.366.196&rep=rep1&type=pdf
     */
    private void processSourceFile(File file) throws IOException {
        int READ_AHEAD_LIMIT = 50000000;
        String currentLine;
        String slashes = "//";
        int numOfBracketLines = 0;
        boolean isCommentSection;

        BufferedReader fileBuff = new BufferedReader(new FileReader(file));
        while((currentLine = fileBuff.readLine()) != null){
            currentLine = currentLine.trim();
            if(currentLine.length() == 0){} // do nothing for empty lines
            else if(currentLine.startsWith("/*")) { // check for comment sections
                if (currentLine.contains("*/")) { // single line comment sections
                    counts.commentCount++;
                    isCommentSection = false;
                }
                else isCommentSection = true;
                while (isCommentSection) { // while lines within /* */ section
                    counts.commentCount++;

                    if ((currentLine = fileBuff.readLine()) == null)
                        break;
                    currentLine = currentLine.trim();
                    if (currentLine.contains("*/")) {
                        isCommentSection = false;
                        counts.commentCount++;
                    }
                    fileBuff.mark(READ_AHEAD_LIMIT);
                }
            }
            // checked for double slash comments, but it wasn't recognized in cpp
            // files for some reason. This works too.
            else if(currentLine.startsWith("/"))
                counts.commentCount++; // increment comment lines
            else if(currentLine.startsWith("}") || currentLine.startsWith("{")){
                if(currentLine.length() == 1)
                    numOfBracketLines++;
                else if(currentLine.contains(slashes))
                    counts.commentCount++;
            }
            else counts.sourceCount++;
        }
        double ratioOfBracketToSource = 0;
        if(counts.sourceCount++ != 0)
            ratioOfBracketToSource = (double)(counts.sourceCount + numOfBracketLines)/counts.sourceCount++;

        // if more than 40% of source lines of code is brackets, don't include them in count
        if(!(ratioOfBracketToSource > 0.4))
            counts.sourceCount += numOfBracketLines;
    }
}

