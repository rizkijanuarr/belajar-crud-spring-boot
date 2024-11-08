package com.example.crudspringboot.service;


import com.example.crudspringboot.dto.TagDTO;
import com.example.crudspringboot.model.Tag;
import com.example.crudspringboot.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<TagDTO> findAll() {
        return tagRepository.findAll().stream()
                .map(tag -> new TagDTO(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }

    public TagDTO findById(Long id) {
        Tag tag = tagRepository.findById(id).orElse(null);
        return tag != null ? new TagDTO(tag.getId(), tag.getName()) : null;
    }


    public TagDTO save(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        Tag savedTag = tagRepository.save(tag);

        return new TagDTO(savedTag.getId(), savedTag.getName());
    }


    public TagDTO update(Long id, TagDTO tagDTO) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id " + id));

        tag.setName(tagDTO.getName());
        Tag updatedTag = tagRepository.save(tag);
        return new TagDTO(updatedTag.getId(), updatedTag.getName());
    }


    public void delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id " + id));
        tagRepository.delete(tag);
    }

}
