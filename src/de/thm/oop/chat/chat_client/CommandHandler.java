package de.thm.oop.chat.chat_client;

import de.thm.oop.chat.base.server.BasicTHMChatServer;
import de.thm.oop.chat.hamster.Search;
import de.thm.oop.chat.messages.*;
import de.thm.oop.chat.receiver.Group;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CommandHandler extends ChatClient{
    private final BasicTHMChatServer server = new BasicTHMChatServer();
    private ArrayList<Group> allGroups = new ArrayList<>();

    public CommandHandler(){
        Scanner scan = new Scanner(System.in);
        while (super.isActive()){
            String input = scan.nextLine();
            commandSelection(filterCommand(input));
        }
    }

    public String[] filterCommand(String input) {
        return input.split(" ");
    }

    public void commandSelection(String[] inputFiltered) {
        switch (inputFiltered[0]) {
            case "msg" -> this.msg(inputFiltered);
            case "msgG" -> this.msgG(inputFiltered);
            case "msgP" -> this.msgP(inputFiltered[1], inputFiltered[2]);
            case "msgGP" -> this.msgGP(inputFiltered[1], inputFiltered[2]);
            case "help" -> this.help();
            case "getMsg" -> this.printMsg(saveMsg());
            case "getUsers" -> this.getUsers();
            case "createGroup" -> this.createGroup(inputFiltered);
            case "getGroups" -> this.getGroups();
            case "exit" -> super.setActive();
            case "LabyrinthSuche" -> this.labyrinthSearch();
            default -> System.out.println("Command could not be found.");
        }
    }

    public void help() {
        System.out.println("User\n" +
                "- msg           -> Individual message      -> msg   [Receiver]  [message] (Press Enter to send a message)\n" +
                "- msgG          -> Group message           -> msgG  [GroupName] [message] (Press Enter to send a message)\n" +
                "- msgP          -> Picture message         -> msgP  [Receiver]  [file]    (Press Enter to send a picture)\n" +
                "- msgGP         -> Group picture message   -> msgGP [GroupName] [file]    (Press Enter to send a picture)\n" +
                "- help          -> Instruction declaration\n" +
                "- getMsg        -> Get messages    [Retrieve all messages]\n" +
                "- getUsers      -> Get all users   [Retrieving a list of users (= potential chat partners]\n" +
                "- createGroup   -> Create a group          -> createGroup [Name] [Member1] [Member2] [Member3] ...\n" +
                "- getGroups     -> Displays all groups\n" +
                "- exit          -> Close/Cancel program");
    }

    public void createGroup(String[] inputFiltered) {
        String[] groupMembers = new String[inputFiltered.length - 2];
        for (int i = 0; i < groupMembers.length; i++) {
            groupMembers[i] = inputFiltered[i + 2];
        }

        String groupName = inputFiltered[1];
        Group group = new Group(groupMembers, groupName);
        allGroups.add(group);
        System.out.println("Group was created.");
    }

    public void msg(String[] inputFiltered) {
        String txt = "";
        for (int i = 2; i < inputFiltered.length; i++) {
            if(i == inputFiltered.length-1){
                txt = txt + inputFiltered[i];
            } else {
                txt = txt + inputFiltered[i] + " ";
            }
        }
        Text text = new Text(inputFiltered[1], txt);
        text.send(super.getUser());
    }

    public void msgG(String[] inputFiltered){
        for (Group group : allGroups) {
            if(group.getName().equalsIgnoreCase(inputFiltered[1])){
                for (int i = 0; i < group.getMembers().size(); i++) {
                    inputFiltered[1] = group.getMembers().get(i).getName();
                    msg(inputFiltered);
                }
            }
        }
    }

    public void msgP(String argument1, String argument2){
        Picture picture = new Picture(argument1, argument2);
        picture.send(super.getUser());
    }

    public void msgGP(String argument1, String argument2) {
        for (Group group : allGroups) {
            if (group.getName().equalsIgnoreCase(argument1)) {
                for (int i = 0; i < group.getMembers().size(); i++) {
                    this.msgP(group.getMembers().get(i).getName(), argument2);
                }
            } else {
                System.out.println("The group could not be found!");
            }
        }
    }

    public void getUsers() {
        try {
            String[] allUsers = server.getUsers(super.getUser().getUsername(), super.getUser().getPassword());
            System.out.println("User:");
            Arrays.sort(allUsers);
            for (String user : allUsers) {
                System.out.println(user);
            }
        } catch (IOException e) {
            System.out.println("An unexpected error has occurred.");
        }
    }

    public void getGroups() {
        for (Group group : allGroups) {
            System.out.println(group.getName() + ":");
            for (int i = 0; i < group.getMembers().size(); i++) {
                System.out.println("    " + (i + 1) + ". " + group.getMembers().get(i).getName());
            }
        }
    }

    public ArrayList<Message> saveMsg() {
        String[] allMessages = new String[0];
        // Server connection
        try {
            allMessages = server.getMessages(super.getUser().getUsername(), super.getUser().getPassword(), 0);
        } catch (IOException e) {
            System.out.println("An unexpected error has occurred.");
        }
        ArrayList<Message> messages = new ArrayList<>();
        for (String allMessage : allMessages) {
            String[] splitMessage = allMessage.split("\\|");
            // Save Messages to Objects
            boolean out = (splitMessage[2].equals("out"));
            if (splitMessage[4].equals("img")) {
                messages.add(new Picture(splitMessage[3], splitMessage[1], Integer.parseInt(splitMessage[0]), out, splitMessage[7], splitMessage[5]));
            } else {
                messages.add(new Text(splitMessage[3], splitMessage[1], Integer.parseInt(splitMessage[0]), out, splitMessage[5]));
            }
        }
        return messages;
    }

    public void printMsg(ArrayList<Message> messages){
        // Paste Messages from Objects
        for (Message msgs : messages) {
            System.out.println(msgs.toString());
        }
    }

    public void labyrinthSearch(){
        Search labyrinthSearch = new Search(this);
        // Einfügen, was der Hamster zurückschickt --> gleich in den Konstruktor mit rein
    }
}
