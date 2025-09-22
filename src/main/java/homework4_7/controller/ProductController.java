package homework4_7.controller;

import homework4_7.dto.ProductDto;
import homework4_7.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/account/{accountNumber}")
    public ProductDto getProductByAccountNumber(@PathVariable("accountNumber") String accountNumber) {
        return productService.getByAccountNumber(accountNumber);
    }

    @GetMapping
    public List<ProductDto> getAllByUserId(@RequestParam("userId") Long id) {
        return productService.getAllByUserId(id);
    }
}
