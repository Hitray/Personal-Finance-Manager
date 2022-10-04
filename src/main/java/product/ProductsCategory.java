package product;

import java.util.*;

public class ProductsCategory {
    private Set<String> products = new HashSet<String>();
    private String categoryName;
    private int sum;

    public ProductsCategory(String categoryName){
        this.categoryName = categoryName;
    }

    public boolean isProductExistsInCategory(String productName){
        return products.contains(productName);
    }

    public void addProduct(String productName){
        products.add(productName);
    }

    public boolean trackSum(String productName, int cost){
        if(isProductExistsInCategory(productName)){
            sum += cost;
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
