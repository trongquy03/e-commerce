package trongquy.project.E_Commerce.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import trongquy.project.E_Commerce.dto.ProductDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(
            @Valid @ModelAttribute ProductDTO productDTO,
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
                    List<MultipartFile> files = productDTO.getFiles();
                    files = files == null ? new ArrayList<MultipartFile>() : files;
                    for (MultipartFile file : files) {
                        // check size file
                        if (file.getSize() == 0) {
                            continue;
                        }
                        if (file.getSize() > 10 * 1024 * 1024) { // 10mb
                            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                                    .body("File is too large > 10mb");
                        }
                        String contentType = file.getContentType();
                        if (contentType == null || !contentType.startsWith("image/")) {
                            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                    .body("File is not an image");
                        }
                        // save file and update thumbnail dto
                        String fileName = storeFile(file);
                    }


                    return ResponseEntity.ok().body(productDTO);
                }catch (Exception e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // add UUID before file name ( file is only)
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        // link folder save file
        Path uploadDir = Paths.get("uploads");
        // check and create folder if empty
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // path to final file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        //copy file vao destination
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;

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
