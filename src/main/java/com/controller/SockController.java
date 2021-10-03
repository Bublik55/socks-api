package com.controller;

import com.models.Color;
import com.models.CottonPart;
import com.models.Sock;
import com.repository.ColorRepository;
import com.repository.CottonPartRepository;
import com.repository.SockRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@Validated
@RequestMapping("/api/socks")
public class SockController {

  @Autowired
  SockRepository sockRepository;
  @Autowired
  CottonPartRepository cottonPartRepository;
  @Autowired
  ColorRepository colorRepository;

  @GetMapping()
  ResponseEntity<String> getByCondition(@RequestParam @NotBlank String strColor,
      @RequestParam @Min(0) @Max(100) int cotton_part, @RequestParam @NotBlank String operation) {
    try {
      strColor = strColor.trim();
      List<Sock> socks = new ArrayList<Sock>();
      switch (operation) {
        case ("equal"):
          this.sockRepository.findByColorAndCottonPartEquals(strColor, cotton_part).forEach(socks::add);
          break;
        case ("moreThan"):
          this.sockRepository.findByColorAndCottonPartGreaterThan(strColor, cotton_part).forEach(socks::add);
          break;
        case ("lessThan"):
          this.sockRepository.findByColorAndCottonPartLessThan(strColor, cotton_part).forEach(socks::add);
          break;
        default:
          return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      }
      if (socks.isEmpty())
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
      final int torturing = socks.stream().mapToInt(Sock::getQuantity).sum();
      return new ResponseEntity<>(Integer.toString(torturing), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Validated
  @PostMapping("/income")
  ResponseEntity<Sock> income(@RequestParam @NotBlank String color, @RequestParam @Min(0) @Max(100) int cotton_part,
      @RequestParam @Min(1) int quantity) {
    ResponseEntity re;
    Color oColor = findColorOrSave(color);
    CottonPart cottonPart = findCottonPartOrSave(cotton_part);
    Optional<Sock> Sock = this.sockRepository.findOneByColorAndCottonPart(oColor, cottonPart);
    if (Sock.isPresent()) {
      Sock.get().incomeSocks(quantity);
      this.sockRepository.saveAndFlush(Sock.get());
      re = new ResponseEntity<>(null, HttpStatus.OK);
    } else {
      this.sockRepository.saveAndFlush(new Sock(oColor, quantity, cottonPart));
      re = new ResponseEntity<>(null, HttpStatus.OK);
    }
    return re;
  }

  @Validated
  @PostMapping("/outcome")
  ResponseEntity<Sock> outcome(@RequestParam @NotBlank String color, @RequestParam @Min(0) @Max(100) int cotton_part,
      @RequestParam @Min(1) int quantity) {
    ResponseEntity re;
    Color oColor = findColorOrSave(color);
    CottonPart cottonPart = findCottonPartOrSave(cotton_part);
    Optional<Sock> sockData = this.sockRepository.findOneByColorAndCottonPart(oColor, cottonPart);
    if (sockData.isPresent()) {
      Sock _sock = sockData.get();
      _sock.outcomeSocks(quantity);
      re = new ResponseEntity<>(null, HttpStatus.OK);
    } else {
      re = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
    return re;
  }

  @ExceptionHandler({ ConstraintViolationException.class })
  public ResponseEntity<Object> handleValidateException(Exception exc) {
    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ HttpServerErrorException.InternalServerError.class })
  public ResponseEntity<Object> handleInternalServerException(Exception exc) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Color findColorOrSave(String color) {
    color = color.trim();
    Optional<Color> colorToDb =this.colorRepository.findOneByColor(color);
    if(colorToDb.isEmpty())
      return this.colorRepository.save(new Color(color));
    else return colorToDb.get();
  }

  private CottonPart findCottonPartOrSave(int cottonPart) {
    Optional<CottonPart> cpToDb = this.cottonPartRepository.findOneByCottonPart(cottonPart);
    if(cpToDb.isEmpty())
      return this.cottonPartRepository.save(new CottonPart(cottonPart));
    else return cpToDb.get();
  }

}