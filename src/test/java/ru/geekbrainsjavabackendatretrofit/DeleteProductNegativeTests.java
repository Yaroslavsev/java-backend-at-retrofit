package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.javabackendatretrofit.dto.utils.ConfigUtils.getFakeId;
import static ru.geekbrains.javabackendatretrofit.dto.utils.DbUtils.selectCategoryById;
import static ru.geekbrains.javabackendatretrofit.dto.utils.DbUtils.selectProductById;

public class DeleteProductNegativeTests extends BaseTest {

    Integer productId;

    @BeforeEach
    void setUp() {

        response = createProductTestData();

        assert response.body() != null;
        productId = response.body().getId();

        productsMapper.deleteByPrimaryKey(Long.valueOf(productId));
    }

    @SneakyThrows
    @Test
    void deleteProductWithNotExistingIdNegativeTest() {

        Response<ResponseBody> response = productService.deleteProduct(getFakeId())
                .execute();

        assertThat(response.code()).isEqualTo(404);
    }

    @SneakyThrows
    @Test
    void deleteJustDeletedProductNegativeTest() {

        Response<ResponseBody> response = productService.deleteProduct(productId)
                .execute();

        assertThat(response.code()).isEqualTo(204);

        assertThat(selectProductById(productId)).isNull();
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {

        productsMapper.deleteByPrimaryKey(Long.valueOf(productId));

        assertThat(selectCategoryById(productId)).isNull();
    }

}
