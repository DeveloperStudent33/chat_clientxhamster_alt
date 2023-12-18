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

    public boolean vorneFrei(boolean[][] besucht){
        return vornFrei() && wurdeDasVonMirBesucht(besucht);
    }

    public boolean linksFrei(boolean[][] besucht){
        linksUm();
        if(vornFrei() && wurdeDasVonMirBesucht(besucht)){
            rechtsUm();
            return true;
        }
        rechtsUm();
        return false;
    }

    public boolean rechtsFrei(boolean[][] besucht){
        rechtsUm();
        if(vornFrei() && wurdeDasVonMirBesucht(besucht)){
            linksUm();
            return true;
        }
        linksUm();
        return false;
    }

    public boolean wurdeDasVonMirBesucht(boolean[][] besucht){
        return !switch (this.getBlickrichtung()) {
            case 0 -> besucht[getReihe() - 1][getSpalte()];
            case 1 -> besucht[getReihe()][getSpalte() + 1];
            case 2 -> besucht[getReihe() + 1][getSpalte()];
            case 3 -> besucht[getReihe()][getSpalte() - 1];
            default -> false;
        };
    }
}
