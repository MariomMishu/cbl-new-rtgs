package com.cbl.cityrtgs.controllers.menu;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.menu.request.MenuActionRequest;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.services.menu.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/menuaction")
@RequiredArgsConstructor
@Slf4j
public class MenuActionController {

    private final ActionService actionService;

    @GetMapping
    public APIResponse getAll(){

        ResponseDTO dto = actionService.getAll();

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse getById(@PathVariable("id") long id){

        ResponseDTO dto = actionService.getById(id);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(dto.getMessage())
                .body(dto.getBody())
                .build();
    }

    @PostMapping
    public APIResponse create(@RequestBody @Valid MenuActionRequest request){

        ResponseDTO dto = actionService.create(request);

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

    @PutMapping("/{id}")
    public APIResponse update(@PathVariable("id") long id, @RequestBody @Valid MenuActionRequest request){

        ResponseDTO dto = actionService.update(id, request);

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

        ResponseDTO dto = actionService.delete(id);

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
                .build();
    }
}
