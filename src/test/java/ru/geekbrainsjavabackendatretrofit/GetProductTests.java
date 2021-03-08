package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.javabackendatretrofit.dto.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.javabackendatretrofit.dto.utils.ConfigUtils.getFakeId;
import static ru.geekbrains.javabackendatretrofit.dto.utils.DbUtils.selectCategoryById;
import static ru.geekbrains.javabackendatretrofit.dto.utils.DbUtils.selectProductById;

public class GetProductTests extends BaseTest {

    Integer productId;
    String productTitle;
    int productPrice;

    @BeforeEach
    void setUp() {

        response = createProductTestData();

        assert response.body() != null;
        productId = response.body().getId();
        productTitle = response.body().getTitle();
        productPrice = response.body().getPrice();

        assertThat(response.isSuccessful()).isTrue();
    }

    @SneakyThrows
    @Test
    void getProductPositiveTest() {

        Response<Product> response = productService.getProduct(productId).execute();

        assertThat(response.code()).isEqualTo(200);
        assert response.body() != null;
        assertThat(response.body().getId()).isEqualTo(productId);
        assertThat(response.body().getTitle()).isEqualTo(productTitle);
        assertThat(response.body().getPrice()).isEqualTo(productPrice);

        assertThat(selectProductById(productId).getId()).isEqualTo(Long.valueOf(productId));
        assertThat(selectProductById(productId).getTitle()).isEqualTo(productTitle);
        assertThat(selectProductById(productId).getPrice()).isEqualTo(productPrice);
    }

    @SneakyThrows
    @Test
    void getProductNegativeTest() {

        Response<Product> response = productService.getProduct(getFakeId()).execute();

        assertThat(response.code()).isEqualTo(404);
        assert response.errorBody() != null;
        assertThat(response.errorBody().string()).contains("Unable to find product with id: " + getFakeId());

        assertThat(selectProductById(getFakeId())).isNull();
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {

        productsMapper.deleteByPrimaryKey(Long.valueOf(productId));

        assertThat(selectCategoryById(productId)).isNull();
    }

}
