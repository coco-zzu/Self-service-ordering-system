package zzu.group.www.selfserviceorderingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zzu.group.www.selfserviceorderingsystem.javabean.Product;
import zzu.group.www.selfserviceorderingsystem.mapper.ProductMapper;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public List<Product> getAllProducts() {
        try {
            return productMapper.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Product getProductById(Long id) {
        try {
            return productMapper.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void addProduct(Product product) {
        try {
            productMapper.insert(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateProduct(Product product) {
        try {
            productMapper.update(product);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteProduct(Long id) {
        try {
            productMapper.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateProductStatus(Long id, Integer status) {
        try {
            productMapper.updateStatus(id, status);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
