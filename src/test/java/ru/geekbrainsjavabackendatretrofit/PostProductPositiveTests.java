package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.javabackendatretrofit.dto.Product;

import static org.assertj.core.api.Assertions.assertThat;

public class PostProductPositiveTests extends BaseTest {

    Integer productId;
    String productTitle;
    int productPrice;

    Product product;

    @BeforeEach
    void setUp() {

        product = prepareProductTestData();
    }

    @SneakyThrows
    @Test
    void createNewProductPositiveTest() {

        Response<Product> response = productService.createProduct(product)
                .execute();

        assert response.body() != null;
        productId = response.body().getId();
        productTitle = product.getTitle();
        productPrice = product.getPrice();

        assertThat(response.code()).isEqualTo(201);
        assertThat(response.body().getId()).isEqualTo(productId);
        assertThat(response.body().getTitle()).isEqualTo(productTitle);
        assertThat(response.body().getPrice()).isEqualTo(productPrice);
    }

    @SneakyThrows
    @Test
    void createNewProductWithoutCategoryTitlePositiveTest() {

        Response<Product> response = productService.createProduct(new Product()
                .withTitle(faker.beer().name())
                .withPrice((int) (Math.random() * 1000 + 1)))
                .execute();

        assert response.errorBody() != null;
        assertThat(response.code()).isEqualTo(201);
        assert response.body() != null;
        assertThat(response.body().getId()).isEqualTo(productId);
        assertThat(response.body().getPrice()).isEqualTo(productPrice);
        assertThat(response.body().getTitle()).isEmpty();
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        Response<ResponseBody> response = productService.deleteProduct(productId)
                .execute();

        assertThat(response.isSuccessful()).isTrue();
    }

}
