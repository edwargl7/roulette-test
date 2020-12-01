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

    @GetMapping("/{id}")
    public ResponseEntity<DRoulette> getRouletteById(@PathVariable("id") int id) {
        final DRoulette roulette = rouletteService.get(id);
        if (roulette == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(roulette, HttpStatus.OK);
    }

    @PutMapping("/{id}/opening")
    public ResponseEntity<String> rouletteOpening(@PathVariable("id") int id) {
        if (rouletteService.opening(id)) {
            return new ResponseEntity<>("successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("denied", HttpStatus.OK);
        }
    }
}
