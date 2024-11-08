package com.example.crudspringboot.controller;

import com.example.crudspringboot.dto.ApiResponse;
import com.example.crudspringboot.dto.PostDTO;
import com.example.crudspringboot.model.Post;
import com.example.crudspringboot.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Validated
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ApiResponse<List<PostDTO>> getAllPosts() {
        List<PostDTO> posts = postService.findAll();

        return new ApiResponse<>(true, "Posts retrieved successfully", posts);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> getPostById(@PathVariable Long id) {
        PostDTO postDTO = postService.findById(id);

        if (postDTO != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Post retrieved successfully", postDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Post not found", null));
        }

    }


    @PostMapping
    public ResponseEntity<ApiResponse<PostDTO>> createPost(@Valid @RequestBody Post post) {
        Post savedPost = postService.save(post);

        PostDTO postDTO = new PostDTO(savedPost);

        if (postDTO != null) {
            return new ResponseEntity<>(new ApiResponse<>(true, "Post created successfully", postDTO), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponse<>(false, "Post not found", null), HttpStatus.NOT_FOUND);
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostDTO>> updatePost(@PathVariable Long id, @Valid @RequestBody Post postDetails) {
        Post updatedPost = postService.update(id, postDetails);

        PostDTO postDTO = new PostDTO(updatedPost);

        if (postDTO != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "Post updated successfully", postDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, "Post not found", null));
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {

        PostDTO postDTO = postService.findById(id);

        if (postDTO != null) {
            postService.delete(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Post deleted successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "Post not found", null));
        }

    }
}
