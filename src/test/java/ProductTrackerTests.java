import com.google.gson.Gson;
import jsonData.JsonProductData;
import net.jodah.failsafe.internal.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import product.ProductsTracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductTrackerTests {
    public static ProductsTracker productsTracker;

    @BeforeEach
    public void InitBeforeTest(){
        productsTracker = new ProductsTracker();
    }

    @Test
    public void testGetCategoryWithHighestSum(){
        String currentDate = getCurrentDate();

        Gson gson = new Gson();
        JsonProductData jsonProductData = gson.fromJson("{\"title\": \"тапки\", \"date\": \"" + currentDate + "\", \"sum\": 500}", JsonProductData.class);
        productsTracker.addNewProduct(jsonProductData);

        jsonProductData = gson.fromJson("{\"title\": \"тапки\", \"date\": \"" + currentDate + "\", \"sum\": 600}", JsonProductData.class);
        productsTracker.addNewProduct(jsonProductData);

        jsonProductData = gson.fromJson("{\"title\": \"мыло\", \"date\": \"" + currentDate + "\", \"sum\": 100}", JsonProductData.class);
        productsTracker.addNewProduct(jsonProductData);

        Assertions.assertEquals(1100, productsTracker.getCategoryWithHighestSum().getSum());
    }

    @Test
    public void testGetJsonSumForCategoryByProductName(){
        String currentDate = getCurrentDate();

        Gson gson = new Gson();
        JsonProductData jsonProductData = gson.fromJson("{\"title\": \"тапки\", \"date\": \"" + currentDate + "\", \"sum\": 500}", JsonProductData.class);
        productsTracker.addNewProduct(jsonProductData);

        jsonProductData = gson.fromJson("{\"title\": \"тапки\", \"date\": \"" + currentDate + "\", \"sum\": 600}", JsonProductData.class);
        productsTracker.addNewProduct(jsonProductData);

        jsonProductData = gson.fromJson("{\"title\": \"мыло\", \"date\": \"" + currentDate + "\", \"sum\": 100}", JsonProductData.class);
        productsTracker.addNewProduct(jsonProductData);

        Assertions.assertEquals("{" +
                "  \"maxCategory\": {" +
                "    \"category\": \"одежда\"," +
                "    \"sum\": \"1100\"" +
                "  }" +
                "}", productsTracker.getJsonSumForCategoryByProductName(""));
    }

    private static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
