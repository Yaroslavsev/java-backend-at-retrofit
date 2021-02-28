package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.javabackendatretrofit.dto.utils.ConfigUtils.getFakeId;

public class DeleteProductNegativeTests extends BaseTest {

    Integer productId;

    @BeforeEach
    void setUp() throws IOException {

        response = createProductTestData();

        assert response.body() != null;
        productId = response.body().getId();

        productService.deleteProduct(productId)
                .execute();
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
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        productService.deleteProduct(productId)
                .execute();
    }

}
