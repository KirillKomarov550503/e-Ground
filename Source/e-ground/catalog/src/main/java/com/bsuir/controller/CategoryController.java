package com.bsuir.controller;

import com.bsuir.dto.CategoryDto;
import com.bsuir.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Class of Offer REST Controller. Contains CRUD methods.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@RestController
@RequestMapping(value = "api/v1/catalog/categories")
public class CategoryController {
    /**
     * Field of Category Service.
     */
    private final CategoryService categoryService;

    /**
     * Constructor that accepts a object CategoryService class.
     *
     * @param categoryService object of CategoryService class
     */
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Method that converts DTO to class object and create it.
     *
     * @param categoryDto data transfer object
     * @return created object of Category class
     */
    @PostMapping
    public CategoryDto create(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    /**
     * Method that finds an object.
     *
     * @param id Long of the object to be found
     * @return founded object or NullPointerException
     */
    @GetMapping(path = "/{id}")
    public CategoryDto getById(@PathVariable("id") UUID id) {
        return categoryService.findById(id);
    }

    /**
     * Method that finds all objects.
     *
     * @return founded objects
     */
    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryService.findAll();
    }

    /**
     * Method that save updated object.
     *
     * @param categoryDto updated category that needs to save
     * @return updated and saved category
     */
    @PutMapping
    public CategoryDto update(@Validated @RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }

    /**
     * Method that delete object.
     *
     * @param id Long object that needs to delete
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") UUID id) {
        categoryService.delete(id);
    }
}