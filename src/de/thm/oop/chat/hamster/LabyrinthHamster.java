package de.thm.oop.chat.hamster;

import de.thm.oop.chat.chat_client.CommandHandler;

public class LabyrinthHamster extends ChatHamster{
    public LabyrinthHamster(CommandHandler cmd){
        super(cmd);
    }

    public void rechtsUm(){
        linksUm();
        linksUm();
        linksUm();
    }

    public void kehrt(){
        linksUm();
        linksUm();
    }

    public boolean vornFrei(boolean[][] visited){
        return vornFrei() && wasVisitedByMe(visited);
    }

    public boolean linksFrei(boolean[][] visited){
        linksUm();
        if(vornFrei() && wasVisitedByMe(visited)){
            rechtsUm();
            return true;
        }
        rechtsUm();
        return false;
    }

    public boolean rechtsFrei(boolean[][] visited){
        rechtsUm();
        if(vornFrei() && wasVisitedByMe(visited)){
            linksUm();
            return true;
        }
        linksUm();
        return false;
    }

    public boolean wasVisitedByMe(boolean[][] visited){
        return !switch (this.getDirection()) {
            case 0 -> visited[getRow() - 1][getColumn()];
            case 1 -> visited[getRow()][getColumn() + 1];
            case 2 -> visited[getRow() + 1][getColumn()];
            case 3 -> visited[getRow()][getColumn() - 1];
            default -> false;
        };
    }
}
