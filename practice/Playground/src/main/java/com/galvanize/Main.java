package com.galvanize;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a name and email");
        } else {

            System.out.println(args[0] + " < " + args[1] + " > " );
    }
}}