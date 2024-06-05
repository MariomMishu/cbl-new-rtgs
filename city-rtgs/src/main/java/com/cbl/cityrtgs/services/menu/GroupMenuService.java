package com.cbl.cityrtgs.services.menu;

import com.cbl.cityrtgs.models.dto.menu.request.GroupMenuRequest;
import com.cbl.cityrtgs.models.dto.menu.response.GroupMenuResponse;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.entitymodels.menu.GroupMenu;
import com.cbl.cityrtgs.repositories.menu.GroupRepository;
import com.cbl.cityrtgs.repositories.menu.GroupMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class GroupMenuService {

    private final GroupRepository groupRepository;
    private final GroupMenuRepository groupMenuRepository;

    public ResponseDTO getAllGroupMenu() {

        List<GroupMenu> groups = groupMenuRepository.findAll();
        List<GroupMenuResponse> responses = new ArrayList<>();

        if(groups.isEmpty()){

            return ResponseDTO.builder()
                    .error(false)
                    .message("Empty list")
                    .build();
        }

        groups.forEach(group -> responses.add(GroupMenuResponse.toDTO(group)));

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " records found")
                .body(responses)
                .build();
    }

    public ResponseDTO create(GroupMenuRequest request) {

        if(groupMenuRepository.findByGroupId(request.getGroupId()).isPresent()){

            return ResponseDTO.builder()
                    .error(true)
                    .message("Group Menu already exists!")
                    .build();
        }

        GroupMenu groupMenu = GroupMenuRequest.toMODEL(request);
        groupMenu.setGroup(groupRepository.findById(request.getGroupId()).get());

        try{

            return ResponseDTO.builder()
                    .error(false)
                    .message("Group Menu created successfully!")
                    .body(GroupMenuResponse.toDTO(groupMenuRepository.save(groupMenu)))
                    .build();

        }catch (Exception e){

            log.info("{}", e.getMessage());

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    public ResponseDTO getByGroupId(Long id) {

        Optional<GroupMenu> optional = groupMenuRepository.findByGroupId(id);

        if(optional.isEmpty()){
            return ResponseDTO.builder()
                    .error(true)
                    .message("Group Menu not found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("Group menu details found")
                .body(GroupMenuResponse.toDTO(optional.get()))
                .build();
    }

    public ResponseDTO getById(Long id) {

        Optional<GroupMenu> optional = groupMenuRepository.findById(id);

        if(optional.isEmpty()){
            return ResponseDTO.builder()
                    .error(true)
                    .message("Group Menu not found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("Group Menu found")
                .body(GroupMenuResponse.toDTO(optional.get()))
                .build();
    }

    public ResponseDTO update(Long groupId, GroupMenuRequest request) {

        Optional<GroupMenu> optional = groupMenuRepository.findByGroupId(groupId);

        if(optional.isEmpty()){
            return ResponseDTO.builder()
                    .error(true)
                    .message("Group Menu not found")
                    .build();
        }

        try{

            GroupMenu entity = optional.get();
            entity.setMenu(request.getMenu());

            return ResponseDTO.builder()
                    .error(false)
                    .message("Group Menu updated!")
                    .body(GroupMenuResponse.toDTO(groupMenuRepository.save(entity)))
                    .build();
        }
        catch (Exception e){

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }
}
