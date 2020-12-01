package com.mt.roulette.controller;

import com.mt.roulette.domain.DRoulette;
import com.mt.roulette.service.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roulettes")
public class RouletteController {
    @Autowired
    private RouletteService rouletteService;

    @GetMapping("/")
    public ResponseEntity<List<DRoulette>> getRoulettes() {
        return new ResponseEntity<>(rouletteService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Integer> createRoulette() {
        return new ResponseEntity<>(rouletteService.create(), HttpStatus.CREATED);
    }
}
