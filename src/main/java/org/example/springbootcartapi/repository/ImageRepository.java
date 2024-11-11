package org.example.springbootcartapi.repository;
import org.example.springbootcartapi.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
