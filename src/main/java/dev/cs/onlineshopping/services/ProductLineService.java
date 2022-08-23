package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.devmodels.models.ProductLine;
import dev.cs.onlineshopping.repositories.ProductLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductLineService {
    private ProductLineRepository productLineRepository;
    public ProductLineService(ProductLineRepository productLineRepository) {
        this.productLineRepository = productLineRepository;
    }
    public List<ProductLine> findAllProductLine(){
        return productLineRepository.findAll();
    }
}