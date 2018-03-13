package parallel_project1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Steven Hogenson
 */
public class COSC444_P1 extends RecursiveAction {

    private static int[][] imgArr;//2D array of RGB int values
    private static BufferedImage img;//Image that user inputs to find Waldo and Co.
    int threadNumber;

    /**
     * @param threadNum
     */
    public COSC444_P1(int threadNum) {
        threadNumber = threadNum;

    }

    public static void main(String[] args) {
        long startTot = System.currentTimeMillis();//Total Execution Time start
        Scanner sc = new Scanner(System.in);//Scanner to get image file name from user
        //System.out.print("What is the image you are searching through? (Enter the complete path to the image)\n> ");
        //String fileName = sc.nextLine();
        File origImage = new File("C:\\Users\\Steven\\Desktop\\sample.png");
        //File origImage = new File(fileName);//accepts file
        long startEx = System.currentTimeMillis();//Execution Time start (excludes time when user is typing file name)
        img = null;//instantiates BufferedImage to null

        try {
            img = ImageIO.read(origImage);//creates a BufferedImage from the file that was accepted
            imgArr = convertToArray(img);//Creates a 2D array of RGB ints from the BufferedImage

            COSC444_P1 findWaldo = new COSC444_P1(1);//Creates task to run findWaldo() method via compute() method
            COSC444_P1 findWilma = new COSC444_P1(2);//Creates task to run findWilma() method via compute() method
            COSC444_P1 findOdlaw = new COSC444_P1(3);//Creates task to run findOdlaw() method via compute() method
            COSC444_P1 findWizard = new COSC444_P1(4);//Creates task to run findWizard() method via compute() method
            COSC444_P1 findWoof = new COSC444_P1(5);//Creates task to run findWoof() method via compute() method

            long startPar = System.currentTimeMillis();//Parallel Execution Time start
            invokeAll(findWaldo, findWilma, findOdlaw, findWizard, findWoof);//Forks the 5 tasks (1 for each character) and returns when isDone is true for each task
            long endPar = System.currentTimeMillis();//Parallel Execution Time end
            System.out.println("\nParallel Execution Time: " + (endPar - startPar) + " milliseconds");
            System.out.println("Execution Time (not counting user input time): " + (endPar - startEx) + " milliseconds");
            System.out.println("Total Execution Time: " + (endPar - startTot) + " milliseconds");
        } catch (IOException ex) {
            Logger.getLogger(Parallel_Project1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * invokes 5 threads that each perform one of the findX() methods.
     */
    @Override
    protected synchronized void compute() {

        if (threadNumber == 1) {
            findWaldo();
        }
        if (threadNumber == 2) {
            findWilma();
        }
        if (threadNumber == 3) {
            findOdlaw();
        }
        if (threadNumber == 4) {
            findWizard();
        }
        if (threadNumber == 5) {
            findWoof();
        }

    }

    /**
     * Converts the pixels of a desired BufferedImage into a 2D integer array of
     * RGB values
     *
     * @param image the image file that the user wishes to find Waldo and Co. in
     * @return 2D integer array of RGB values of each pixel in image
     */
    private static int[][] convertToArray(BufferedImage image) {
        int[][] imgArr = new int[image.getHeight()][image.getWidth()];
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                imgArr[row][col] = image.getRGB(col, row);
            }
        }
        return imgArr;
    }

    /**
     * finds the pixel representation of Waldo
     */
    private void findWaldo() {

        Color waldo[][] = new Color[2][2];
        waldo[0][0] = new Color(238, 21, 32);//upper-left pixel color
        waldo[0][1] = new Color(255, 255, 255);//upper-right pixel color
        waldo[1][0] = new Color(255, 255, 255);//lower-left pixel color
        waldo[1][1] = new Color(1, 136, 226);//lower-right pixel color
        for (int i = 0; i < img.getWidth() - 1; i++) {
            for (int j = 0; j < img.getHeight() - 1; j++) {
                if (imgArr[j][i] == waldo[0][0].getRGB()) {//finds a match for the color of the upper-left pixel, and then checks the surrounding pixels to see if they match all the RGB values declared above
                    if (imgArr[j + 1][i] == waldo[0][1].getRGB() && imgArr[j][i + 1] == waldo[1][0].getRGB() && imgArr[j + 1][i + 1] == waldo[1][1].getRGB()) {
                        System.out.println("Waldo found at: (" + i + "," + " " + j + ") by thread ID: " + Thread.currentThread().getId());//Prints x and y coordinates of Waldo in the image, as well as the thread ID
                        break;
                    }
                }
            }
        }
    }

    /**
     * finds the pixel representation of Wilma
     */
    private void findWilma() {
        Color wilma[][] = new Color[2][2];
        wilma[0][0] = new Color(255, 255, 255);//upper-left pixel color
        wilma[0][1] = new Color(238, 21, 32);//upper-right pixel color
        wilma[1][0] = new Color(1, 136, 226);//lower-left pixel color
        wilma[1][1] = new Color(255, 255, 255);//lower-right pixel color
        for (int i = 0; i < img.getWidth() - 1; i++) {
            for (int j = 0; j < img.getHeight() - 1; j++) {
                if (imgArr[j][i] == wilma[0][0].getRGB()) {//finds a match for the color of the upper-left pixel, and then checks the surrounding pixels to see if they match all the RGB values declared above
                    if (imgArr[j + 1][i] == wilma[1][0].getRGB() && imgArr[j][i + 1] == wilma[0][1].getRGB() && imgArr[j + 1][i + 1] == wilma[1][1].getRGB()) {
                        System.out.println("Wilma found at: (" + i + "," + " " + j + ") by thread ID: " + Thread.currentThread().getId());//Prints x and y coordinates of Wilma in the image, as well as the thread ID
                        break;
                    }
                }
            }
        }
    }

    /**
     * finds the pixel representation of Odlaw
     */
    private void findOdlaw() {
        Color odlaw[][] = new Color[2][2];
        odlaw[0][0] = new Color(253, 252, 3);//upper-left pixel color
        odlaw[0][1] = new Color(32, 32, 32);//upper-right pixel color
        odlaw[1][0] = new Color(32, 32, 32);//lower-left pixel color
        odlaw[1][1] = new Color(253, 252, 3);//lower-right pixel color
        for (int i = 0; i < img.getWidth() - 1; i++) {
            for (int j = 0; j < img.getHeight() - 1; j++) {
                if (imgArr[j][i] == odlaw[0][0].getRGB()) {//finds a match for the color of the upper-left pixel, and then checks the surrounding pixels to see if they match all the RGB values declared above
                    if (imgArr[j + 1][i] == odlaw[0][1].getRGB() && imgArr[j][i + 1] == odlaw[1][0].getRGB() && imgArr[j + 1][i + 1] == odlaw[1][1].getRGB()) {
                        System.out.println("Odlaw found at: (" + i + "," + " " + j + ") by thread ID: " + Thread.currentThread().getId());//Prints x and y coordinates of Odlaw in the image, as well as the thread ID
                        break;
                    }
                }
            }
        }
    }

    /**
     * finds the pixel representation of Wizard Whitebeard
     */
    private void findWizard() {
        Color wizard[][] = new Color[2][2];
        wizard[0][0] = new Color(216, 216, 216);//upper-left pixel color
        wizard[0][1] = new Color(238, 21, 32);//upper-right pixel color
        wizard[1][0] = new Color(238, 21, 32);//lower-left pixel color
        wizard[1][1] = new Color(216, 216, 216);//lower-right pixel color
        for (int i = 0; i < img.getWidth() - 1; i++) {
            for (int j = 0; j < img.getHeight() - 1; j++) {
                if (imgArr[j][i] == wizard[0][0].getRGB()) {//finds a match for the color of the upper-left pixel, and then checks the surrounding pixels to see if they match all the RGB values declared above
                    if (imgArr[j + 1][i] == wizard[0][1].getRGB() && imgArr[j][i + 1] == wizard[1][0].getRGB() && imgArr[j + 1][i + 1] == wizard[1][1].getRGB()) {
                        System.out.println("Wizard Whitebeard found at: (" + i + "," + " " + j + ") by thread ID: " + Thread.currentThread().getId());//Prints x and y coordinates of Wizard Whitebeard in the image, as well as the thread ID
                        break;
                    }
                }
            }
        }
    }

    /**
     * finds the pixel representation of Woof
     */
    private void findWoof() {
        Color woof[][] = new Color[2][2];
        woof[0][0] = new Color(255, 255, 255);//upper-left pixel color
        woof[0][1] = new Color(238, 21, 32);//upper-right pixel color
        woof[1][0] = new Color(238, 21, 32);//lower-left pixel color
        woof[1][1] = new Color(255, 255, 255);//lower-right pixel color
        for (int i = 0; i < img.getWidth() - 1; i++) {
            for (int j = 0; j < img.getHeight() - 1; j++) {
                if (imgArr[j][i] == woof[0][0].getRGB()) {//finds a match for the color of the upper-left pixel, and then checks the surrounding pixels to see if they match all the RGB values declared above
                    if (imgArr[j + 1][i] == woof[0][1].getRGB() && imgArr[j][i + 1] == woof[1][0].getRGB() && imgArr[j + 1][i + 1] == woof[1][1].getRGB()) {
                        System.out.println("Woof found at: (" + i + "," + " " + j + ") by thread ID: " + Thread.currentThread().getId());//Prints x and y coordinates of Woof in the image, as well as the thread ID

                    }
                }
            }
        }
    }
}
