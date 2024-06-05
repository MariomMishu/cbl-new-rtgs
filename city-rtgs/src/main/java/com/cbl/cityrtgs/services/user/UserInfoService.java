package com.cbl.cityrtgs.services.user;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.userInfo.*;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.UserInfoMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.RoleEntity;
import com.cbl.cityrtgs.models.entitymodels.menu.GroupAccess;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.*;
import com.cbl.cityrtgs.repositories.menu.GroupAccessRepository;
import com.cbl.cityrtgs.repositories.specification.CreatedUserInfoSpecification;
import com.cbl.cityrtgs.repositories.specification.UserInfoSpecification;
import com.cbl.cityrtgs.services.email.EmailService;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;

@Slf4j
@Service
//@Transactional
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final RolesRepository roleRepository;
    private final UserAudService userAudService;
    private final PasswordEncoder passwordEncoder;
    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;
    private final ProfileRepository profileRepository;
    private final UserInfoMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final GroupAccessRepository groupAccessRepository;

    @Qualifier("passwordResetMailService")
    private final EmailService emailService;


    public ResponseDTO getAllUnapprovedUsers(int pageNo, int pageSize) {

        List<UserInfoResponse> responses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserInfoEntity> pages = userInfoRepository.findAllUnapprovedUsers(pageable);

        if (!pages.isEmpty()) {

            pages.forEach(user -> {
                responses.add(mapper.entityToDomain(user));
            });
        }

        response.put("result", responses);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

        return ResponseDTO.builder().error(false).message(responses.size() + " data found!").body(response).build();
    }

    public ResponseDTO getAllRejectedUsers(int pageNo, int pageSize) {

        List<UserInfoResponse> responses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserInfoEntity> pages = userInfoRepository.findAllRejectedUsers(pageable);

        if (!pages.isEmpty()) {

            pages.forEach(user -> {
                responses.add(mapper.entityToDomain(user));
            });
        }

        response.put("result", responses);
        response.put("currentPage", pages.getNumber());
        response.put("totalItems", pages.getTotalElements());
        response.put("totalPages", pages.getTotalPages());

        return ResponseDTO.builder().error(false).message(responses.size() + " data found!").body(response).build();
    }

    public ResponseDTO activateUser(Long id) {

        Optional<UserInfoEntity> optional = userInfoRepository.findById(id);

        if (optional.isEmpty()) {

            return ResponseDTO.builder().error(true).message("Invalid User").build();
        }

        try {

            userInfoRepository.activateUser(id, UserStatus.Active);

            return ResponseDTO.builder().error(false).message("User activated").build();
        } catch (Exception e) {
            return ResponseDTO.builder().error(true).message(e.getMessage()).build();
        }
    }

    public ResponseDTO deactivateUser(Long id) {

        Optional<UserInfoEntity> optional = userInfoRepository.findById(id);

        if (optional.isEmpty()) {

            return ResponseDTO.builder().error(true).message("Invalid User").build();
        }

        try {

            userInfoRepository.deactivateUser(id, UserStatus.Rejected);

            return ResponseDTO.builder().error(false).message("User rejected").build();
        } catch (Exception e) {

            return ResponseDTO.builder().error(true).message(e.getMessage()).build();
        }
    }

    public ResponseDTO deleteUser(Long id) {

        Optional<UserInfoEntity> optional = userInfoRepository.findById(id);

        if (optional.isEmpty()) {

            return ResponseDTO.builder().error(true).message("Invalid User").build();
        }

        try {

            userInfoRepository.deleteUser(id, UserStatus.Deleted);

            return ResponseDTO.builder().error(false).message("User deleted").build();
        } catch (Exception e) {

            return ResponseDTO.builder().error(true).message(e.getMessage()).build();
        }
    }

    public UserInfoResponse getById(Long id) {

        return userInfoRepository.findByIdAndIsDeletedFalse(id).map(mapper::entityToDomain).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public ResponseDTO create(UserInfoRequest request) {


        if (userInfoRepository.isUserAdmin(LoggedInUserDetails.getUserInfoDetails().getId()) == 0) {

            return ResponseDTO.builder().error(true).message("Access Denied! Admin privilege required for user creation.").build();
        }

        if (request.getFullName() == null || request.getFullName().equals("")) {

            return ResponseDTO.builder().error(true).message("Please input full name").build();
        }

        if (request.getEmployeeId() == null || request.getEmployeeId().equals("")) {

            return ResponseDTO.builder().error(true).message("Please input employee id").build();
        }

        if (request.getProfileId() == null) {

            return ResponseDTO.builder().error(true).message("Please input profile id").build();
        }

        if (request.getCellNo() == null) {

            return ResponseDTO.builder().error(true).message("Please input cell no").build();
        }

        if (request.getEmailAddress() == null || request.getEmailAddress().equals("")) {

            return ResponseDTO.builder().error(true).message("Please input email").build();
        }

        if (request.getUsername() == null || request.getUsername().equals("")) {

            return ResponseDTO.builder().error(true).message("Please input username").build();
        }

        if (request.getPassword() == null || request.getPassword().equals("")) {

            return ResponseDTO.builder().error(true).message("Please input password").build();
        }

        if (request.getGroupId() == null) {

            return ResponseDTO.builder().error(true).message("Please input group").build();
        }

        if (request.getBranchId() == null) {

            return ResponseDTO.builder().error(true).message("Please input branch").build();
        }

        if (request.getDeptId() == null) {

            return ResponseDTO.builder().error(true).message("Please input department").build();
        }

        if (request.getRoleIds().isEmpty()) {

            return ResponseDTO.builder().error(true).message("Please assign roles").build();
        }

        if (userInfoRepository.existsByUsername(request.getUsername())) {

            return ResponseDTO.builder().error(true).message("Username already taken").build();
        }

        if (userInfoRepository.existsByEmailAddr(request.getEmailAddress())) {

            return ResponseDTO.builder().error(true).message("Email already taken").build();
        }

        if (userInfoRepository.existsByCellNo(request.getCellNo())) {

            return ResponseDTO.builder().error(true).message("Cell No already taken").build();
        }


        try {

            Set<RoleEntity> roles = new HashSet<>();

            request.getRoleIds().forEach(roleId -> {

                Optional<RoleEntity> roleOptional = roleRepository.findByIdAndIsDeletedFalse(roleId);
                roleOptional.ifPresent(roles::add);
            });

            ProfileEntity profile = profileRepository.findById(request.getProfileId()).isPresent() ? profileRepository.findById(request.getProfileId()).get() : null;
            GroupAccess groupAccess = groupAccessRepository.findByGroupId(request.getGroupId()).isPresent() ? groupAccessRepository.findByGroupId(request.getGroupId()).get() : null;
            DepartmentEntity department = departmentRepository.findByIdAndIsDeletedFalse(request.getDeptId()).isPresent() ? departmentRepository.findByIdAndIsDeletedFalse(request.getDeptId()).get() : null;
            BranchEntity branch = branchRepository.findByIdAndIsDeletedFalse(request.getBranchId()).isPresent() ? branchRepository.findByIdAndIsDeletedFalse(request.getBranchId()).get() : null;
            request.setPassword(passwordEncoder.encode(request.getPassword()));

            UserInfoEntity model = UserInfoRequest.toMODEL(request, roles, profile, groupAccess, department, branch);

            UserInfoResponse response = mapper.entityToDomain(userInfoRepository.save(model));
            userAudService.saveUserAud(model);

            return ResponseDTO.builder().error(false).message("User created!").body(response).build();

        } catch (Exception e) {

            return ResponseDTO.builder().error(true).message(e.getMessage()).build();
        }
    }

    public ResponseDTO update(Long id, UserInfoRequest request) {

        if (userInfoRepository.isUserAdmin(LoggedInUserDetails.getUserInfoDetails().getId()) == 0) {

            return ResponseDTO.builder().error(true).message("Access Denied! Admin privilege required for user update.").build();
        }

        Optional<UserInfoEntity> optional = userInfoRepository.findByIdAndIsDeletedFalse(id);

        if (optional.isEmpty()) {

            return ResponseDTO.builder().error(true).message("Invalid user ID").build();
        }

        if (userInfoRepository.checkDuplicateEmailAddr(request.getEmailAddress(), id) > 0) {

            return ResponseDTO.builder().error(true).message("Email already taken").build();
        }

        if (userInfoRepository.checkDuplicateCellNo(request.getCellNo(), id) > 0) {

            return ResponseDTO.builder().error(true).message("Cell No already taken").build();
        }

        try {

            UserInfoEntity entity = optional.get();

            Set<RoleEntity> roles = new HashSet<>();

            request.getRoleIds().forEach(roleId -> {

                Optional<RoleEntity> roleOptional = roleRepository.findByIdAndIsDeletedFalse(roleId);
                roleOptional.ifPresent(roles::add);
            });

            ProfileEntity profile = profileRepository.findById(request.getProfileId()).isPresent() ? profileRepository.findById(request.getProfileId()).get() : null;
            GroupAccess groupAccess = groupAccessRepository.findByGroupId(request.getGroupId()).isPresent() ? groupAccessRepository.findByGroupId(request.getGroupId()).get() : null;
            DepartmentEntity department = departmentRepository.findByIdAndIsDeletedFalse(request.getDeptId()).isPresent() ? departmentRepository.findByIdAndIsDeletedFalse(request.getDeptId()).get() : null;
            BranchEntity branch = branchRepository.findByIdAndIsDeletedFalse(request.getBranchId()).isPresent() ? branchRepository.findByIdAndIsDeletedFalse(request.getBranchId()).get() : null;

            entity = UserInfoRequest.toMODEL(entity, roles, profile, groupAccess, department, branch, request);
            entity = userInfoRepository.save(entity);

            log.info("User {} Updated", entity.getUsername());

            UserInfoResponse response = mapper.entityToDomain(entity);
            userAudService.saveUserAud(entity);

            return ResponseDTO.builder().error(false).message("User Updated!").body(response).build();
        } catch (Exception e) {

            return ResponseDTO.builder().error(true).message(e.getMessage()).build();
        }
    }

    public ResponseDTO getUserById(Long id) {

        Optional<UserInfoEntity> entityOptional = userInfoRepository.findByIdAndIsDeletedFalse(id);

        if (entityOptional.isPresent()) {
            UserInfoEntity entity = entityOptional.get();
            UserInfoResponse response = mapper.entityToDomain(entity);

            return ResponseDTO.builder().error(false).message("Request Process Successfully").body(response).build();
        } else {
            return ResponseDTO.builder().error(true).message("User not found").build();
        }
    }

    public ResponseDTO passwordResetRequest(PasswordResetRequest request) throws MessagingException {
        ResponseDTO response = new ResponseDTO(false, "Operation Successful", null);

        if (!StringUtils.isEmpty(request.getEmployeeId())) {
            if (!StringUtils.isEmpty(request.getCellNo())) {
                UserInfoEntity userInfoEntity = userInfoRepository.findByEmployeeIdAndCellNo(request.getEmployeeId(), request.getCellNo());
                if (userInfoEntity != null) {
                    //UserInfoEntity userInfoEntity = optional.get();
                    if (userInfoEntity.isActivated()) {
                        String recStatus = userInfoEntity.getRecStatus().name();
                        if (recStatus.equals("Locked") || recStatus.equals("Active") || recStatus.equals("Inactive")) {
                            if (ValidationUtility.isEmailValid(userInfoEntity.getEmailAddr().trim())) {
                                ResponseDTO emailResponse = emailService.sendMail(userInfoEntity.getEmailAddr().trim());
                                if (!emailResponse.isError()) {
                                    response.setMessage(emailResponse.getMessage());
                                } else {
                                    response.setError(true);
                                    response.setMessage(emailResponse.getMessage());
                                }
                            } else {
                                response.setError(true);
                                response.setMessage("Invalid email account!");
                            }
                        } else {
                            response.setError(true);
                            response.setMessage("Account " + recStatus);
                        }

                    } else {
                        response.setError(true);
                        response.setMessage("Account Inactive!");
                    }

                } else {
                    response.setError(true);
                    response.setMessage("User not found!");
                }
            } else {
                response.setError(true);
                response.setMessage("Please provide Cell No!");
            }

        } else {
            response.setError(true);
            response.setMessage("Please provide Cell No!");
        }
        return response;


       /* if (StringUtils.isEmpty(request.getEmployeeId())) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide Employee ID!")
                    .build();
        }

        if (request.getCellNo() == null || request.getCellNo().isEmpty()) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Please provide Cell No!")
                    .build();
        }*/

        //Optional<UserInfoEntity> optional = userInfoRepository.findByEmployeeIdAndCellNo(request.getEmployeeId(), request.getCellNo());

       /* if (optional.isEmpty()) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("User not found!")
                    .build();
        }*/

        //UserInfoEntity userInfoEntity = optional.get();

        /*if (!userInfoEntity.isActivated()) {

            return ResponseDTO.builder()
                    .error(true)
                    .message("Account Inactive!")
                    .build();
        }*/

        /*String recStatus = userInfoEntity.getRecStatus().name();

        if (recStatus.equals("Locked") || recStatus.equals("Active") || recStatus.equals("Inactive")) {

            String to = userInfoEntity.getEmailAddr();

            if (ValidationUtility.isEmailValid(to)) {

                ResponseDTO response = emailService.sendMail(to);

                if (response.isError()) {

                    return ResponseDTO.builder().error(true).message(response.getMessage()).build();
                }

                return ResponseDTO.builder().error(false).message(response.getMessage()).build();
            } else {
                return ResponseDTO.builder().error(true).message("Invalid email account!").build();
            }
        }

        return ResponseDTO.builder().error(true).message("Account " + recStatus).build();*/
    }

    public ResponseDTO resetPassword(NewPasswordResetRequest request) {

        if (request.getPhoneNo().isEmpty()) {

            return ResponseDTO.builder().error(true).message("Phone No empty!").build();
        }

        if (request.getPassword().isEmpty()) {

            return ResponseDTO.builder().error(true).message("Password empty!").build();
        }

        if (request.getRetypePassword().isEmpty()) {

            return ResponseDTO.builder().error(true).message("Re-type Password empty!").build();
        }

        if (!request.getPassword().trim().equals(request.getRetypePassword().trim())) {

            return ResponseDTO.builder().error(true).message("Password does not match!").build();
        }

        Optional<UserInfoEntity> optional = userInfoRepository.findByPhoneNo(request.getPhoneNo());

        if (optional.isEmpty()) {

            return ResponseDTO.builder().error(true).message("Phone Number not found!").build();
        }

        UserInfoEntity userInfoEntity = optional.get();

        String password = passwordEncoder.encode(request.getPassword());
        String status = userInfoEntity.getRecStatus().name();

        if (status.equals("Active")) {

            userInfoEntity.setPassword(password);
            userInfoRepository.save(userInfoEntity);

            return ResponseDTO.builder().error(false).message("Password reset successful!").build();
        }

        if (status.equals("Locked")) {

            userInfoEntity.setPassword(password);
            userInfoEntity.setRecStatus(UserStatus.Active);
            userInfoRepository.save(userInfoEntity);

            return ResponseDTO.builder().error(false).message("Password reset successful!").build();
        }

        return ResponseDTO.builder().error(true).message("Account " + status + "! Password cannot be reset.").build();
    }

    public ResponseDTO updatePasswordByUserId(Long userId, PasswordUpdateRequest request) {

        if (userInfoRepository.isUserAdmin(LoggedInUserDetails.getUserInfoDetails().getId()) == 0) {

            return ResponseDTO.builder().error(true).message("You are not authorized to perform this action!").build();
        }

        Optional<UserInfoEntity> optional = userInfoRepository.findById(userId);

        if (optional.isEmpty()) {

            return ResponseDTO.builder().error(true).message("User not found!").build();
        }

        if (request.getPassword().isEmpty()) {

            return ResponseDTO.builder().error(true).message("Password empty!").build();
        }

        if (request.getRetypePassword().isEmpty()) {

            return ResponseDTO.builder().error(true).message("Re-type Password empty!").build();
        }

        if (!request.getPassword().equals(request.getRetypePassword())) {

            return ResponseDTO.builder().error(true).message("Password does not match!").build();
        }

        try {

            UserInfoEntity userInfoEntity = optional.get();
            userInfoEntity.setPassword(passwordEncoder.encode(request.getPassword()));
            userInfoEntity.setRecStatus(UserStatus.Active);
            userInfoRepository.save(userInfoEntity);

            return ResponseDTO.builder().error(false).message("Password updated!").build();
        } catch (Exception e) {

            return ResponseDTO.builder().error(true).message(e.getMessage()).build();
        }
    }

    public ResponseDTO changePassword(ChangePasswordRequest request) {

        if (request.getOldPassword().isEmpty()) {

            return ResponseDTO.builder().error(true).message("Old Password empty!").build();
        }

        if (request.getPassword().isEmpty()) {

            return ResponseDTO.builder().error(true).message("Password empty!").build();
        }

        if (request.getRetypePassword().isEmpty()) {

            return ResponseDTO.builder().error(true).message("Re-type Password empty!").build();
        }

        if (!request.getPassword().equals(request.getRetypePassword())) {

            return ResponseDTO.builder().error(true).message("Password does not match!").build();
        }

        Optional<UserInfoEntity> optional = userInfoRepository.findById(LoggedInUserDetails.getUserInfoDetails().getId());

        if (optional.isEmpty()) {

            return ResponseDTO.builder().error(true).message("User not found!").build();
        }

        UserInfoEntity userInfoEntity = optional.get();

        if (!bCryptPasswordEncoder.matches(request.getOldPassword(), userInfoEntity.getPassword())) {
            return ResponseDTO.builder().error(true).message("Old password does not match!").build();
        }

        try {

            userInfoEntity.setPassword(passwordEncoder.encode(request.getPassword()));
            userInfoEntity.setRecStatus(UserStatus.Active);
            userInfoRepository.save(userInfoEntity);

            return ResponseDTO.builder().error(false).message("Password updated!").build();
        } catch (Exception e) {

            return ResponseDTO.builder().error(true).message(e.getMessage()).build();
        }
    }

    public void deleteOne(Long id) {
        UserInfoEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        userInfoRepository.save(entity);
        log.info("User {} Deleted", id);
    }

    public UserInfoEntity getEntityById(Long id) {

        Optional<UserInfoEntity> optional = userInfoRepository.findByIdAndIsDeletedFalse(id);

        return optional.orElse(null);

    }

    public boolean existOne(Long id) {
        return userInfoRepository.existsById(id);
    }

    public ResponseDTO getApprovedUserList(Pageable pageable, UserInfoFilter filter) {
        Page<UserInfoEntity> entities = userInfoRepository.findAll(UserInfoSpecification.all(filter), pageable);
        Page<UserInfoResponse> users = entities.map(mapper::entityToDomain);

        return ResponseDTO.builder().error(false).message(users.getTotalElements() + " data found").body(users).build();
    }

    public ResponseDTO getAllCreatedUserList(Pageable pageable, UserInfoFilter filter) {
        Page<UserInfoResponse> response;
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        filter.setCreatedBy(currentUser.getUsername());
        Page<UserInfoEntity> userEntities = userInfoRepository.findAll(CreatedUserInfoSpecification.all(filter), pageable);
        List<Long> userInfoIds = new ArrayList<>();
        userEntities.forEach(user -> userInfoIds.add(user.getId()));
        Page<UserInfoEntity> userInfoEntities = userInfoRepository.findAllByIdInAndIsDeletedFalseAndCreatedByNotLike(pageable, userInfoIds, currentUser.getUsername());
        response = userInfoEntities.map(mapper::entityToDomain);
        return ResponseDTO.builder().error(false).message(response.getTotalElements() + " data found").body(response).build();
    }

    public void lockedUser(Long id) {
        UserInfoEntity entity = this.getEntityById(id);
        entity.setRecStatus(UserStatus.Locked);
        entity = userInfoRepository.save(entity);
        entity.setActivated(true);
        userAudService.saveUserAud(entity);
        log.info("User {} Locked", entity.getUsername());
    }

    public void unLockedUser(Long id) {
        UserInfoEntity entity = this.getEntityById(id);
        entity.setRecStatus(UserStatus.UnLocked);
        entity = userInfoRepository.save(entity);
        entity.setActivated(true);
        userAudService.saveUserAud(entity);
        log.info("User {} UnLocked", entity.getUsername());
    }

    public UserInfoResponse getByUserName(String userName) {
        return userInfoRepository.findByUsernameAndIsDeletedFalse(userName).map(e -> mapper.entityToDomain(e)).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    public UserInfoResponse getUserByUserName(String userName) {
        Optional<UserInfoEntity> entityOptional = userInfoRepository.findByUsernameAndIsDeletedFalse(userName);
        if (entityOptional.isPresent()) {
            UserInfoEntity entity = entityOptional.get();
            return mapper.entityToDomain(entity);
        } else {
            return null;
        }
    }
}
