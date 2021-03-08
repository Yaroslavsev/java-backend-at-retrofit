package ru.geekbrainsjavabackendatretrofit;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.db.model.ProductsExample;
import ru.geekbrains.javabackendatretrofit.dto.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetAllProductsTests extends BaseTest {

    @SneakyThrows
    @Test
    void getAllProductsPositiveTest() {

        Response<List<Product>> response = productService.getAllProducts()
                .execute();

        assertThat(response.body()).isNotEmpty();

        assert response.body() != null;

        /*----------Не уверен, сработала бы такая проверка на равенство количества продуктов в ответе через API и через базу----------*/
        assertThat(productsMapper.selectByExample(new ProductsExample()).size()).isEqualTo(response.body().size());
    }

}
