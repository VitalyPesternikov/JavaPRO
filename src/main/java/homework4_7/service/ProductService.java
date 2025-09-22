package homework4_7.service;

import homework4_7.dto.ProductDto;
import homework4_7.model.ProductEntity;
import homework4_7.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto getByAccountNumber(String accountNumber) {
        return productRepository.findByAccountNumber(accountNumber)
                .map(this::productToDto)
                .orElseThrow(() -> new NoSuchElementException("Не найдена запись Product c accountNumber = " + accountNumber));
    }

    public List<ProductDto> getAllByUserId(Long id) {
        return productRepository.findAllByUserId(id)
                .stream()
                .map(this::productToDto)
                .toList();
    }

    private ProductDto productToDto(ProductEntity productEntity) {
        return new ProductDto(productEntity.getId(), productEntity.getAccountNumber(), productEntity.getBalance(), productEntity.getType());
    }
}
