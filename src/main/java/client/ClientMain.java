package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientMain {
    private static int port = 8989;
    private static String ip = "localhost";

    public static void main(String[] args) {
        try(Socket clientSocket = new Socket(ip, port);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (true){
                String currentCommand = reader.readLine();
                String[] subSplitCommand = currentCommand.split(" ");

                if(currentCommand.equalsIgnoreCase("end") ||
                   currentCommand.equalsIgnoreCase("exit") ||
                   currentCommand.equalsIgnoreCase("quit")){
                    System.out.println("stop");
                    break;
                }else if(subSplitCommand.length > 1 && subSplitCommand[0].equalsIgnoreCase("add")){
                    String currentDate = getCurrentDate();
                    int sum = Integer.parseInt(subSplitCommand[2]);

                    out.println("{\"title\": \""+ subSplitCommand[1] +"\", \"date\": \"" + currentDate + "\", \"sum\": " + sum + "}");
                    System.out.println("app on server.");

                    System.out.println("answer on server:");
                    System.out.println(in.readLine());
                }
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
