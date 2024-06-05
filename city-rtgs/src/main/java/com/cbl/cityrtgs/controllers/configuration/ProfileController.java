package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.profile.ProfileRequest;
import com.cbl.cityrtgs.models.dto.configuration.profile.ProfileResponse;
import com.cbl.cityrtgs.services.user.ProfileService;
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
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProfile(@RequestBody @Valid ProfileRequest request) {
        ProfileResponse response= new ProfileResponse();
        try {
            response= service.createProfile(request);
            return ResponseHandler.
                    generateResponse("Profile has been created successfully",
                            HttpStatus.OK,
                            response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NO_CONTENT, response);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged,
                                    @RequestParam(value = "name", required = false) final String name) {
        try {
            Page<ProfileResponse> responseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, name);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ProfileResponse response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody @Valid ProfileRequest request) {
        ProfileResponse response = new ProfileResponse();
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            response= service.updateProfile(id, request);
            return ResponseHandler.generateResponse("Profile has been updated successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Profile Deleted Successfully", HttpStatus.OK, null);
    }
}
