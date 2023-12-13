package de.thm.oop.chat.hamster;

import de.thm.oop.chat.chat_client.CommandHandler;

import java.sql.SQLOutput;

public class Suche {
    boolean besucht[][];
    LabyrinthHamster hamster;
    public Suche(CommandHandler cmd){
        hamster = new LabyrinthHamster(cmd);
        besucht = new boolean[hamster.getTerritoriumReihen    ()][hamster.getTerritoriumSpalten()];
        for(int i = 0; i < besucht.length; i++){
            for(int j = 0; j < besucht[0].length; j++){
                besucht[i][j] = false;
            }
        }

        /*if(!hamster.linksFrei() && !hamster.rechtsFrei() && !hamster.vorneFrei()){ 			// Wenn der Hamster in einer Sackgasse startet
            hamster.kehrt();
        }*/

        durchsucheLabyrinth();
    }

    public boolean durchsucheLabyrinth(){
        if(hamster.kornDa()){ 																// Wurde das korn gefunden???
            hamster.nimm();
            hamster.schreib("Korn gefunden");
            return true;
        }

        besucht[hamster.getReihe()][hamster.getSpalte()] = true;

        if(hamster.linksFrei(besucht) && durchsucheTeilLabyrinthLinks()){ 					// ist links offen, dann besuche links das Labyrinth
            return true;
        }

        if(hamster.rechtsFrei(besucht) && durchsucheTeilLabyrinthRechts()){ 					// ist rechts offen, dann besuche rechts das Labyrinth
            return true;
        }

        if(!hamster.vorneFrei(besucht)){ 														// Sackgasse
            hamster.kehrt();
            return false;
        }

        return durchsucheTeilLabyrinthVorne();											// sonst vorne Durchsuchen
    }

    public boolean durchsucheTeilLabyrinthLinks(){
        hamster.linksUm();
        hamster.vor();
        boolean gefunden = durchsucheLabyrinth();

        hamster.vor();
        hamster.linksUm();

        return gefunden;
    }

    public boolean durchsucheTeilLabyrinthRechts(){
        hamster.rechtsUm();
        hamster.vor();
        boolean gefunden = durchsucheLabyrinth();

        hamster.vor();
        hamster.rechtsUm();

        return gefunden;
    }

    public boolean durchsucheTeilLabyrinthVorne(){
        hamster.vor();
        boolean gefunden = durchsucheLabyrinth();

        hamster.vor();

        return gefunden;
    }
}
