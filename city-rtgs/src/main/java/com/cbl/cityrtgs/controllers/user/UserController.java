package com.cbl.cityrtgs.controllers.user;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.configuration.userInfo.*;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.services.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;

    @GetMapping("/unapproved-users")
    public APIResponse getUnapprovedUsers(@RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size) {

        ResponseDTO response = userInfoService.getAllUnapprovedUsers(page, size);

        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @GetMapping("/rejected-users")
    public APIResponse getRejectedUsers(@RequestParam(defaultValue = "0") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size) {
        ResponseDTO response = userInfoService.getAllRejectedUsers(page, size);
        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @PostMapping
    public APIResponse create(@RequestBody UserInfoRequest request) {
        ResponseDTO response = userInfoService.create(request);

        if (response.isError()) {
            return APIResponse.builder()
                    .statusCode(500)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(response.getMessage())
                    .build();
        }
        return APIResponse.builder()
                .statusCode(201)
                .status(HttpStatus.CREATED)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @PutMapping("/{id}")
    public APIResponse update(@PathVariable Long id, @RequestBody UserInfoRequest request) {

        ResponseDTO response = userInfoService.update(id, request);

        if (response.isError()) {
            return APIResponse.builder()
                    .statusCode(400)
                    .status(HttpStatus.BAD_REQUEST)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .statusCode(204)
                .status(HttpStatus.NO_CONTENT)
                .message("User has been updated successfully")
                .body(response.getBody())
                .build();
    }

    @PutMapping("/approve-user/{id}")
    public APIResponse approve(@PathVariable("id") Long id) {

        ResponseDTO response = userInfoService.activateUser(id);

        if (response.isError()) {

            return APIResponse.builder()
                    .statusCode(500)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .statusCode(204)
                .status(HttpStatus.NO_CONTENT)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @PutMapping("/reject-user/{id}")
    public APIResponse reject(@PathVariable("id") Long id) {

        ResponseDTO response = userInfoService.deactivateUser(id);

        if (response.isError()) {

            return APIResponse.builder()
                    .statusCode(500)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .statusCode(204)
                .status(HttpStatus.NO_CONTENT)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @DeleteMapping("/delete-user/{id}")
    public APIResponse delete(@PathVariable("id") Long id) {

        ResponseDTO response = userInfoService.deleteUser(id);

        if (response.isError()) {

            return APIResponse.builder()
                    .statusCode(500)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .statusCode(204)
                .status(HttpStatus.NO_CONTENT)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @PostMapping("/change-password")
    public APIResponse changePassword(@RequestBody @Valid ChangePasswordRequest request) {

        ResponseDTO response = userInfoService.changePassword(request);

        if (response.isError()) {

            return APIResponse.builder()
                    .statusCode(500)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .statusCode(204)
                .status(HttpStatus.NO_CONTENT)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @PutMapping("/update-password/{userId}")
    public APIResponse updatePassword(@PathVariable("userId") Long userId, @RequestBody @Valid PasswordUpdateRequest request) {

        ResponseDTO response = userInfoService.updatePasswordByUserId(userId, request);

        if (response.isError()) {

            return APIResponse.builder()
                    .statusCode(500)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .statusCode(204)
                .status(HttpStatus.NO_CONTENT)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @PostMapping("/password-reset-request")
    public APIResponse passwordResetRequest(@RequestBody PasswordResetRequest request) throws MessagingException {
        APIResponse model = new APIResponse(HttpStatus.OK, 200, "Operation Successful", null);
        ResponseDTO response = userInfoService.passwordResetRequest(request);

        if (!response.isError()) {
            model.setMessage(response.getMessage());
            model.setBody(response.getBody());
        } else {
            model = APIResponse.builder()
                    .statusCode(500)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(response.getMessage())
                    .build();
        }

        return model;
    }

    @PostMapping("/reset-password")
    public APIResponse resetPassword(@RequestBody @Valid NewPasswordResetRequest request) {

        ResponseDTO response = userInfoService.resetPassword(request);

        if (response.isError()) {

            return APIResponse.builder()
                    .statusCode(500)
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @GetMapping
    public APIResponse getAll(Pageable pageable,
                              @RequestParam(value = "unPaged", required = false) final boolean unPaged,
                              @RequestParam(value = "deptName", required = false) final String deptName,
                              @RequestParam(value = "branchName", required = false) final String branchName,
                              @RequestParam(value = "fullName", required = false) final String fullName,
                              @RequestParam(value = "username", required = false) final String username,
                              @RequestParam(value = "recStatus", required = false) final UserStatus recStatus,
                              @RequestParam(value = "createdBy", required = false) final String createdBy,
                              @RequestParam(value = "updatedBy", required = false) final String updatedBy) {
        UserInfoFilter filter = new UserInfoFilter();
        filter.setFullName(fullName);
        filter.setUsername(username);
        filter.setRecStatus(recStatus);
        filter.setDeptName(deptName);
        filter.setBranchName(branchName);
        filter.setCreatedBy(createdBy);
        filter.setUpdatedBy(updatedBy);

        ResponseDTO response = userInfoService.getApprovedUserList(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);

        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @GetMapping("/created")
    public APIResponse getAllCreatedUserList(Pageable pageable,
                                             @RequestParam(value = "unPaged", required = false) final boolean unPaged,
                                             @RequestParam(value = "deptName", required = false) final String deptName,
                                             @RequestParam(value = "branchName", required = false) final String branchName,
                                             @RequestParam(value = "fullName", required = false) final String fullName,
                                             @RequestParam(value = "username", required = false) final String username,
                                             @RequestParam(value = "recStatus", required = false) final UserStatus recStatus,
                                             @RequestParam(value = "createdBy", required = false) final String createdBy,
                                             @RequestParam(value = "updatedBy", required = false) final String updatedBy) {
        UserInfoFilter filter = new UserInfoFilter();
        filter.setFullName(fullName);
        filter.setUsername(username);
        filter.setRecStatus(recStatus);
        filter.setDeptName(deptName);
        filter.setBranchName(branchName);
        filter.setCreatedBy(createdBy);
        filter.setUpdatedBy(updatedBy);

        ResponseDTO response = userInfoService.getAllCreatedUserList(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }

    @PutMapping("/{id}/sts-update")
    public APIResponse userStsUpdate(@PathVariable Long id, @RequestParam UserStatus status) {
        String sts = "";
        if (!userInfoService.existOne(id))

            return APIResponse.builder()
                    .statusCode(404)
                    .status(HttpStatus.NOT_FOUND)
                    .message("User Not Found")
                    .build();

        if (status.equals(UserStatus.Locked)) {
            sts = "Locked";
            userInfoService.lockedUser(id);
        } else if (status.equals(UserStatus.UnLocked)) {
            sts = "UnLocked";
            userInfoService.unLockedUser(id);
        } else if (status.equals(UserStatus.Deleted)) {
            sts = "Deleted";
            userInfoService.deleteOne(id);
        }

        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message("User " + sts + " Successfully")
                .build();
    }

    @GetMapping("/{id}")
    public APIResponse getById(@PathVariable Long id) {
        ResponseDTO response = userInfoService.getUserById(id);
        if (response.isError()) {
            return APIResponse.builder()
                    .statusCode(404)
                    .status(HttpStatus.NOT_FOUND)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .statusCode(200)
                .status(HttpStatus.OK)
                .message(response.getMessage())
                .body(response.getBody())
                .build();
    }
}
