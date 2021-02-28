package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.javabackendatretrofit.dto.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllProductsTests extends BaseTest {

    @SneakyThrows
    @Test
    void getAllProductsPositiveTest() {

        Response<List<Product>> response = productService.getAllProducts()
                .execute();

        assertThat(response.code()).isEqualTo(200);
        assertThat(response.body()).isNotEmpty();
    }

}
