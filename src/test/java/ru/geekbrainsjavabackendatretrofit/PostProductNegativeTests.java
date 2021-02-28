package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.javabackendatretrofit.dto.Product;
import ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.javabackendatretrofit.dto.utils.ConfigUtils.getFakeId;

public class PostProductNegativeTests extends BaseTest {

    Product product;

    @BeforeEach
    void setUp() {

        product = prepareProductTestData();
    }

    @SneakyThrows
    @Test
    void createNewProductWithIdNegativeTest() {

        Response<Product> response = productService.createProduct(product.withId(getFakeId()))
                .execute();

        assert response.errorBody() != null;
        assertThat(response.code()).isEqualTo(400);
        assertThat(response.errorBody().string()).contains("Id must be null for new entity");
    }

    @SneakyThrows
    @Test
    void createNewEmptyProductNegativeTest() {

        Response<Product> response = productService.createProduct(new Product())
                .execute();

        assert response.errorBody() != null;
        assertThat(response.code()).isEqualTo(400);
    }

    @SneakyThrows
    @Test
    void createNewProductWithNegativePriceNegativeTest() {

        Response<Product> response = productService.createProduct(new Product()
                .withTitle(faker.beer().name())
                .withPrice(-100)
                .withCategoryTitle(Categories.ELECTRONIC.getTitle()))
                .execute();

        assert response.errorBody() != null;
        assertThat(response.code()).isEqualTo(400);
    }

}
