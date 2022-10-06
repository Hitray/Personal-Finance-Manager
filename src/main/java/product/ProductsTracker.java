package product;

import jsonData.JsonProductData;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ProductsTracker {
    private Map trackedProducts = new HashMap<String, ProductsCategory>();
    // Key - Название товара Value - Категория
    private Map hashProducts = new HashMap<String, String>();

    private static String template = "булка\tеда\n" +
            "колбаса\tеда\n" +
            "сухарики\tеда\n" +
            "курица\tеда\n" +
            "тапки\tодежда\n" +
            "шапка\tодежда\n" +
            "мыло\tбыт\n" +
            "акции\tфинансы";

    public ProductsTracker(){
        loadExistingCategories();
    }

    private void loadExistingCategories(){
        try {
            File myObj = new File("categories.tsv");

         
            if(myObj.exists() == false){
                myObj.createNewFile();

                Files.writeString(Path.of(myObj.getPath()), template);
                System.out.println("Load data.");
            } else{
                System.out.println("File data.");
            }

            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String[] splitedData = data.split("\t");

                if(splitedData.length == 2){
                    ProductsCategory currentCategory;
                    if(trackedProducts.containsKey(splitedData[1])){
                        currentCategory = (ProductsCategory) trackedProducts.get(splitedData[1]);
                    } else {
                        currentCategory = new ProductsCategory(splitedData[1]);
                    }
                    currentCategory.addProduct(splitedData[0]);
                    hashProducts.put(splitedData[0], splitedData[1]);
                    trackedProducts.put(splitedData[1], currentCategory);
                }
                System.out.println(data);
            }
            myReader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewProduct(JsonProductData product){
        
        if(hashProducts.containsKey(product.title)){
            ProductsCategory prCat = (ProductsCategory) trackedProducts.get(hashProducts.get(product.title));
            prCat.trackSum(product);
        } else {
            ProductsCategory currentCategory;
            if(trackedProducts.containsKey("другое")){
                currentCategory = (ProductsCategory) trackedProducts.get("другое");
            } else {
                currentCategory = new ProductsCategory("другое");
            }
            currentCategory.addProduct(product.title);
            currentCategory.trackSum(product);
            hashProducts.put(product.title, "другое");
            trackedProducts.put("другое", currentCategory);
        }

        System.out.println("point " + product.title + " load category " + hashProducts.get(product.title) + " summ: " + product.sum);
    }

    public String getJsonSumForCategoryByProductName(String productName){
        ProductsCategory productsCategory = (ProductsCategory) trackedProducts.get(hashProducts.get(productName));

        return "{" +
                "  \"maxCategory\": {" +
                "    \"category\": \"" + productsCategory.getCategoryName() + "\"," +
                "    \"sum\": \"" + productsCategory.getSum() + "\"" +
                "  }" +
                "}";
    }
}
