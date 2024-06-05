package com.cbl.cityrtgs.controllers.menu;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.menu.request.GroupRequest;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.services.menu.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Slf4j
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public APIResponse getAllActiveGroups(){

        ResponseDTO dto = groupService.getAllActiveGroups();

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse getById(@PathVariable("id") long id){

        ResponseDTO dto = groupService.getById(id);

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

    @PostMapping
    public APIResponse create(@RequestBody @Valid GroupRequest request){

        ResponseDTO dto = groupService.create(request);

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

    @PutMapping("/{id}")
    public APIResponse update(@PathVariable("id") long id, @RequestBody @Valid GroupRequest request){

        ResponseDTO dto = groupService.update(id, request);

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

    @DeleteMapping("/{id}")
    public APIResponse delete(@PathVariable("id") long id){

        ResponseDTO dto = groupService.delete(id);

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
