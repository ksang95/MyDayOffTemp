package com.team4.dayoff.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.dayoff.api.googleStorageAPI.GoogleCloudStorageUpload;
import com.team4.dayoff.entity.Category;
import com.team4.dayoff.entity.Color;
import com.team4.dayoff.entity.Product;
import com.team4.dayoff.entity.ProductImage;
import com.team4.dayoff.entity.ProductSize;
import com.team4.dayoff.repository.CategoryRepository;
import com.team4.dayoff.repository.ColorRepository;
import com.team4.dayoff.repository.ProductImageRepository;
import com.team4.dayoff.repository.ProductRepository;
import com.team4.dayoff.repository.ProductSizeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdminProductController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @RequestMapping("/addProduct")
    public Map<String, Object> addProduct() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Color> color = colorRepository.findAll();
        List<Category> category = categoryRepository.findAll();
        map.put("color", color);
        map.put("category", category);
        return map;
    }

    @PostMapping("/addProductProcess")
    public Map<String, Object> addProductProcess(String json, @RequestParam("file") MultipartFile[] files) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Product product = new ObjectMapper().readValue(json, Product.class);
            Product savedProduct = productRepository.save(product);
            int productId = savedProduct.getId();
            List<ProductSize> productSizes = savedProduct.getProductSize();
            productSizes.forEach(i -> {
                i.setProduct(savedProduct);
            });
            productSizeRepository.saveAll(productSizes);

            try {
                String name = GoogleCloudStorageUpload.saveFile(files[0]);
                savedProduct.setDetailImage(name);
                productRepository.save(savedProduct);
                System.out.println(files.length);
                for (int i = 1; i < files.length; i++) {
                    MultipartFile file = files[i];
                    name = GoogleCloudStorageUpload.saveFile(file);
                    ProductImage productImage = new ProductImage();
                    productImage.setOriginalName(file.getOriginalFilename());
                    productImage.setName(name);
                    productImage.setProduct(savedProduct);
                    productImageRepository.save(productImage);

                }
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                System.out.println(e.getMessage());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println(e.getMessage());
            }

            ProductImage latestProduct = productImageRepository.findTop1ByProduct_IdOrderById(productId);

            // int productCount=productRepository.countByRegisterDatein24Hours();
            map.put("latestProduct", latestProduct);
            map.put("productCount", 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return map;
    }

    @GetMapping("/getProduct")
    public Product getProduct(Integer id) {
        Product product = productRepository.findById(id).get();
        return product;
    }

    @PostMapping("/deleteProduct")
    public void deleteProduct(@RequestParam("id") Integer id) {
        // productRepository.deleteById(id); //실제 delete 잘 작동한다
        productRepository.disableProduct(id); // 상품 이용불가로.
    }

}