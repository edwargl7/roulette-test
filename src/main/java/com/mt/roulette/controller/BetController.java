package com.mt.roulette.controller;

import com.mt.roulette.controller.customExceptions.CustomNotFoundException;
import com.mt.roulette.domain.DBet;
import com.mt.roulette.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bets")
public class BetController {
    @Autowired
    private BetService betService;

    @GetMapping("/")
    public ResponseEntity<List<DBet>> getBets() {
        return new ResponseEntity<>(betService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DBet> getBetById(@PathVariable("id") int id) {
        final DBet bet = betService.get(id);
        if (bet == null) {
            throw new CustomNotFoundException("Bet with id: (" + id + ") not found");
        } else {
            return new ResponseEntity<>(bet, HttpStatus.OK);
        }
    }
}
