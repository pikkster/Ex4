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
        int j;
        for (int p = 1; p < w.length; p++) {
            String tmp = w[p];
            for (j = p; j > 0 && tmp.compareTo(w[j-1]) < 0 ; j--) {
                w[j] = w[j-1];
            }
            w[j] = tmp;
        }
    }

    public static void quickSort (String[] w) {
        quicksort(w, 0, w.length-1);
    }

    private static Comparable median3 (String[] w,int left, int right) {
        int center = (left + right)/2;
        if (w[center].compareTo(w[left]) < 0)
            swapReferences(w,left, center);
        if (w[right].compareTo(w[left]) < 0)
            swapReferences(w,left, right);
        if (w[right].compareTo(w[center]) < 0)
            swapReferences(w, center, right);
        // Placera pivoten
        swapReferences(w,center, right-1);
        return w[right-1];
    }

    private static void quicksort(String[] w, int left, int right) {
        if ((left + CUTOFF) <= right) // Gör quicksort
        { Comparable pivot = median3(w, left, right);
            // Starta partitionering
            int i = left, j = right - 1;
            for ( ; ; )
            { while (w[++i].compareTo(String.valueOf(pivot)) < 0){}
                while (w[--j].compareTo(String.valueOf(pivot)) > 0){}
                if (i < j)
                    swapReferences(w, i, j);
                else
                    break;
            }
            swapReferences(w, i, right-1); // Återställ pivoten i mitten
            quicksort(w, left, i-1); // Sortera S1 rekursivt
            quicksort(w, i+1, right); // Sortera S2 rekursivt
        }
         else // Gör en insättningssortering på delräckan
            insertionSort(w);
    }

    private static void swapReferences (String[] w, int left, int right) {
        String temp = w[left];
        w[left] = w[right];
        w[right] = temp;
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
