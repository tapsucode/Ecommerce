package com.vananh.ecommerce.service.implementation;

import com.vananh.ecommerce.exception.ProductException;
import com.vananh.ecommerce.model.Category;
import com.vananh.ecommerce.model.Product;
import com.vananh.ecommerce.repository.CategoryRepository;
import com.vananh.ecommerce.repository.ProductRepository;
import com.vananh.ecommerce.request.CreateProductRequest;
import com.vananh.ecommerce.service.ProductService;
import com.vananh.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    @Override
    public Product createProduct(CreateProductRequest request) {

        Category topLevel = categoryRepository.findByName(request.getTopLavelCategory());

        if (topLevel == null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(request.getTopLavelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(request.getSecondLavelCategory(),topLevel.getName());

        if (secondLevel == null){
            Category secondLavelCategory = new Category();
            secondLavelCategory.setName(request.getSecondLavelCategory());
            secondLavelCategory.setParentCategory(topLevel);
            secondLavelCategory.setLevel(2);

            secondLevel=categoryRepository.save(secondLavelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(request.getThirdLavelCategory(),secondLevel.getName());

        if (thirdLevel == null){
            Category thirdLavelCategory = new Category();
            thirdLavelCategory.setName(request.getSecondLavelCategory());
            thirdLavelCategory.setParentCategory(topLevel);
            thirdLavelCategory.setLevel(3);

            thirdLevel=categoryRepository.save(thirdLavelCategory);
        }

        Product product = new Product();
        BeanUtils.copyProperties(request,product);

        product.setCategory(thirdLevel);
        product.setCreateAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {

        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product request) throws ProductException {

        Product product = findProductById(productId);

        if (request.getQuantity()!=0) {
            product.setQuantity(request.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()){
            return product.get();
        }
        throw new ProductException("Product not found with id - "+id);
    }

    @Override
    public Product findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice,
                                       Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productRepository.filterProduct(category,minPrice,maxPrice,minDiscount,sort);

        if(!colors.isEmpty()){
           products =products.stream().filter(p->colors.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
                   .collect(Collectors.toList());
        }

        if (stock != null){
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")){
                products=products.stream().filter(p -> p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());

        List<Product> pageContent = products.subList(startIndex,endIndex);

        return new PageImpl<>(pageContent,pageable,products.size());
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
