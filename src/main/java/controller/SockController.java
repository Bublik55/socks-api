package controller;

import model.Sock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import repository.SockRepository;

@RequestMapping("/api/socks")
public class SockController {

  @Autowired
  SockRepository sockRepository;

  // @TODO SETUP FOR CONDITION - OWERRIDE getAll()
  // operation must be enum
  @GetMapping()
  public ResponseEntity<List<Sock>> getAll(String color, String operation, int cotton_part) {
    try {
      List<Sock> socks = new ArrayList<Sock>();
      this.sockRepository.findAll(color, operation, cotton_part).forEach(socks::add);
      if (socks.isEmpty())
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

      return new ResponseEntity<>(socks, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/income")
  ResponseEntity<Sock> income(@RequestParam String color, @RequestParam int cotton_part, @RequestParam int quantity) {
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

  @PostMapping("/outcome")
  ResponseEntity<Sock> outcome(@RequestParam String color, @RequestParam int cotton_part, @RequestParam int quantity) {
    Optional<Sock> sockData = this.sockRepository.findOneByColorAndCottonPart(color, cotton_part);
    if (sockData.isPresent()) {
      Sock _sock = sockData.get();
      _sock.outcomeSocks(quantity);
      return new ResponseEntity<>(this.sockRepository.save(_sock), HttpStatus.OK);
    } else {
      Sock _sock = new Sock(color, quantity, cotton_part); 
      return new ResponseEntity<>(this.sockRepository.save(_sock), HttpStatus.OK);
    }
  }

}
