package com.controller;

import com.model.Sock;
import com.repository.SockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Validated
@RequestMapping("/api/socks")
public class SockController {

  @Autowired
  SockRepository sockRepository;

  @GetMapping()
  ResponseEntity<List<Sock>> getByCondition(@RequestParam @NotBlank String color,
      @RequestParam @Min(0) @Max(100) int cotton_part, @RequestParam @NotBlank String operation) {
    try {
      List<Sock> socks = new ArrayList<Sock>();
      switch (operation) {
        case ("equal"):
          this.sockRepository.findByColorAndCottonPartEquals(color, cotton_part).forEach(socks::add);
          break;
        case ("moreThan"):
          this.sockRepository.findByColorAndCottonPartGreaterThan(color, cotton_part).forEach(socks::add);
          break;
        case ("lessThan"):
          this.sockRepository.findByColorAndCottonPartLessThan(color, cotton_part).forEach(socks::add);
          break;
        default:
          return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }
      if (socks.isEmpty())
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      return new ResponseEntity<>(socks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Validated
  @PostMapping("/income")
  ResponseEntity<Sock> income(@RequestParam @NotBlank String color, @RequestParam @Min(0) @Max(100) int cotton_part,
      @RequestParam @Min(1) int quantity) {
    Optional<Sock> sockData = this.sockRepository.findOneByColorAndCottonPart(color, cotton_part);
    if (sockData.isPresent()) {
      Sock _sock = sockData.get();
      _sock.incomeSocks(quantity);
      return new ResponseEntity<>(this.sockRepository.save(_sock), HttpStatus.OK);
    } else {
      Sock _sock = new Sock(color, quantity, cotton_part);
      return new ResponseEntity<>(this.sockRepository.save(_sock), HttpStatus.OK);
    }
  }

  @Validated
  @PostMapping("/outcome")
  ResponseEntity<Sock> outcome(@RequestParam @NotBlank String color, @RequestParam @Min(0) @Max(100) int cotton_part,
      @RequestParam @Min(1) int quantity) {
    Optional<Sock> sockData = this.sockRepository.findOneByColorAndCottonPart(color, cotton_part);
    if (sockData.isPresent()) {
      Sock _sock = sockData.get();
      _sock.outcomeSocks(quantity);
      if (_sock.getQuantity() < 0)
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      return new ResponseEntity<>(this.sockRepository.save(_sock), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }
}