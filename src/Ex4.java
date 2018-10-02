import java.io.*;
import java.util.Scanner;
import java.lang.System;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.io.IOException;
import java.io.FileNotFoundException;

public final class Ex4 {
    private static final int MAXSIZE = 1000000;  // Max nr of words
    private static final int CUTOFF = 20;       // Cut-off for recursive quicksort

    public static void insertionSort (String[] w) {

    }



    public static void quickSort (String[] w) {

    }


    public static String[] readWords(String fileName) {
        String[] words = new String[MAXSIZE];
        int rowCount = 0;
        int wordCount = 0;
        try {
            BufferedReader myReader = new BufferedReader(new FileReader(fileName));
            String inputLine, thisLine;

            // Read lines until end of file
            while ((inputLine = myReader.readLine()) != null) {
                // Remove punctuation characters and convert to lower case
                //// Note: compound words will have the - removed !!!
                thisLine = inputLine.replaceAll("\\p{Punct}", "").toLowerCase();
                if (thisLine.length() !=0) {         // Skip empty lines
                    // Split the line into separate words on one or more whitespace
                    String[] w = thisLine.split("\\p{IsWhite_Space}+");
                    // Put the words in an array
                    for(String s:w){
                        if (!s.isEmpty()) words[wordCount++] = s;  // Skip empty words
                    }
                    rowCount++;
                }
            }
            System.out.println();
            System.out.println("Read " + rowCount + " rows and " + wordCount + " words");
            // Return the words in an array of of length wordCound
            return(java.util.Arrays.copyOfRange(words, 0, wordCount));
        }

        catch (IOException e) { // No file
            System.out.println("Error: " + e.getMessage());
            return (null);
        }
    }


    public static void writeWords(String [] words, String fileName) {
        BufferedWriter bw = null;
        try {
            File outputFile = new File(fileName);
            outputFile.createNewFile();        // Create the output file
            FileWriter fw = new FileWriter(outputFile);
            bw = new BufferedWriter(fw);
            for (String s:words) {       // Write the words to the file
                bw.write(s + " ");
            }
            System.out.println("Wrote file " + fileName);

        } catch (IOException e) {
            System.out.println ("No file " + fileName);
            System.exit(0);
        }
        finally {
            try {
                if (bw != null) bw.close();
            }
            catch (Exception ex) {
                System.out.println("Error in closing file " + fileName);
            }
        }
    }

    public static void main(String[] args) {
        // Check that a file name is given as an argument
        if (args.length != 1 ) {
            System.out.println("Usage: java Ex4 filename");
            System.exit(0);
        }

        // Read the words from the input file
        String[] words = readWords(args[0]);
        if (words == null) System.exit(0);     // Quit if file is not found

        System.out.println();
        System.out.println("Sorting with Insertion sort ");
        // Test the insertion sort method and measure how long it takes
        long startTime = System.nanoTime();
        insertionSort(words);
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Time for InsertionSort: " + estimatedTime/1000000000.0 + " seconds");

        // Write the result to a new file
        writeWords(words, args[0] + ".InsertionSort" );
        System.out.println();

        // Test the quicksort method and measure how long it takes
        System.out.println("Sorting with Quicksort ");
        startTime = System.nanoTime();
        quickSort(words);
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("Time for QuickSort: " + estimatedTime/1000000000.0 + " seconds");

        // Write the result to a new file
        writeWords(words, args[0] + ".QuickSort" );
        System.out.println();
        System.out.println();

    }
}
