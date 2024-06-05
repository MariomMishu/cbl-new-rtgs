package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.userAud.UserAud;
import com.cbl.cityrtgs.services.user.UserAudService;
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
@RequestMapping("/user-aud")
@RequiredArgsConstructor
public class UserAudController {
    private final UserAudService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createAud(@RequestBody @Valid UserAud request) {
        try {
            service.createUserAud(request);
            return ResponseHandler.
                    generateResponse("User New Audit record has been created successfully",
                            HttpStatus.OK,
                            request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged) {
        try {
            Page<UserAud> responseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        UserAud response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateAud(@PathVariable Long id, @RequestBody @Valid UserAud request) {
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse("User Audit record has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteAud(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("User Audit record Deleted Successfully", HttpStatus.OK, null);
    }
}
