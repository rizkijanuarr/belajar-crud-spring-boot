package com.example.crudspringboot.repository;

import com.example.crudspringboot.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
