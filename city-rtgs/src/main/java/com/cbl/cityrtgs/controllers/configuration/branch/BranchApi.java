package com.cbl.cityrtgs.controllers.configuration.branch;

import com.cbl.cityrtgs.models.dto.configuration.branch.BranchRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "branch")
public interface BranchApi {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getAllBranch(
            Pageable pageable,
            @RequestParam(value = "unPaged", required = false) final boolean unPaged,
            @RequestParam(value = "routingNumber", required = false) final String routingNumber,
            @RequestParam(value = "address", required = false) final String address,
            @RequestParam(value = "name", required = false) final String name);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<?> createBranch(@RequestBody @Valid BranchRequest createRequest);


    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getBranch(@PathVariable Long id);


    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> updateBranch(@PathVariable Long id,
                                   @RequestBody @Valid BranchRequest updateRequest);


    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> deleteBranch(@PathVariable Long id);

    @GetMapping("/bank/{bankId}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getAllBranchByBankId(
            Pageable pageable,
            @PathVariable Long bankId,
            @RequestParam(value = "unPaged", required = false) final boolean unPaged,
            @RequestParam(value = "routingNumber", required = false) final String routingNumber,
            @RequestParam(value = "address", required = false) final String address,
            @RequestParam(value = "name", required = false) final String name

    );

    @GetMapping("/routing/{routingNumber}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getBranchByRouting(@PathVariable String routingNumber);

    @GetMapping("/bankId")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<?> getAllBranchListByBankId(@RequestParam(value = "bankId") final Long bankId);
}
