package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.Avatar;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Integer> { }
