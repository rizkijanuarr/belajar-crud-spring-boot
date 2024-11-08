package com.example.crudspringboot.service;

import com.example.crudspringboot.dto.PostDTO;
import com.example.crudspringboot.model.Post;
import com.example.crudspringboot.model.Tag;
import com.example.crudspringboot.repository.PostRepository;
import com.example.crudspringboot.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;


    public List<PostDTO> findAll() {
        return postRepository.findAll().stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }


    public PostDTO findById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return post != null ? new PostDTO(post) : null;
    }


    public Post save(Post post) {

        Set<Tag> validTags = new HashSet<>();

        for (Tag tag : post.getTags()) {
            Tag existingTag = tagRepository.findById(tag.getId()).orElse(null);
            if (existingTag != null) {
                validTags.add(existingTag);
            }
        }
        post.setTags(validTags);
        return postRepository.save(post);
    }


    public Post update(Long id, Post postDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));

        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setTags(postDetails.getTags()); // update
        return postRepository.save(post);
    }


    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        postRepository.delete(post);
    }
}
