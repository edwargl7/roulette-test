package com.mt.roulette.controller;

import com.mt.roulette.controller.customExceptions.CustomInvalidDataException;
import com.mt.roulette.controller.customExceptions.CustomNotFoundException;
import com.mt.roulette.domain.DBet;
import com.mt.roulette.domain.DRoulette;
import com.mt.roulette.service.BetService;
import com.mt.roulette.service.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
        if (rouletteService.get(id) == null) {
            return new ResponseEntity<>("Roulette with id: (" + id + ") not found",
                    HttpStatus.NOT_FOUND);
        }
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
    public ResponseEntity<DBet> createBet(@RequestHeader(value = "User-Id") int userId,
                                          @PathVariable("id") int id, @RequestBody DBet bet) {
        DRoulette roulette = rouletteService.get(id);
        validateMoneyBet(bet.getMoneyBet());
        validateChosenValue(bet.getBetByNumber(), bet.getChosenValue());
        if (roulette == null) {
            throw new CustomNotFoundException("Roulette with id: (" + id + ") not found");
        } else if (!roulette.getIsOpen() || !roulette.getIsActive()) {
            throw new CustomInvalidDataException("Roulette not allowed");
        } else {
            return new ResponseEntity<>(betService.create(userId, id, bet), HttpStatus.CREATED);
        }
    }

    private void validateChosenValue(boolean betByNumer, String value){
        if (betByNumer) {
            validateNumber(value);
        } else {
            validateColor(value);
        }
    }

    private void validateColor(String value) {
        List<String> colors = Arrays.asList("black", "red");
        if (!colors.contains(value.toLowerCase())) {
            throw new CustomInvalidDataException("Color (" + value + ") invalid, choose " +
                    "'black' or 'red', or numbers between 0 - 36 and betByNumber = true");
        }
    }

    private void validateMoneyBet(double moneyBet) {
        if (moneyBet > 10000.0 || moneyBet < 0.0) {
            throw new CustomInvalidDataException("moneyBet is invalid, must be between 0.0 " +
                    "and 10000.0");
        }
    }

    private void validateNumber(String value) {
        try {
            int valueNumber = Integer.parseInt(value);
            if (valueNumber < 0 || valueNumber > 36) {
                throw new CustomInvalidDataException("chosenValue invalid, must be " +
                        "between 0 - 36, or color 'black' or 'red' and betByNumber = true");
            }
        } catch (NumberFormatException e) {
            throw new CustomInvalidDataException("chosenValue invalid, must be between 0 - 36");
        }
    }
}
