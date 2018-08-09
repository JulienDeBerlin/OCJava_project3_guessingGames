package com.berthoud.project3oc;

/**
 * This class contains static methods that can be useful in different contexts beyond the 2 games
 */
public class MyTools {

    /**
     * This methods tests if the String entered as parameter is only made of digits
     * @param myString chain of characters to be tested
     * @return true or false
     */
    public static boolean  isMyStringAnInt(String myString) {
        char[] tab = myString.toCharArray();
        int k = 0;
        for (char c: tab) {
            if (!Character.isDigit(c)){
                k--;
                return k >= 0;
            }
        }

        if (myString.equals("")) {
            k--;
        }
        return k >= 0;
    }


    /**
     * This methods insert a break in the game flow, to make the interactions with the computer more human-like
     * @param lengthBreak length of the break, in milliseconds
     */
    public static void makeABreak (int lengthBreak){
        try{
            Thread.sleep(lengthBreak);
        } catch (java.lang.InterruptedException e){
            System.out.println("Everything is fine!");
        }
    }


    /**
     * This method converts a String array to a String and add a space between each element
     * @param myArray String array to be converted
     * @return the String made of all the elements of the String array, with space between each element
     */
    public static String arrayToString(String myArray[]) {
        String myString = myArray[0];
        for (int i = 1; i < myArray.length; i++) {
            myString += (' ' + myArray[i]);
        }
        return myString;
    }



}
