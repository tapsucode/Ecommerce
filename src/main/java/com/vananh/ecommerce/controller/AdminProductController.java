package com.vananh.ecommerce.controller;

import com.vananh.ecommerce.exception.ProductException;
import com.vananh.ecommerce.model.Product;
import com.vananh.ecommerce.request.CreateProductRequest;
import com.vananh.ecommerce.response.ApiResponse;
import com.vananh.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest request){

        Product product = productService.createProduct(request);

        return new  ResponseEntity<Product>(product,HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable() Long productId) throws ProductException{

        productService.deleteProduct(productId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Product deleted successfully");
        apiResponse.setStatus(true);

        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProduct() {

        List<Product> products = productService.findAllProducts();

        return new ResponseEntity<List<Product>>(products,HttpStatus.OK);

    }

    @PutMapping("/{productId}update/")
    public ResponseEntity<Product> updateProduct(@PathVariable()Long productId,@RequestBody Product request) throws ProductException{

        Product product = productService.updateProduct(productId,request);

        return new ResponseEntity<Product>(product, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] product)  {

        for (CreateProductRequest request: product){
            productService.createProduct(request);
        }
        ApiResponse result = new ApiResponse();
        result.setMessage("Product create successfully");
        result.setStatus(true);
        return new  ResponseEntity<ApiResponse>(result,HttpStatus.CREATED);

    }
}
