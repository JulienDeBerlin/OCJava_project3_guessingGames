package com.berthoud;

public class MyTools {

    /**
     * This methods tests if the String entered as parameter is only made of digits
     * @param myString
     * @return true or false
     */
    public static boolean  isMyStringAnInt(String myString) {
        char[] tab = myString.toCharArray(); // conversion d'un String en tableau de chars
        int k = 0;
        for (char carac : tab) {
            if (!Character.isDigit(carac))
                k--;
        }
        if (k < 0) {
            return false;
        }
        return true;
    }


    /**
     * This methods insert a break in the game flow, to make the interactions with the computer more human-like
     * @param lenghtBreak
     */
    public static void makeABreak (int lenghtBreak){
        try{
            Thread.sleep(lenghtBreak);
        } catch (java.lang.InterruptedException e){
            System.out.println("Everything is fine!");
        }
    }




}
