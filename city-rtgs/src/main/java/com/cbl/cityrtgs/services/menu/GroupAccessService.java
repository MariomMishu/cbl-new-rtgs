package com.cbl.cityrtgs.services.menu;

import com.cbl.cityrtgs.models.dto.menu.request.GroupAccessRequest;
import com.cbl.cityrtgs.models.dto.menu.response.GroupAccessResponse;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.entitymodels.menu.GroupAccess;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.UserInfoRepository;
import com.cbl.cityrtgs.repositories.menu.GroupAccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupAccessService {

    private final UserInfoRepository userInfoRepository;
    private final GroupAccessRepository groupAccessRepository;

    public ResponseDTO getUsersByGroupId(Long groupId) {

        Optional<GroupAccess> optional = groupAccessRepository.findUsersByGroupId(groupId);

        if(optional.isEmpty()){

            return ResponseDTO.builder()
                    .error(false)
                    .message("Invalid group id")
                    .build();
        }

        GroupAccess groupAccess = optional.get();

        GroupAccessResponse groupAccessResponse = GroupAccessResponse.toDTO(groupAccess);


        return ResponseDTO.builder()
                .error(false)
                .message(groupAccessResponse.getUsers().size() + " records found")
                .body(groupAccessResponse)
                .build();
    }

    public ResponseDTO getAllGroupAccess() {

        List<GroupAccess> groups = groupAccessRepository.findAll();
        List<GroupAccessResponse> responses = new ArrayList<>();

        if(groups.isEmpty()){

            return ResponseDTO.builder()
                    .error(false)
                    .message("Empty list")
                    .build();
        }

        groups.forEach(group -> responses.add(GroupAccessResponse.toDTO(group)));

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " records found")
                .body(responses)
                .build();
    }

    public ResponseDTO update(Long groupId, GroupAccessRequest request) {

        Optional<GroupAccess> optional = groupAccessRepository.findByGroupId(groupId);

        if(optional.isEmpty()){

            return ResponseDTO.builder()
                    .error(true)
                    .message("Group Access not found!")
                    .build();
        }

        try{

            GroupAccess groupAccess = optional.get();

            Collection<UserInfoEntity> users = groupAccess.getUsers();
            users.clear();
            request.getUsers().forEach(user -> users.add(userInfoRepository.findById(user).get()));

            groupAccess.setUsers(users);

            return ResponseDTO.builder()
                    .error(false)
                    .message("Group Access updated successfully!")
                    .body(GroupAccessResponse.toDTO(groupAccessRepository.save(groupAccess)))
                    .build();

        }catch (Exception e){

            log.info("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    public ResponseDTO getByUserId(Long id) {

        GroupAccess groupAccess = groupAccessRepository.findGroupAccessByUserId(id);

        if(groupAccess == null){

            return ResponseDTO.builder()
                    .error(true)
                    .message("User has no menu access")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("Found")
                .body(GroupAccessResponse.toDTO(groupAccess))
                .build();
    }

    public ResponseDTO getById(Long id) {

        Optional<GroupAccess> optional = groupAccessRepository.findById(id);

        if(optional.isEmpty()){
            return ResponseDTO.builder()
                    .error(true)
                    .message("Group Access not found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("Group Access found")
                .body(GroupAccessResponse.toDTO(optional.get()))
                .build();
    }
}
