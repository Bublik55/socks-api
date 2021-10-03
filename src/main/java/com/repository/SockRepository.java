package com.repository;

import java.util.Optional;

import com.models.Color;
import com.models.CottonPart;
import org.springframework.data.jpa.repository.JpaRepository;

import com.models.Sock;

public interface SockRepository extends JpaRepository<Sock, Long> {
  Iterable<Sock> findByColorAndCottonPartLessThan(String color, int cottonPart);
  Iterable<Sock> findByColorAndCottonPartGreaterThan(String color,int cotton_part);
  Iterable<Sock> findByColorAndCottonPartEquals(String color, int cottonPart);

  Optional<Sock> findOneByColorAndCottonPart(Color color, CottonPart cotton_part);
}
