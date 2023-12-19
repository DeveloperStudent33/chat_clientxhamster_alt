package de.thm.oop.chat.hamster;

import de.thm.oop.chat.chat_client.CommandHandler;
import de.thm.oop.chat.messages.Message;

import java.util.ArrayList;

public class ChatHamster {

    private CommandHandler commandHandler;
    private int direction;
    String[][] field;
    private int row;
    private int column;

    private int territoryColumns;
    private int territoryRows;


    public ChatHamster(CommandHandler commandHandler) {
        this.direction = 1;
        this.row = 0;
        this.column = 0;
        this.commandHandler = commandHandler;
        String text1, text2;
        do {
            sendCommand("init");
            ArrayList<Message> msg = commandHandler.saveMsg();
            text1 = msg.get(msg.size()-3).getContent();
            text2 = msg.get(msg.size()-2).getContent();
        } while (!text1.startsWith("farbe"));
        System.out.println("[Hampter]: Im starting with color " + text1.substring(7));

        calculateField(text2);
    }

    public void calculateField(String text){
        String[] text2Splitted = text.split(" ");
        territoryColumns = Integer.parseInt(text2Splitted[1]);
        territoryRows = Integer.parseInt(text2Splitted[2]);
        field = new String[territoryRows][territoryColumns];
        int counter = 3;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = text2Splitted[counter];
                counter++;
            }
        }
    }

    public void sendCommand(String command){
        this.commandHandler.msg(new String[]{"", "hamster23ws", command});
    }

    public void vor(){
        sendCommand("vor");
        calculateCoords();
    }

    public void linksUm(){
        sendCommand("linksUm");
        changeDirection();
    }

    public void nimm(){
        sendCommand("nimm");
    }

    public void schreib(String input){
        System.out.println(input);
    }

    public boolean kornDa(){
        return field[row][column].equals("!");
    }

    public boolean vornFrei(){
        if (direction == 0 && (getRow()-1) >= 0) {
            return !field[getRow() - 1][getColumn()].equals("x");
        } else if (direction == 1 && (getColumn()+1) < field[0].length) {
            return !field[getRow()][getColumn() + 1].equals("x");
        } else if (direction == 2 && (getRow()+1) < field.length) {
            return !field[getRow() + 1][getColumn()].equals("x");
        } else if (direction == 3 && (getColumn()-1) >= 0) {
            return !field[getRow()][getColumn() - 1].equals("x");
        }
        return false;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public int getDirection(){
        return direction;
    }

    public int getTerritoryColumns() {
        return territoryColumns;
    }

    public int getTerritoryRows() {
        return territoryRows;
    }

    public void changeDirection(){
        if(direction == 0){
            direction = 3;
        } else {
            direction--;
        }
    }

    public void calculateCoords(){
        switch(direction) {
            case 0 -> row--;
            case 1 -> column++;
            case 2 -> row++;
            case 3 -> column--;
        }
    }
}
