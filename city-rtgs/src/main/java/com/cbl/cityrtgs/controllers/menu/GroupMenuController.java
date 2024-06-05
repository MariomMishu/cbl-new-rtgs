package com.cbl.cityrtgs.controllers.menu;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.menu.request.GroupMenuRequest;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.services.menu.GroupMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/groupmenu")
@RequiredArgsConstructor
@Slf4j
public class GroupMenuController {

    private final GroupMenuService groupMenuService;

    @GetMapping
    public APIResponse getAll(){

        ResponseDTO dto = groupMenuService.getAllGroupMenu();

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse getById(@PathVariable("id") long id){

        ResponseDTO dto = groupMenuService.getById(id);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .statusCode(404)
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

    @GetMapping("/group/{groupId}")
    public APIResponse getByGroupId(@PathVariable("groupId") long groupId){

        ResponseDTO dto = groupMenuService.getByGroupId(groupId);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .statusCode(404)
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

    @PostMapping
    public APIResponse create(@RequestBody @Valid GroupMenuRequest request){

        ResponseDTO dto = groupMenuService.create(request);

        if(dto.isError()){

            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(dto.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.CREATED)
                .statusCode(201)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @PutMapping("/{groupId}")
    public APIResponse update(@PathVariable("groupId") long groupId, @RequestBody @Valid GroupMenuRequest request){

        ResponseDTO dto = groupMenuService.update(groupId, request);

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
}
