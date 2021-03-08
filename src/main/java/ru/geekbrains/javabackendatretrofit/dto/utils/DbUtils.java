package ru.geekbrains.javabackendatretrofit.dto.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.geekbrains.db.dao.CategoriesMapper;
import ru.geekbrains.db.dao.ProductsMapper;
import ru.geekbrains.db.model.Categories;
import ru.geekbrains.db.model.Products;

import java.io.IOException;
import java.io.InputStream;

@UtilityClass
public class DbUtils {

    private static String resource = "mybatisConfig.xml";

    @SneakyThrows
    public static CategoriesMapper getCategoriesMapper() {

        return getSqlSession().getMapper(CategoriesMapper.class);
    }

    @SneakyThrows
    public static ProductsMapper getProductsMapper() {

        return getSqlSession().getMapper(ProductsMapper.class);
    }

    public static Products selectProductById(Integer id){

        return getProductsMapper().selectByPrimaryKey(Long.valueOf(id));
    }

    public static Categories selectCategoryById(Integer id){

        return getCategoriesMapper().selectByPrimaryKey(id);
    }

    private static SqlSession getSqlSession() throws IOException {
        SqlSessionFactory sqlSessionFactory;

        InputStream is = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = sqlSessionFactory.openSession(true);
        return session;
    }

}