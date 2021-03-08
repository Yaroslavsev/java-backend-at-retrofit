package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.javabackendatretrofit.dto.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories.ELECTRONIC;
import static ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories.FOOD;
import static ru.geekbrains.javabackendatretrofit.dto.utils.DbUtils.selectProductById;

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
        assertThat(response.body().getCategoryTitle()).isEqualTo(FOOD.getTitle());
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

        assertThat(selectProductById(productId).getTitle()).isNotEqualTo(productTitle);
        assertThat(selectProductById(productId).getPrice()).isNotEqualTo(productPrice);
        assertThat(selectProductById(productId).getCategory_id()).isNotEqualTo(Long.valueOf(FOOD.getId()));
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

        assertThat(selectProductById(productId).getTitle()).isEqualTo(productTitle);
        assertThat(selectProductById(productId).getPrice()).isEqualTo(productPrice);
        assertThat(selectProductById(productId).getCategory_id()).isNotEqualTo(Long.valueOf(FOOD.getId()));
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {

        productsMapper.deleteByPrimaryKey(Long.valueOf(productId));

        assertThat(selectProductById(productId)).isNull();
    }

}
