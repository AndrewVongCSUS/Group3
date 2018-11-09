package com.metrics;

// -----------------------------------------------------------------------------
// Stores all of the counts for a file.
class AllCounts{
    int lineCount, wordCount, charCount, numDistinctOperators, numDistinctOperands,
            totalOperators, totalOperands, sourceCount, commentCount;
    double programVocab, programLength, calcProgramLength, volume, difficulty,
            effort, timeToProgram, numberOfBugs;

    public AllCounts(){
        lineCount = wordCount = charCount = sourceCount = commentCount =
                numDistinctOperators = numDistinctOperands = totalOperators = totalOperands = 0;
        programVocab = programLength = calcProgramLength = volume = difficulty = effort =
                timeToProgram = numberOfBugs = 0.0;
    }
}

