package util;

import model.Tournament;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/*
UserStory: En admin skal kunne indlæse hold til en turnering fra en fil. Hvis han skal oprette en ny turnering med nye hold, skal han blot udskifte denne fil.
 Det vil gøre at vi ikke behøver at ændre i koden bare fordi der er tale om nogle andre hold.
iteration 1:
 */

public class FileLoader {

    public static Scanner importTeams(String filename) throws FileNotFoundException {
        File fh = new File(filename);
        Scanner input;

        if (fh.exists()) {
            input = new Scanner(fh);


        }else {
            throw new FileNotFoundException();
        }
        return input;
    }

    public static void exportMatches(String filename, String filter){

        File fh = new File(filename);

    }
    public static void saveTeamsToFile(String s){
        File log = new File("orderLog.txt");
        try{
            PrintWriter out = new PrintWriter(log);
            out.println(s);
            out.close();
        }catch(IOException e){
            System.out.println("Error writing to file");
        }
    }

    public static Scanner loadTeams(String file) {
        Scanner data=null;
        try{
           data = FileLoader.importTeams("teams.csv");

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
