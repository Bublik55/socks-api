package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Sock;

public interface SockRepository extends JpaRepository<Sock, Long> {
  Sock findByColorAndCottonPart(String color, int cottonPart);

  Iterable<Sock> findAll(String color, String operation, int cotton_part);

  Optional<Sock> findOneByColorAndCottonPart(String color, int cotton_part);
}
