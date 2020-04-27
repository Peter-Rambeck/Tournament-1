package util;

import model.Tournament;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TeamDataLoader {
    public static void importTeams(String filename, Tournament tournament) throws FileNotFoundException {

        File fh = new File(filename);
        String line = "";
        if(fh.exists()){
            Scanner input = new Scanner(fh);
            while(input.hasNextLine()){
                line = input.nextLine();
                String [] lineArr = line.split(",");
                String [] playerArr = Arrays.copyOfRange(lineArr,1,3);
                tournament.registerTeam(lineArr[0], playerArr);
            }
        }else{

            throw new FileNotFoundException();
        }
    }
}
