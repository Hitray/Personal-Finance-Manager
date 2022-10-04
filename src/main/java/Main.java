import product.ProductsTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989);) { 
            System.out.println("Server runs!");

            ProductsTracker productsTracker = new ProductsTracker();
            while (true) { 
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    
                    String inString = in.readLine();

                    

                    out.println("{" +
                            "  \"maxCategory\": {" +
                            "    \"category\": \"еда\"," +
                            "    \"sum\": 350000" +
                            "  }" +
                            "}");
                }
            }
        } catch (IOException e) {
            System.out.println("Dont starts server");
            e.printStackTrace();
        }
    }
}
