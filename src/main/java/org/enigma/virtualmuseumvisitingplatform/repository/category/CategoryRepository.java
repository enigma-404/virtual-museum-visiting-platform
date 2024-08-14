package org.enigma.virtualmuseumvisitingplatform.repository.category;

import org.enigma.virtualmuseumvisitingplatform.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
