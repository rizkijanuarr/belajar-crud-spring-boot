package com.example.crudspringboot.controller;

import com.example.crudspringboot.dto.ApiResponse;
import com.example.crudspringboot.dto.TagDTO;
import com.example.crudspringboot.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
@Validated
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public ApiResponse<List<TagDTO>> getAllTags() {
        List<TagDTO> tags = tagService.findAll();

        return new ApiResponse<>(true, "Tags retrieved successfully", tags);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagDTO>> getTagById(@PathVariable Long id) {
        TagDTO tagDTO = tagService.findById(id);

        if (tagDTO != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Tag retrieved successfully", tagDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Tag not found", null));
        }

    }

    @PostMapping
    public ResponseEntity<ApiResponse<TagDTO>> createTag(@Valid @RequestBody TagDTO tag) {
        TagDTO savedTag = tagService.save(tag);

        TagDTO tagDTO = new TagDTO(savedTag.getId(), savedTag.getName());

        if (tagDTO != null) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Tag created successfully", tagDTO), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(false, "Tag not found", null), HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TagDTO>> updateTag(@PathVariable Long id, @RequestBody TagDTO tagDetails) {
        TagDTO updatedTag = tagService.update(id, tagDetails);

        TagDTO tagDTO = new TagDTO(updatedTag.getId(), updatedTag.getName());

        if(tagDTO != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Tag updated successfully", tagDTO));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(false, "Tag not found", null));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable Long id) {

        TagDTO tagDTO = tagService.findById(id);

        if (tagDTO != null) {
            tagService.delete(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Tag deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Tag deleted successfully", null));
        }

    }
}


