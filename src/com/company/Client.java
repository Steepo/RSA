package com.company;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {
        while (true) {
            Scanner keyboard = new Scanner(System.in);
            System.out.print("Inserisci: ");
            String frase = keyboard.nextLine();
            String echo = "";
            Socket clientSocket = new Socket("localhost", 12005);
            DataOutputStream outputVersoServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inputServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputVersoServer.writeBytes(frase + "\n");
            echo = inputServer.readLine();
            System.out.println("Encrypted: " + echo);
            clientSocket.close();
            outputVersoServer.close();
            inputServer.close();
        }
    }
}
