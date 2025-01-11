package com.knoettner.hhuddle.repository;

import com.knoettner.hhuddle.models.Facility;
import com.knoettner.hhuddle.models.Post;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTimestampAfter(LocalDateTime timestamp);//return the latest posts after a definate date

    @Modifying
    @Query(value = "update Post  set endtime = ?2," +
            " isAnonymous = ?3," +
            " isPrivate = ?4," +
            " pathToPhoto = ?5," +
            " starttime = ?6," +
            " text = ?7," +
            " title = ?8," +
            " facility = ?9" +
            " WHERE id = ?1" )
    void updatePost(
                    Long id,
                    LocalDateTime endtime,
                    Boolean is_anonymous,
                    Boolean is_private,
                    String path_to_photo,
                    LocalDateTime starttime,
                    String text,
                    String post_title,
                    Facility facility_id
    );
}
