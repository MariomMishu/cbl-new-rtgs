package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentFilter;
import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentRequest;
import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentResponse;
import com.cbl.cityrtgs.services.configuration.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createDepartment(@RequestBody @Valid DepartmentRequest request) {
        try {
            service.createDepartment(request);
            return ResponseHandler.generateResponse(
                    "Department has been created successfully",
                    HttpStatus.OK,
                    request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged,
                                    @RequestParam(value = "name", required = false) final String name) {
        DepartmentFilter filter = new DepartmentFilter();
        filter.setName(name);
        try {
            Page<DepartmentResponse> departmentResponseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    departmentResponseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long deptId) {
        DepartmentResponse response = service.getById(deptId);
        return ResponseHandler.generateResponse(
                "Request process Successfully",
                HttpStatus.OK,
                response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentRequest request) {
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse(
                    "Department has been updated successfully",
                    HttpStatus.OK,
                    request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse(
                "Department Deleted Successfully",
                HttpStatus.OK,
                null);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getDeptList() {
        List<DepartmentResponse> response = service.getDeptList();
        return ResponseHandler.generateResponse(
                "Request process Successfully",
                HttpStatus.OK,
                response);
    }
}
