package trongquy.project.E_Commerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import trongquy.project.E_Commerce.dto.ProductDTO;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @PostMapping("")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDTO productDTO,
            BindingResult result
            ){
                try {
                    if (result.hasErrors()) {
                        List<String> errorMessages = result.getFieldErrors()
                                .stream()
                                .map(FieldError::getDefaultMessage)
                                .toList();
                        return ResponseEntity.badRequest().body(errorMessages);
                    }
                    return ResponseEntity.ok().body(productDTO);
                }catch (Exception e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }

    @GetMapping("")
    public ResponseEntity<String> getProducts(
            @RequestParam("page")   int page,
            @RequestParam("limit")  int limit
    ) {
        return ResponseEntity.ok("Product list");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Product details");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok("Product deleted");
    }
}
