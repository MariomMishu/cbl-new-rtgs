package com.cbl.cityrtgs.services.menu;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.menu.request.GroupRequest;
import com.cbl.cityrtgs.models.dto.menu.response.GroupResponse;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.entitymodels.menu.Group;
import com.cbl.cityrtgs.models.entitymodels.menu.GroupAccess;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.UserInfoRepository;
import com.cbl.cityrtgs.repositories.menu.GroupAccessRepository;
import com.cbl.cityrtgs.repositories.menu.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupAccessRepository groupAccessRepository;
    private final UserInfoRepository userInfoRepository;

    public ResponseDTO getAllActiveGroups() {

        List<Group> groups = groupRepository.findAllByIsDeletedFalse();
        List<GroupResponse> responses = new ArrayList<>();

        if(groups.isEmpty()){

            return ResponseDTO.builder()
                    .error(false)
                    .message("Empty list")
                    .build();
        }

        groups.forEach(group -> {
            responses.add(GroupResponse.toDTO(group));
        });

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " active groups found")
                .body(responses)
                .build();
    }

    public ResponseDTO create(GroupRequest request) {

        if(isExist(request.getName())){

            return ResponseDTO.builder()
                    .error(true)
                    .message("Group name already exist!")
                    .build();
        }

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        Group group = GroupRequest.toMODEL(request);
        group.setCreatedAt(new Date());
        group.setCreatedBy(currentUser.getId());

        try{
            group = groupRepository.save(group);
            GroupResponse groupResponse = GroupResponse.toDTO(group);

            GroupAccess groupAccess = GroupAccess.builder()
                    .group(group)
                    .build();
            groupAccessRepository.save(groupAccess);

            return ResponseDTO.builder()
                    .error(false)
                    .message("Group created successfully!")
                    .body(groupResponse)
                    .build();

        }catch (Exception e){

            log.info("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    public ResponseDTO getById(Long id) {

        Optional<Group> optional = groupRepository.findByIdAndIsDeletedFalse(id);

        if(optional.isEmpty()){
            return ResponseDTO.builder()
                    .error(true)
                    .message("Group not found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("Group found!")
                .body(GroupResponse.toDTO(optional.get()))
                .build();
    }

    public ResponseDTO update(Long id, GroupRequest request) {

        Optional<Group> optional = groupRepository.findByIdAndIsDeletedFalse(id);

        if(optional.isEmpty()){

            return ResponseDTO.builder()
                    .error(true)
                    .message("Group Not Found!")
                    .build();
        }

        if(groupRepository.findDuplicate(id, request.getName()).isPresent()){

            return ResponseDTO.builder()
                    .error(true)
                    .message("Duplicate group name!")
                    .build();
        }

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        try{

            Group entity = optional.get();
            entity.setName(request.getName());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());

            return ResponseDTO.builder()
                    .error(false)
                    .message("Group updated!")
                    .body(GroupResponse.toDTO(groupRepository.save(entity)))
                    .build();
        }
        catch (Exception e){

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    public ResponseDTO delete(Long id) {

        Optional<Group> optional = groupRepository.findByIdAndIsDeletedFalse(id);

        if(optional.isEmpty()){
            return ResponseDTO.builder()
                    .error(true)
                    .message("Group not found")
                    .build();
        }

        try{

            Group entity = optional.get();
            entity.setDeleted(true);
            GroupResponse response = GroupResponse.toDTO(groupRepository.save(entity));

            GroupAccess groupAccess = groupAccessRepository.findByGroupId(id).get();

            userInfoRepository.deleteUserGroupAccess(groupAccess.getId());

            return ResponseDTO.builder()
                    .error(false)
                    .message("Group deleted!")
                    .body(response)
                    .build();
        }
        catch (Exception e){

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    public Boolean isExist(String name) {
        return groupRepository.existsByName(name);
    }

}
