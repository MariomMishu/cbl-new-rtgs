package com.cbl.cityrtgs.controllers.dashboard;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.services.dashboard.abstraction.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/checkSTPGStatus")
    public APIResponse checkSTPGStatus() {

        ResponseDTO dto = dashboardService.checkSTPGStatus();

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.BAD_GATEWAY)
                    .statusCode(502)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/summary")
    public APIResponse getSummary(@RequestParam(value = "searchDate", defaultValue = "") String searchDate) {

        ResponseDTO dto = dashboardService.getSummary(searchDate);

        if(dto.isError()){
            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Request process Successfully")
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getCashLiquidity")
    public APIResponse getCashLiquidity(@RequestParam(value = "searchDate", defaultValue = "") String searchDate) {

        ResponseDTO dto = dashboardService.getCashLiquidity(searchDate);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getInwardC2CTransactions")
    public APIResponse getInwardC2CTransactions(@RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllInwardC2CTransactions(searchDate, shortCode);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Request process Successfully")
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getInwardC2CTransactionsDetails")
    public APIResponse getInwardC2CTransactionsDetails(@RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                       @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllInwardC2CTransactionsDetails(searchDate, shortCode);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Request process Successfully")
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getInwardC2CTransactionsDetailsBankWise")
    public APIResponse getInwardC2CTransactionsDetailsBankWise(@RequestParam(value = "bankId") Long bankId,
                                                               @RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                               @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllInwardC2CTransactionsDetailsBankWise(searchDate, shortCode, bankId);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Request process Successfully")
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getOutwardC2CTransactions")
    public APIResponse getOutwardC2CTransactions(@RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                 @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllOutwardC2CTransactions(searchDate, shortCode);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Request process Successfully")
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getOutwardC2CTransactionsDetails")
    public APIResponse getOutwardC2CTransactionsDetails(@RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                        @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllOutwardC2CTransactionsDetails(searchDate, shortCode);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Request process Successfully")
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getOutwardC2CTransactionsDetailsBankWise")
    public APIResponse getOutwardC2CTransactionsDetailsBankWise(@RequestParam(value = "bankId") Long bankId,
                                                                @RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                                @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllOutwardC2CTransactionsDetailsBankWise(searchDate, shortCode, bankId);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Request process Successfully")
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getInwardB2BTransactions")
    public APIResponse getInwardB2BTransactions(@RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllInwardB2BTransactions(searchDate, shortCode);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getInwardB2BTransactionsDetails")
    public APIResponse getInwardB2BTransactionsDetails(@RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                       @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllInwardB2BTransactionsDetails(searchDate, shortCode);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getInwardB2BTransactionsDetailsBankWise")
    public APIResponse getInwardB2BTransactionsDetailsBankWise(@RequestParam(value = "bankId") Long bankId,
                                                               @RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                               @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllInwardB2BTransactionsDetailsBankWise(searchDate, shortCode, bankId);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getOutwardB2BTransactions")
    public APIResponse getOutwardB2BTransactions(@RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                 @RequestParam(value = "currencyCode", defaultValue = "BDT") String currencyCode) {

        ResponseDTO dto = dashboardService.getAllOutwardB2BTransactions(searchDate, currencyCode);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Request process Successfully")
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getOutwardB2BTransactionsDetails")
    public APIResponse getOutwardB2BTransactionsDetails(@RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                        @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllOutwardB2BTransactionsDetails(searchDate, shortCode);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getOutwardB2BTransactionsDetailsBankWise")
    public APIResponse getOutwardB2BTransactionsDetailsBankWise(@RequestParam(value = "bankId") Long bankId,
                                                                @RequestParam(value = "searchDate", defaultValue = "") String searchDate,
                                                                @RequestParam(value = "shortCode", defaultValue = "BDT") String shortCode) {

        ResponseDTO dto = dashboardService.getAllOutwardB2BTransactionsDetailsBankWise(searchDate, shortCode, bankId);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/getActivityEventLogs")
    public APIResponse getActivityEventLogs(@RequestParam(value = "searchDate", defaultValue = "") String searchDate) {

        ResponseDTO dto = dashboardService.getApprovalEventLogs(searchDate);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }
}
