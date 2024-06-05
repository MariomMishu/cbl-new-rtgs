package com.cbl.cityrtgs.repositories.transaction;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.report.DepartmentList;
import com.cbl.cityrtgs.models.dto.report.ReconciledReport;
import com.cbl.cityrtgs.models.entitymodels.transaction.ReconcileDepartmentAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;


@Repository
public interface ReconcileDepartmentAccountRepository extends JpaRepository<ReconcileDepartmentAccountEntity, Long> {
    @Query(value = "SELECT * FROM TBL_RECON_DEPT_ACC " +
            "WHERE DEPARTMENT_ID =:DEPARTMENT_ID " +
            "AND ROUTING_TYPE =:ROUTING_TYPE AND CURRENCY_ID =:CURRENCY_ID AND TO_CHAR(CREATEDAT, 'YYYY-MM-dd') = " +
            "(SELECT TO_CHAR(sysdate, 'YYYY-MM-dd') FROM DUAL) ORDER BY ID ASC", nativeQuery = true)
    ReconcileDepartmentAccountEntity getReconcileDeptAccByRouteCurrencyDeptReconDate(@Param("ROUTING_TYPE") RoutingType ROUTING_TYPE,
                                                                                     @Param("CURRENCY_ID") Long CURRENCY_ID,
                                                                                     @Param("DEPARTMENT_ID") Long DEPARTMENT_ID);
    @Query(value = "SELECT " +
            "RDEPTACC.ACCOUNT_NUMBER AS accountNumber, RDEPTACC.AMOUNT AS amount, RDEPTACC.CHARGE_AMOUNT AS chargeAmount, RDEPTACC.VAT_AMOUNT AS vatAmount, RDEPTACC.CONFIRM_TXN_NO AS confirmTxnNo, RDEPTACC.RECONCILE_DATE AS reconcileDate, " +
            "RDEPTACC.RECONCILE_TIME AS reconcileTime, RDEPTACC.RECONCILE_USER AS reconcileUser, RDEPTACC.VOUCHER_NUMBER AS voucherNumber, RDEPTACC.ROUTING_TYPE AS routingType, CUR.SHORTCODE AS currency, DEP.NAME AS deptName, " +
            "RDEPTACC.DEPARTMENT_ID AS deptId, RDEPTACC.CHARGE_RECONCILE_TIME AS chargeReconcileTime, RDEPTACC.CHARGE_RECONCILE_USER AS chargeReconcileUser, RDEPTACC.CHARGE_VOUCHER_NUMBER AS chargeVoucherNumber, RDEPTACC.VAT_RECONCILE_TIME AS vatReconcileTime, " +
            "RDEPTACC.VAT_RECONCILE_USER AS vatReconcileUser, RDEPTACC.VAT_VOUCHER_NUMBER AS vatVoucherNumber " +
            "FROM TBL_RECON_DEPT_ACC RDEPTACC, TBL_RTGS_CURRENCIES CUR,TBL_DEPARTMENT DEP " +
            "WHERE " +
            "RDEPTACC.CURRENCY_ID = CUR.ID " +
            "AND RDEPTACC.DEPARTMENT_ID = DEP.ID " +
            "AND CUR.SHORTCODE like :currency " +
            "AND DEP.NAME like :dept " +
            "AND TRUNC(RDEPTACC.RECONCILE_DATE) >= :fromDate " +
            "AND TRUNC(RDEPTACC.RECONCILE_DATE) <= :toDate " +
            "ORDER BY RDEPTACC.DEPARTMENT_ID, RDEPTACC.RECONCILE_DATE DESC, RDEPTACC.RECONCILE_TIME DESC", nativeQuery = true)
    List<ReconciledReport> getReconciledReport(LocalDate fromDate, LocalDate toDate, String currency, String dept);
    @Query(value = "SELECT COUNT(T.ID) AS txnNumber, T.DeptName AS deptName " +
                        "FROM  (" +
                        "SELECT RDEPTACC.ID as id, DEP.NAME as DeptName " +
                        "FROM TBL_RECON_DEPT_ACC RDEPTACC, TBL_RTGS_CURRENCIES CUR, TBL_DEPARTMENT DEP " +
                        "WHERE RDEPTACC.CURRENCY_ID = CUR.ID " +
                        "AND RDEPTACC.DEPARTMENT_ID = DEP.ID " +
                        "AND CUR.SHORTCODE like :currency " +
                        "AND TRUNC(RDEPTACC.RECONCILE_DATE) >= :fromDate " +
                        "AND TRUNC(RDEPTACC.RECONCILE_DATE) <= :toDate " +
                        "ORDER BY RDEPTACC.DEPARTMENT_ID, RDEPTACC.RECONCILE_DATE DESC, RDEPTACC.RECONCILE_TIME DESC) T " +
                    "GROUP BY T.DeptName ", nativeQuery = true)
    List<DepartmentList> getReconciledDepartmentList(LocalDate fromDate, LocalDate toDate, String currency);
}
