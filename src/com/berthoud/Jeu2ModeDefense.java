package com.berthoud;

import java.util.Arrays;
import java.util.Scanner;

public class Jeu2ModeDefense extends Jeu2 {

    public Jeu2ModeDefense(int pNombreCases, int pNombreEssaisMax, int pNombreCouleurs) {
        super(pNombreCases, pNombreEssaisMax, pNombreCouleurs);
        super.mode = "mode défense";
    }

    public void play() {

        System.out.println("Choisis une combinaison de " + nombreCases + " chiffres compris entre 0 et " + nombreCouleurs);
        codeMysterieux = super.saisieCodeJoueur();

        tentativeCode = super.generateurCode(); //1ere réponse de l'ordinateur = chiffre aléatoire

        while ((super.nombreEssais < super.nombreEssaisMax) && (!super.isCodeFound)) {

            // affichage du code sous forme d'une ligne:
            displayTentativeCodeOrdinateur(tentativeCode);

            // saisie validation par le joueur:
            saisieValidationJoueur();


            // Fait une réponse en retour à la validation joueur
            reponseValidationJoueur();

        }

        // CE CODE DOIT ETRE ENCORE FACTORISE
        if (super.isCodeFound) {
            System.out.println("Superbrain a gagné!");
        } else {
            System.out.println("Tu as vaincu Superbrain!");
        }

    }


    // affichage d'un int array code sous forme d'une ligne:
    protected void displayTentativeCodeOrdinateur(int[] tentativeCode) {
        String[] tentativeCodeStringArray = new String[nombreCases];
        for (int x = 0; x < nombreCases; x++) {
            tentativeCodeStringArray[x] = String.valueOf(tentativeCode[x]);
        }
        System.out.println("Superbrain te répond:");
        System.out.println(super.arrayToString(tentativeCodeStringArray));

    }


    protected void reponseValidationJoueur() {

        // création d'un pool de l'ensemble des combinaisons possibles sous la forme d'un tableau multidimensionnel de bytes
        // chaque ligne inclut une combinaison + 2 bytes pour validation (bien placés/couleurOk)

        int nbCombinaisons = (int) Math.pow(nombreCases, nombreCouleurs);
        byte[][] poolCombinaisons = new byte[nbCombinaisons][nombreCases + 2];


        for (int i = 0; i < (nombreCases); i++) {
            poolCombinaisons[0][i] = 0;
        }

        for (int j = 1; j < nbCombinaisons ; j++) {
            poolCombinaisons[j] = remplissagePoolCombinaisons(poolCombinaisons [j-1]);

        }


            //   Définition des conditions de sortie de boucle
            super.isCodeFound();
            super.nombreEssais++;

        }



    protected void saisieValidationJoueur() {

        //Validation ordi pour vérifier entrées du joueur
        int[] validationOrdi = super.validation(codeMysterieux, tentativeCode);

        String bienPlacéJoueur = "0";
        String couleursOKJoueur = "0";

        System.out.print("Nombre de couleurs bien placées = ");
        bienPlacéJoueur = scan.nextLine();

        while ((!super.isInt(bienPlacéJoueur) && bienPlacéJoueur.length() > 2)) {
            System.out.println("Saisie incorrecte");
        }
        int intBienPlacéJoueur = Integer.parseInt(bienPlacéJoueur);

        while (intBienPlacéJoueur != validationOrdi[0]) {
            System.out.println("Oups, saisie incorrecte");
        }

        System.out.print("Nombre de couleurs présente = ");
        couleursOKJoueur = scan.nextLine();

        while ((!super.isInt(couleursOKJoueur) && couleursOKJoueur.length() > 2)) {
            System.out.println("Saisie incorrecte");
        }
        int intCouleursOKJoueur = Integer.parseInt(couleursOKJoueur);

        while (intCouleursOKJoueur != validationOrdi[1]) {
            System.out.println("Oups, saisie incorrecte");
        }
    }

    private byte[] remplissagePoolCombinaisons(byte[] tab) {
        byte[] nextTab = Arrays.copyOf(tab, tab.length);

        for ( int i = tab.length; i == 0 ; i--) {

            if (tab[i] != nombreCouleurs) {
                nextTab[i] = (byte) (tab[tab.length] + 1);
                break;
            } else nextTab[i] = 0;

        }

        return nextTab;
    }

}