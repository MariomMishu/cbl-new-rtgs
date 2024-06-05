package com.cbl.cityrtgs.common.utility;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class SessionUtility {

    public static void unauthorizedAccessReply(HttpServletResponse response){

        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            APIResponse apiResponse = APIResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED)
                    .statusCode(401)
                    .message("401! Unauthorized access")
                    .build();

            OutputStream out = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, apiResponse);
            out.flush();
        }
        catch (IOException e) {

            log.error("{}", e.getMessage());

            throw new RuntimeException(e);
        }
    }
}
