package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.javabackendatretrofit.dto.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories.ELECTRONIC;

public class PutProductPositiveTests extends BaseTest {

    Integer productId;
    String productTitle;
    int productPrice;
    String productCategoryTitle;

    String productNewTitle;
    int productNewPrice;
    String productNewCategoryTitle;

    @SneakyThrows
    @BeforeEach
    void setUp() {

        response = createProductTestData();

        assert response.body() != null;
        productId = response.body().getId();
        productTitle = response.body().getTitle();
        productPrice = response.body().getPrice();
        productCategoryTitle = response.body().getCategoryTitle();

        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body().getCategoryTitle()).isEqualTo("Food");
    }

    @SneakyThrows
    @Test
    void modifyProductPositiveTest() {

        Response<Product> response = productService.modifyProduct(new Product()
                .withId(productId)
                .withTitle(faker.lebowski().actor())
                .withPrice((int) (Math.random()*1000+100000))
                .withCategoryTitle(ELECTRONIC.getTitle()))
                .execute();

        assert response.body() != null;
        productNewTitle = response.body().getTitle();
        productNewPrice = response.body().getPrice();
        productNewCategoryTitle = response.body().getCategoryTitle();

        assertThat(response.code()).isEqualTo(200);
        assertThat(productNewTitle).isNotEqualTo(productTitle);
        assertThat(productNewPrice).isNotEqualTo(productPrice);
        assertThat(productNewCategoryTitle).isNotEqualTo(productCategoryTitle);
    }

    @SneakyThrows
    @Test
    void modifyProductCategoryTitlePositiveTest() {

        Response<Product> response = productService.modifyProduct(new Product()
                .withId(productId)
                .withTitle(productTitle)
                .withPrice(productPrice)
                .withCategoryTitle(ELECTRONIC.getTitle()))
                .execute();

        assert response.body() != null;
        productNewTitle = response.body().getTitle();
        productNewPrice = response.body().getPrice();

        assertThat(response.code()).isEqualTo(200);
        assertThat(productNewCategoryTitle).isNotEqualTo(productCategoryTitle);
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(productId)
                .execute();

        assertThat(response.isSuccessful()).isTrue();
    }

}
