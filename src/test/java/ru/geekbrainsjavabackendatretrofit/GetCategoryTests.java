package ru.geekbrainsjavabackendatretrofit;

import org.junit.jupiter.api.Test;
import retrofit2.Response;
import ru.geekbrains.db.model.ProductsExample;
import ru.geekbrains.javabackendatretrofit.dto.Category;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories.ELECTRONIC;
import static ru.geekbrains.javabackendatretrofit.dto.base.enums.Categories.FOOD;
import static ru.geekbrains.javabackendatretrofit.dto.utils.ConfigUtils.getFakeId;
import static ru.geekbrains.javabackendatretrofit.dto.utils.DbUtils.selectCategoryById;

public class GetCategoryTests extends BaseTest {

    ProductsExample productsExample = new ProductsExample();

    @Test
    void getFoodCategoryPositiveTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(FOOD.getId()).execute();

        assertThat(response.code()).isEqualTo(200);
        assert response.body() != null;

        assertThat(response.body().getId()).isEqualTo(selectCategoryById(FOOD.getId()).getId());
        assertThat(response.body().getTitle()).isEqualTo(selectCategoryById(FOOD.getId()).getTitle());

        productsExample.createCriteria().andCategory_idEqualTo(Long.valueOf(FOOD.getId()));
        assertThat(response.body().getProducts().size()).isEqualTo(productsMapper.countByExample(productsExample));
    }

    @Test
    void getElectronicsCategoryPositiveTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(ELECTRONIC.getId()).execute();

        assertThat(response.code()).isEqualTo(200);
        assert response.body() != null;

        assertThat(response.body().getId()).isEqualTo(selectCategoryById(ELECTRONIC.getId()).getId());
        assertThat(response.body().getTitle()).isEqualTo(selectCategoryById(ELECTRONIC.getId()).getTitle());

        productsExample.createCriteria().andCategory_idEqualTo(Long.valueOf(ELECTRONIC.getId()));
        assertThat(response.body().getProducts().size()).isEqualTo(productsMapper.countByExample(productsExample));
    }

    @Test
    void getCategoryWithNotExistingIdNegativeTest() throws IOException {

        Response<Category> response = categoryService
                .getCategory(getFakeId()).execute();

        assertThat(response.code()).isEqualTo(404);
        assert response.errorBody() != null;
        assertThat(response.errorBody().string()).contains("Unable to find category with id: " + getFakeId());

        assertThat(categoriesMapper.selectByPrimaryKey(getFakeId())).isNull();
    }

}
