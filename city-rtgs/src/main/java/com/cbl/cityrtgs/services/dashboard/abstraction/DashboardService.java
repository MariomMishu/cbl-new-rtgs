package com.cbl.cityrtgs.services.dashboard.abstraction;

import com.cbl.cityrtgs.models.dto.response.ResponseDTO;

public interface DashboardService {

    ResponseDTO getSummary(String dateString);
    ResponseDTO getCashLiquidity(String dateString);
    ResponseDTO getAllInwardC2CTransactions(String dateString, String shortCode);
    ResponseDTO getAllOutwardC2CTransactions(String dateString, String shortCode);
    ResponseDTO getAllInwardB2BTransactions(String dateString, String shortCode);
    ResponseDTO getAllInwardB2BTransactionsDetails(String dateString, String shortCode);
    ResponseDTO getAllInwardB2BTransactionsDetailsBankWise(String dateString, String shortCode, Long bankId);
    ResponseDTO getAllOutwardB2BTransactions(String dateString, String shortCode);
    ResponseDTO getAllOutwardB2BTransactionsDetails(String dateString, String shortCode);
    ResponseDTO getAllInwardC2CTransactionsDetails(String dateString, String shortCode);
    ResponseDTO getAllInwardC2CTransactionsDetailsBankWise(String dateString, String shortCode, Long bankId);
    ResponseDTO getAllOutwardC2CTransactionsDetails(String dateString, String shortCode);
    ResponseDTO getAllOutwardC2CTransactionsDetailsBankWise(String dateString, String shortCode, Long bankId);
    ResponseDTO getAllOutwardB2BTransactionsDetailsBankWise(String dateString, String shortCode, Long bankId);
    ResponseDTO getApprovalEventLogs(String dateString);
    ResponseDTO checkSTPGStatus();
}

