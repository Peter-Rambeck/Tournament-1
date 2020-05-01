
package view;

import model.Match;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author tess
 * Dette er en consolUI, men en naturlig udvidelse af systemet ville være at bygge en grafisk UI. 
 * Derfor ville det være en god ide at lave et interface, UI som lister alle de ting et UI skal kunne
 * Og så omnavngive denne til consolUI
 */
public class UI {

    Scanner input = new Scanner(System.in);
  
    public String getInput() {
        return input.nextLine();
    }

    public void println(String msg) {
        System.out.println(msg);
    }


    public String registerTeam() {

        println("Skriv holdets navn");
        String userinput = getInput();

        return userinput;
    }
    /**
     * Et userflow hvor brugeren får lov at tilføje x antal spillere til holdet
     *
     * @return  den liste af spillere holdet får tildelt efter flow'et
     */
    public ArrayList<String> addPlayers(){
        String userinput = "";
        ArrayList<String> playerlist = new ArrayList();

        while (userinput.equals("")){
            println("\nSkriv navn på spiller q for at afslutte");
            try {
                userinput = getInput();
                if(!userinput.equals("q")){
                    playerlist.add(userinput);
                    userinput = "";
                }
            } catch(InputMismatchException e){
                println("Det ligner ikke et navn");
            }
        }
        return playerlist;
    }





    public void scheduleMatch() {

        println("Hvilken type kamp er det?");
        println("1) finale");
        println("2) semifinale");
        println("3) kvartfinale");
        println("6) q");
        String matchType = "";

        while (!matchType.equals("q")){
   //input validering
            try {
                matchType = getInput();
                if(Match.MatchType.valueOf(matchType)!=null){
                    throw new InputMismatchException();
                }

            } catch(InputMismatchException e){
                println("Det ligner ikke et navn");
            }
        }

        println("Skriv kampens dato. Format:  yyyy-mm-yy");
        println("6) q");
        String date = "";

        while (!matchType.equals("q")){
            //input validering
            try {
                matchType = getInput();
                if(Match.MatchType.valueOf(matchType)!=null){
                    throw new InputMismatchException();
                }

            } catch(InputMismatchException e){
                println("Det ligner ikke et navn");
            }
        }

    }
}
