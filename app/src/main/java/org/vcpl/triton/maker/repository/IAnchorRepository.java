package org.vcpl.triton.maker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.maker.model.Anchor;

public interface IAnchorRepository extends JpaRepository<Anchor, Long> {

    @Query(value="SELECT * FROM anchor a WHERE a.name=?1", nativeQuery=true)
    Anchor findByName(String name);
}
