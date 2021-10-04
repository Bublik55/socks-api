package com.controller;

import com.SockService;
import com.models.Color;
import com.models.CottonPart;
import com.models.Sock;
import com.repository.ColorRepository;
import com.repository.CottonPartRepository;
import com.repository.SockRepository;


import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.service.spi.InjectService;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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

    SockService sockService;

    public SockController(SockService sockService) {
        this.sockService = sockService;
    }

    @GetMapping()
    ResponseEntity countByCondition(
            @RequestParam @NotBlank String color,
            @RequestParam @Min(0) @Max(100) int cotton_part,
            @RequestParam @NotBlank String operation
    ) {
        Integer res;
        color = color.trim();
        switch (operation) {
            case ("equal") -> res = this.sockRepository.findByColorAndCottonPartEquals(color, cotton_part);
            case ("moreThan") -> res = this.sockRepository.findByColorAndCottonPartGreaterThan(color, cotton_part);
            case ("lessThan") -> res = this.sockRepository.findByColorAndCottonPartLessThan(color, cotton_part);
            default -> res = 0;
        }
        if (res == null) res = 0;
        return new ResponseEntity<>(res.toString(), HttpStatus.OK);
    }

    @Validated
    @PostMapping("/income")
    ResponseEntity income(
            @RequestParam @NotBlank String color,
            @RequestParam @Min(0) @Max(100) int cotton_part,
            @RequestParam @Min(1) int quantity
    ) {
        ResponseEntity re;
        Color oColor = this.sockService.findColorOrSave(color);
        CottonPart cottonPart = this.sockService.findCottonPartOrSave(cotton_part);
        this.sockService.income(oColor, cottonPart, quantity);

        return new ResponseEntity<>(null, HttpStatus.OK);

    }

    @Validated
    @PostMapping("/outcome")
    ResponseEntity outcome(
            @RequestParam @NotBlank String color,
            @RequestParam @Min(0) @Max(100) int cotton_part,
            @RequestParam @Min(1) int quantity
    ) {
        Color oColor = this.sockService.findColorOrSave(color);
        CottonPart cottonPart = this.sockService.findCottonPartOrSave(cotton_part);
        if (this.sockService.outcome(oColor, cottonPart, quantity)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleValidateException(Exception exc) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<Object> handleInternalServerException(Exception exc) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}