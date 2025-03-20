package trongquy.project.E_Commerce.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import trongquy.project.E_Commerce.dto.CategoryDTO;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
//@Validated
public class CategoryController {

    @GetMapping("")
    public ResponseEntity<String> getAllCategories(
            @RequestParam("page")   int page,
            @RequestParam("limit")  int limit
    ) {
        return ResponseEntity.ok("All categories");
    }



    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Category updated");
    }

    @PostMapping("")
    public ResponseEntity<?> insertCategory(@Valid
             @RequestBody CategoryDTO categoryDTO,
            BindingResult result) {
//        System.out.println("Received name: [" + categoryDTO.getName() + "]");
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok("Category inserted" +categoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok("Category deleted");
    }
}
