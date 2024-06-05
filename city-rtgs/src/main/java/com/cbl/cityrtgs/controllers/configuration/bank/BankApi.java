package com.cbl.cityrtgs.controllers.configuration.bank;

import com.cbl.cityrtgs.models.dto.configuration.bank.BankRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RequestMapping(value = "bank")
public interface BankApi {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getBanks(
            Pageable pageable,
            @RequestParam(value = "unPaged", required = false) final boolean unPaged,
            @RequestParam(value = "bic", required = false) final String bic,
            @RequestParam(value = "bankCode", required = false) final String bankCode,
            @RequestParam(value = "address", required = false) final String address,
            @RequestParam(value = "name", required = false) final String name);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> createBank(@RequestBody @Valid BankRequest createRequest);

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getBank(@PathVariable Long id);

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> updateBank(@PathVariable Long id,
                                 @RequestBody @Valid BankRequest updateRequest);

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> deleteBank(@PathVariable Long id);

    @GetMapping("/owner-bank")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getOwnerBank();

    @GetMapping("/sattlement-bank")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getSattlementBank();

    @GetMapping("/ben-bank")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getAllBenBankList();

    @GetMapping("/bic/{bic}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getBankByBic(@PathVariable String bic);
}
