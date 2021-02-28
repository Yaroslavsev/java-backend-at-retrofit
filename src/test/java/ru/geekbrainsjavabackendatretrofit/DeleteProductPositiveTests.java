package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteProductPositiveTests extends BaseTest {

    Integer productId;

    @BeforeEach
    void setUp() {

        response = createProductTestData();

        assert response.body() != null;
        productId = response.body().getId();
    }

    @SneakyThrows
    @Test
    void deleteProductPositiveTest() {

        Response<ResponseBody> response = productService.deleteProduct(productId)
                .execute();

        assertThat(response.code()).isEqualTo(200);
    }

}
