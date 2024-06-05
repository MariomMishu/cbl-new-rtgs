package com.cbl.cityrtgs.controllers.menu;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.menu.request.GroupAccessRequest;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.services.menu.GroupAccessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/groupaccess")
@RequiredArgsConstructor
@Slf4j
public class GroupAccessController {

    private final GroupAccessService groupAccessService;

    @GetMapping
    public APIResponse getAll(){

        ResponseDTO dto = groupAccessService.getAllGroupAccess();

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/user-access-list/{groupId}")
    public APIResponse getAllUserListByGroup(@PathVariable("groupId") Long groupId){

        ResponseDTO dto = groupAccessService.getUsersByGroupId(groupId);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/{groupAccessId}")
    public APIResponse getByGroupAccessId(@PathVariable("groupAccessId") long groupAccessId){

        ResponseDTO dto = groupAccessService.getById(groupAccessId);

        if(dto.isError()){
            return APIResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .statusCode(404)
                    .message(dto.getMessage())
                    .body(dto.getBody())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/user/{userId}")
    public APIResponse getAccessByUserId(@PathVariable("userId") long userId){

        ResponseDTO dto = groupAccessService.getByUserId(userId);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @PutMapping("/{groupId}")
    public APIResponse update(@PathVariable("groupId") long groupId, @RequestBody @Valid GroupAccessRequest request){

        ResponseDTO dto = groupAccessService.update(groupId, request);

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
}
