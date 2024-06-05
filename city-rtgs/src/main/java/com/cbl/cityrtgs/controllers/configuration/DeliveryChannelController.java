package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.transaction.DeliveryChannelRequest;
import com.cbl.cityrtgs.models.dto.transaction.DeliveryChannelResponse;
import com.cbl.cityrtgs.services.transaction.DeliveryChannelService;
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
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/delivery-channel")
@RequiredArgsConstructor
public class DeliveryChannelController {
    private final DeliveryChannelService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createDeliveryChannel(@RequestBody @Valid DeliveryChannelRequest request) {
        try {
            service.create(request);
            return ResponseHandler.generateResponse(
                    "Delivery Channel has been created successfully",
                    HttpStatus.OK,
                    request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateDeliveryChannel(@PathVariable Long id, @RequestBody @Valid DeliveryChannelRequest request) {
        try {
            boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse(
                    "Delivery Channel has been updated successfully",
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
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged) {
        try {
            Page<DeliveryChannelResponse> deliveryChannelResponsePage = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, deliveryChannelResponsePage);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getList() {
        List<DeliveryChannelResponse> response = service.getList();
        return ResponseHandler.generateResponse(
                "Request process Successfully",
                HttpStatus.OK,
                response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long deliveryChannelId) {
        try {
            DeliveryChannelResponse response = service.getById(deliveryChannelId);
            if (Objects.nonNull(response)) {
                return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
            } else {
                return ResponseHandler.generateResponse("Delivery Channel Not Found", HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteDeliveryChannel(@PathVariable Long id) {
        boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse(
                "Delivery Channel Deleted Successfully",
                HttpStatus.OK,
                null);
    }

}
