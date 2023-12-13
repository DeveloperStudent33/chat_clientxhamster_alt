package de.thm.oop.chat.hamster;

import de.thm.oop.chat.chat_client.CommandHandler;
import de.thm.oop.chat.messages.Message;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class ChatHamster {

    private CommandHandler commandHandler;
    private int blickrichtung;
    String[][] feld;
    private int reihe;
    private int spalte;

    private int territoriumSpalten;
    private int territoriumReihen;


    public ChatHamster(CommandHandler commandHandler) {
        this.blickrichtung = 1;
        this.reihe = 0;
        this.spalte = 0;
        this.commandHandler = commandHandler;
        String text1, text2;
        do {
            befehlSchicken("init");
            ArrayList<Message> msg = commandHandler.saveMsg();
            text1 = msg.get(msg.size()-3).getContent();
            text2 = msg.get(msg.size()-2).getContent();
        } while (!text1.startsWith("farbe"));
        System.out.println("[Hampter]: Im starting with color " + text1.substring(7));

        feldBerechnen(text2);
    }

    public void feldBerechnen(String text){
        String[] text2Splitted = text.split(" ");
        territoriumSpalten = Integer.parseInt(text2Splitted[1]);
        territoriumReihen = Integer.parseInt(text2Splitted[2]);
        feld = new String[territoriumReihen][territoriumSpalten];
        int counter = 3;
        for (int i = 0; i < feld.length; i++) {
            for (int j = 0; j < feld[0].length; j++) {
                feld[i][j] = text2Splitted[counter];
                counter++;
            }
        }
    }

    public void befehlSchicken(String befehl){
        this.commandHandler.msg(new String[]{"", "hamster23ws", befehl});
    }

    public void vor(){
        befehlSchicken("vor");
        coordsBerechnen();
    }

    public void linksUm(){
        befehlSchicken("linksUm");
        blickrichtungÄndern();
    }

    public void nimm(){
        befehlSchicken("nimm");
    }

    public void schreib(String input){
        System.out.println(input);
    }

    public boolean kornDa(){
        return feld[reihe][spalte].equals("!");
    }

    public boolean vornFrei(){
        if (blickrichtung == 0 && (getReihe()-1) >= 0) {
            return !feld[getReihe() - 1][getSpalte()].equals("x");
        } else if (blickrichtung == 1 && (getSpalte()+1) < feld[0].length) {
            return !feld[getReihe()][getSpalte() + 1].equals("x");
        } else if (blickrichtung == 2 && (getReihe()+1) < feld.length) {
            return !feld[getReihe() + 1][getSpalte()].equals("x");
        } else if (blickrichtung == 3 && (getSpalte()-1) >= 0) {
            return !feld[getReihe()][getSpalte() - 1].equals("x");
        }
        return false;
    }

    public int getReihe(){
        return reihe;
    }

    public int getSpalte(){
        return spalte;
    }

    public int getBlickrichtung(){
        return blickrichtung;
    }

    public int getTerritoriumSpalten() {
        return territoriumSpalten;
    }

    public int getTerritoriumReihen() {
        return territoriumReihen;
    }

    public void blickrichtungÄndern(){
        if(blickrichtung == 0){
            blickrichtung = 3;
        } else {
            blickrichtung--;
        }
    }

    public void coordsBerechnen(){
        switch(blickrichtung) {
            case 0 -> reihe--;
            case 1 -> spalte++;
            case 2 -> reihe++;
            case 3 -> spalte--;
        }
    }
}
