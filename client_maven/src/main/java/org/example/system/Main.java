package org.example.system;


public class Main {
    public static void main(String[] args) {
        try {
            Client client = new Client(args[0], Integer.parseInt(args[1]));
            client.start();
        } catch (Exception e){
            System.out.println("Something wrong: " + e.getMessage());
        }
    }
}