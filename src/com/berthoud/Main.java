package com.berthoud;


public class Main {

    public static void main(String[] args) {

        Jeu2 partie1 = new Jeu2ModeDefense(4, 15, 10);

        ((Jeu2ModeDefense) partie1).reponseValidationJoueur();

        partie1.play();


//        for(int i=0; i<args.length; i++) {
//            System.out.println("Parameter "+i+" is: "+ args[i]);
//        }

//        Jeu1 partie1 = new Jeu1ModeAttaque(4, 4);
//        partie1.play();
//
//        Jeu1ModeDefense partie2 = new Jeu1ModeDefense(4, 6);
//        partie2.play();
//
//        Jeu1ModeDuel partie3 = new Jeu1ModeDuel(2, 6);
//        partie3.play();




    }

}

