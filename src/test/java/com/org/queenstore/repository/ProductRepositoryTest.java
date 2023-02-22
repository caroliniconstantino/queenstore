package com.org.queenstore.repository;

import com.org.queenstore.model.Product;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductRepositoryTest {

    @Autowired
    public ProductRepository productRepository;

    @BeforeAll
    void start(){
        productRepository.deleteAll();
        productRepository.save(new Product(0L, "Vestido", 299.90, 50));
        productRepository.save(new Product(0L, "Sapato", 539.90, 20));

    }

    @Test
    @DisplayName("Retornar um produto")
    public void deveRetornarUmProduto(){
        Optional<Product> product = productRepository.findByName("Vestido");
        assertTrue(product.get().getName().equals("Vestido"));
    }

}
