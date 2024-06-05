package com.cbl.cityrtgs.models.dto.dashboard;

import com.cbl.cityrtgs.models.dto.projection.BalanceProjection;
import com.cbl.cityrtgs.models.dto.projection.CashLiquidityProjection;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CashLiquidityResponse {

    private List<BalanceProjection> balance = new ArrayList<>();
    private List<CashLiquidityProjection> cashLiquidity;
}
