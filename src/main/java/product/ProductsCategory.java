package product;

import jsonData.JsonProductData;

import java.util.*;

public class ProductsCategory {
    private Set<String> products = new HashSet<String>();
    private String categoryName;
    private int sum;

    private List addedData = new ArrayList<JsonProductData>();

    public ProductsCategory(String categoryName){
        this.categoryName = categoryName;
    }

    public boolean isProductExistsInCategory(String productName){
        return products.contains(productName);
    }

    public void addProduct(String productName){
        products.add(productName);
    }

    public boolean trackSum(String productName, int cost, String date){
        if(isProductExistsInCategory(productName)){
            sum += cost;

            JsonProductData jsonProductData = new JsonProductData();
            jsonProductData.title = productName;
            jsonProductData.sum = sum;
            jsonProductData.date = date;

            addedData.add(jsonProductData);
            return true;
        }

        return false;
    }

    public boolean trackSum(JsonProductData jsonProductData){
        if(isProductExistsInCategory(jsonProductData.title)){
            sum += jsonProductData.sum;

            addedData.add(jsonProductData);
            return true;
        }

        return false;
    }

    public Set<String> getProducts() {
        return products;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getSum() {
        return sum;
    }
}
