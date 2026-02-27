package github.com.rexfilius.tea.modules.category.service;

import github.com.rexfilius.tea.exception.ResourceNotFoundException;
import github.com.rexfilius.tea.modules.category.model.Category;
import github.com.rexfilius.tea.modules.category.model.CategoryDto;
import github.com.rexfilius.tea.modules.category.repository.CategoryRepository;
import github.com.rexfilius.tea.utils.ModelMapper;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public CategoryDto addCategory(CategoryDto dto) {
        Category category = modelMapper.dtoToModel(dto);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.modelToDto(savedCategory);
    }

    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return modelMapper.modelToDto(category);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map((category) -> modelMapper.modelToDto(category))
                .collect(Collectors.toList());
    }

    public CategoryDto updateCategory(CategoryDto dto, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setId(dto.getId());

        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.modelToDto(updatedCategory);
    }

    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
    }
}
