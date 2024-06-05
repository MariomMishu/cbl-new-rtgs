package com.cbl.cityrtgs.controllers.si;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.si.RejectRequest;
import com.cbl.cityrtgs.models.dto.si.SiEditRequest;
import com.cbl.cityrtgs.models.dto.si.SiRequest;
import com.cbl.cityrtgs.services.si.implementation.SiHistoryService;
import com.cbl.cityrtgs.services.si.implementation.SiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/standinginstruction")
public class SIController {

    private final SiService siService;
    private final SiHistoryService siHistoryService;

    @GetMapping("/search")
    public APIResponse search(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "10") Integer size,
                              @RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String status,
                              @RequestParam(defaultValue = "") String state,
                              @RequestParam(defaultValue = "") String expiryDate,
                              @RequestParam(defaultValue = "") String beneficiaryAccountNo,
                              @RequestParam(defaultValue = "") String payerAccountNo,
                              @RequestParam(defaultValue = "") String currency,
                              @RequestParam(defaultValue = "") String sortBy,
                              @RequestParam(defaultValue = "asc") String sortOrder){

        ResponseDTO response = siService.search(
                name.trim(),
                status,
                state,
                expiryDate,
                beneficiaryAccountNo.trim(),
                payerAccountNo.trim(),
                currency.trim(),
                page,
                size,
                sortBy,
                sortOrder);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @GetMapping("/search-submitted")
    public APIResponse searchSubmitted(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "10") Integer size,
                              @RequestParam(defaultValue = "") String name,
                              @RequestParam(defaultValue = "") String status,
                              @RequestParam(defaultValue = "") String expiryDate,
                              @RequestParam(defaultValue = "") String beneficiaryAccountNo,
                              @RequestParam(defaultValue = "") String payerAccountNo,
                              @RequestParam(defaultValue = "") String currency,
                              @RequestParam(defaultValue = "") String sortBy,
                              @RequestParam(defaultValue = "asc") String sortOrder){

        ResponseDTO response = siService.searchSubmittedSI(
                name.trim(),
                status,
                expiryDate,
                beneficiaryAccountNo.trim(),
                payerAccountNo.trim(),
                currency.trim(),
                page,
                size,
                sortBy,
                sortOrder);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @GetMapping("/search-rejected")
    public APIResponse searchRejected(@RequestParam(defaultValue = "0") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       @RequestParam(defaultValue = "") String name,
                                       @RequestParam(defaultValue = "") String status,
                                       @RequestParam(defaultValue = "") String expiryDate,
                                       @RequestParam(defaultValue = "") String beneficiaryAccountNo,
                                       @RequestParam(defaultValue = "") String payerAccountNo,
                                       @RequestParam(defaultValue = "") String currency,
                                       @RequestParam(defaultValue = "") String sortBy,
                                       @RequestParam(defaultValue = "asc") String sortOrder){

        ResponseDTO response = siService.searchRejectedSI(
                name.trim(),
                status,
                expiryDate,
                beneficiaryAccountNo.trim(),
                payerAccountNo.trim(),
                currency.trim(),
                page,
                size,
                sortBy,
                sortOrder);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @GetMapping("/search-log")
    public APIResponse searchLog(@RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 @RequestParam(defaultValue = "") String description,
                                 @RequestParam(defaultValue = "") String error,
                                 @RequestParam(defaultValue = "") String status,
                                 @RequestParam(defaultValue = "") String frequency,
                                 @RequestParam(defaultValue = "") String date){

        ResponseDTO response = siHistoryService.search(description.trim(), error.trim(), status.trim(), frequency.trim(), date.trim(), page, size);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @PutMapping("/approve/{id}")
    public APIResponse approve(@PathVariable("id") Long id){

        ResponseDTO dto = siService.approve(id);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .statusCode(204)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @PutMapping("/reject/{id}")
    public APIResponse reject(@PathVariable("id") Long id, @RequestBody @Valid RejectRequest request){

        ResponseDTO dto = siService.reject(id, request);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .statusCode(204)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @PutMapping("/cancel/{id}")
    public APIResponse cancel(@PathVariable("id") Long id){

        ResponseDTO dto = siService.cancel(id);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .statusCode(204)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @PutMapping("/activate/{id}")
    public APIResponse activate(@PathVariable("id") Long id){

        ResponseDTO dto = siService.activate(id);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .statusCode(204)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @PutMapping("/deactivate/{id}")
    public APIResponse deactivate(@PathVariable("id") Long id){

        ResponseDTO dto = siService.deactivate(id);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .statusCode(204)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }


    @GetMapping("/details/{id}")
    public APIResponse getDetailsById(@PathVariable("id") long id){

        ResponseDTO dto = siService.getStandingInstructionDetails(id);
        APIResponse apiResponse = APIResponse.builder().build();

        if(dto.isError()){

            apiResponse.setStatus(HttpStatus.NOT_FOUND);
            apiResponse.setStatusCode(404);
            apiResponse.setMessage("SI Not Found!");

            return apiResponse;
        }

        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setStatusCode(200);
        apiResponse.setMessage("");
        apiResponse.setBody(dto.getBody());

        return apiResponse;
    }

    @PostMapping
    public APIResponse create(@RequestBody @Valid SiRequest request){

        ResponseDTO response = siService.create(request);

        if(response.isError()){

            return APIResponse.builder()
                    .message(response.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .build();
        }

        return APIResponse.builder()
                .message(response.getMessage())
                .status(HttpStatus.CREATED)
                .statusCode(201)
                .body(response.getBody())
                .build();
    }

    @PutMapping("/{id}")
    public APIResponse edit(@PathVariable("id") Long id, @RequestBody @Valid SiEditRequest request){

        ResponseDTO response = siService.edit(id, request);

        if(response.isError()){

            return APIResponse.builder()
                    .message(response.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .build();
        }

        return APIResponse.builder()
                .message(response.getMessage())
                .status(HttpStatus.NO_CONTENT)
                .statusCode(204)
                .build();
    }

    @GetMapping("/log")
    public APIResponse getExecutedSiLog(){

        ResponseDTO dto = siHistoryService.findAllExecutedSI();

        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .body(dto.getBody())
                .build();
    }

    @PutMapping("/reprocess/{id}")
    public APIResponse reProcessSi(@PathVariable("id") long id){

        ResponseDTO dto = siService.reprocessStandingInstruction(id);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.NO_CONTENT)
                .statusCode(204)
                .message("SI reprocess successful!")
                .build();
    }

    @GetMapping("/fire")
    public APIResponse fire(){

        siService.startSiExecution();

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("SI Fired!")
                .build();
    }
}
