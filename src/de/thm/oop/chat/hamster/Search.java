package de.thm.oop.chat.hamster;

import de.thm.oop.chat.chat_client.CommandHandler;

public class Search {
    boolean visited[][];
    LabyrinthHamster hamster;
    public Search(CommandHandler cmd){
        hamster = new LabyrinthHamster(cmd);
        visited = new boolean[hamster.getTerritoryRows()][hamster.getTerritoryColumns()];
        for(int i = 0; i < visited.length; i++){
            for(int j = 0; j < visited[0].length; j++){
                visited[i][j] = false;
            }
        }

        if(!hamster.linksFrei(visited) && !hamster.rechtsFrei(visited) && !hamster.vornFrei(visited)){
            hamster.kehrt();
        }

        searchLabyrinth();
    }

    public boolean searchLabyrinth(){
        if(hamster.kornDa()){
            hamster.nimm();
            hamster.schreib("Corn Found");
            return true;
        }

        visited[hamster.getRow()][hamster.getColumn()] = true;

        if(hamster.linksFrei(visited) && searchPartialLabyrinthLeft()){
            return true;
        }

        if(hamster.rechtsFrei(visited) && searchPartialLabyrinthRight()){
            return true;
        }

        if(!hamster.vornFrei(visited)){
            hamster.kehrt();
            return false;
        }

        return searchPartialLabyrinthFront();
    }

    public boolean searchPartialLabyrinthLeft(){
        hamster.linksUm();
        hamster.vor();
        boolean found = searchLabyrinth();

        hamster.vor();
        hamster.linksUm();

        return found;
    }

    public boolean searchPartialLabyrinthRight(){
        hamster.rechtsUm();
        hamster.vor();
        boolean found = searchLabyrinth();

        hamster.vor();
        hamster.rechtsUm();

        return found;
    }

    public boolean searchPartialLabyrinthFront(){
        hamster.vor();
        boolean found = searchLabyrinth();

        hamster.vor();

        return found;
    }
}
