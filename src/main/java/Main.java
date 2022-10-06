import com.google.gson.Gson;
import jsonData.JsonProductData;
import product.ProductsTracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8989);) 
            System.out.println("Server runs!");

            ProductsTracker productsTracker = new ProductsTracker();
            while (true) { 
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ) {
                    
                    String inString = in.readLine();

                    // Формируем JSON из строки и добавляем данные в ProductTracer
                    Gson gson = new Gson();
                    JsonProductData jsonProductData = gson.fromJson(inString, JsonProductData.class);
                    productsTracker.addNewProduct(jsonProductData);

                    
                    String outJsonData = productsTracker.getJsonSumForCategoryByProductName(jsonProductData.title);

                    System.out.println("Clietn json : ");
                    System.out.println(outJsonData);

                    out.println(outJsonData);
                }
            }
        } catch (IOException e) {
            System.out.println("Dont start server");
            e.printStackTrace();
        }
    }
}
