package com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Sock;

public interface SockRepository extends JpaRepository<Sock, Long> {
  Iterable<Sock> findByColorAndCottonPartLessThan(String color, int cottonPart);
  Iterable<Sock> findByColorAndCottonPartGreaterThan(String color,int cotton_part);
  Iterable<Sock> findByColorAndCottonPartEquals(String color, int cottonPart);

  Optional<Sock> findOneByColorAndCottonPart(String color, int cotton_part);
}
