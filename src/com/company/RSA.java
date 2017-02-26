package com.company;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.math.*;

public class RSA
{
    static BigInteger p;
    static BigInteger q;
    static BigInteger N;
    static BigInteger z;
    static BigInteger e;
    static BigInteger d;
    static int lung = 4096;
    static Random     r;


    public static void main(String[] args) throws IOException
    {

        ServerSocket welcome = new ServerSocket(12005);
        String msg = "";
        while (true)
        {
            Socket clientSocket = welcome.accept();
            BufferedReader inputClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream outputVersoClient = new DataOutputStream(clientSocket.getOutputStream());
            msg = inputClient.readLine();
            System.out.println("Testo: " + msg);
            outputVersoClient.writeBytes(encode(msg)); 
            clientSocket.close();
            outputVersoClient.close();

        }
    }
    public static String encode(String frase){
        r = new Random();
        p = BigInteger.probablePrime(lung, r); //genera p numero primo
        q = BigInteger.probablePrime(lung, r); //genera q numero primo
        N = p.multiply(q);
        z = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));

        d = BigInteger.probablePrime(lung / 2, r);

        while ((z.gcd(d).compareTo(BigInteger.valueOf(1)) > 0) && (d.compareTo(z) < 0))
        {
            d.add(BigInteger.ONE);
        }
        e = d.modInverse(z);



        //System.out.println("Encrypting String: " + frase);
        System.out.println("String in bytes: "
                + binToString(frase.getBytes()));
        //Cripta
        byte[] encrypted = encrypt(frase.getBytes());
        //Decripta
        byte[] decrypted = decrypt(encrypted);
        System.out.println("Privata: " + d);
        System.out.println("Pubblica: "+e);

        System.out.println("Encrypted: " + binToString(encrypted));
        System.out.println("Decrypted: " + binToString(decrypted));
        System.out.println("Decrypted String: " + new String(decrypted));
        return binToString(encrypted);
    }

    private static String binToString(byte[] encrypted)
    {
        String temp = "";
        for (byte b : encrypted)
        {
            temp += Byte.toString(b);
        }
        return temp;
    }

    public static byte[] encrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }

    public static byte[] decrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }
}