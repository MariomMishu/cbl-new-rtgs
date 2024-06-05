package com.cbl.cityrtgs.models.dto.menu.request;

import lombok.*;
import lombok.experimental.Accessors;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupAccessRequest {

    private List<Long> users;
}
