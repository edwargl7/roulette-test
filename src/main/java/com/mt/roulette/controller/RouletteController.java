package com.mt.roulette.controller;

import com.mt.roulette.domain.DBet;
import com.mt.roulette.domain.DRoulette;
import com.mt.roulette.service.BetService;
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

    @Autowired
    private BetService betService;

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

    @GetMapping("/{id}/bets/")
    public ResponseEntity<List<DBet>> getBetByRoulette(@PathVariable("id") int id) {
        final List<DBet> bets = betService.getAllByRoulette(id);
        if (bets == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(bets, HttpStatus.OK);
        }
    }

    @PostMapping("/{id}/bets/")
    public ResponseEntity<DBet> createBet(@RequestHeader(value = "User-Id") int userId, @PathVariable("id") int id, @RequestBody DBet bet) {
        System.out.println("User-Id" + userId);
        System.out.println("id" + id);
        System.out.println("Bet Money" + bet.getMoneyBet());
        System.out.println("Bet Value" + bet.getChosenValue());
        System.out.println("Bet Number" + bet.getBetByNumber());

        DRoulette roulette = rouletteService.get(id);
        if (roulette == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (!roulette.getIsOpen() || !roulette.getIsActive()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(betService.create(userId, id, bet), HttpStatus.CREATED);
        }
    }
}
