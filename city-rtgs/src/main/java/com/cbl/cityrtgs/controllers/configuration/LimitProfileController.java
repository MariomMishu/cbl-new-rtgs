package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.limitprofile.LimitProfileRequest;
import com.cbl.cityrtgs.models.dto.configuration.limitprofile.LimitProfileResponse;
import com.cbl.cityrtgs.services.configuration.LimitProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/limit-profile")
@RequiredArgsConstructor
public class LimitProfileController {
    private final LimitProfileService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createLimitProfile(@RequestBody @Valid LimitProfileRequest request) {
        LimitProfileResponse response=  new LimitProfileResponse();
        try {
            response = service.createLimitProfile(request);
            return ResponseHandler.
                    generateResponse("Profile Limit has been created successfully",
                            HttpStatus.OK,
                            response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged,
                                    @RequestParam(value = "profileId", required = false) final Long profileId) {
        try {
            Page<LimitProfileResponse> responseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable,profileId);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        LimitProfileResponse response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateLimitProfile(@PathVariable Long id, @RequestBody @Valid LimitProfileRequest request) {
        LimitProfileResponse response = new LimitProfileResponse();
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            response=  service.updateOne(id, request);
            return ResponseHandler.generateResponse("Profile Limit has been updated successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteLimitProfile(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Profile Limit Deleted Successfully", HttpStatus.OK, null);
    }
}
