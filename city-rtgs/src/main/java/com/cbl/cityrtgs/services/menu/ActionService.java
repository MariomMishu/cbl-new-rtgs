package com.cbl.cityrtgs.services.menu;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.menu.request.MenuActionRequest;
import com.cbl.cityrtgs.models.dto.menu.response.MenuActionResponse;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.entitymodels.menu.Action;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.menu.ActionRepository;
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
public class ActionService {

    private final ActionRepository actionRepository;

    public ResponseDTO getAll() {

        List<MenuActionResponse> responses = new ArrayList<>();
        List<Action> entities = actionRepository.findAllByIsDeletedFalse();

        entities.forEach(menuAction -> responses.add(MenuActionResponse.toDTO(menuAction)));

        return ResponseDTO.builder()
                .error(false)
                .message(responses.size() + " records found")
                .body(responses)
                .build();
    }

    public ResponseDTO create(MenuActionRequest request) {

        if(isExist(request.getName(), request.getAction())){

            return ResponseDTO.builder()
                    .error(true)
                    .message("Action name already exist!")
                    .build();
        }

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        Action action = MenuActionRequest.toMODEL(request);
        action.setCreatedAt(new Date());
        action.setCreatedBy(currentUser.getId());

        try{

            action = actionRepository.save(action);

            return ResponseDTO.builder()
                    .error(false)
                    .message("Action created successfully!")
                    .body(MenuActionResponse.toDTO(action))
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

        Optional<Action> optional = actionRepository.findByIdAndIsDeletedFalse(id);

        if(optional.isEmpty()){
            return ResponseDTO.builder()
                    .error(true)
                    .message("Action not found")
                    .build();
        }

        return ResponseDTO.builder()
                .error(false)
                .message("Record found!")
                .body(MenuActionResponse.toDTO(optional.get()))
                .build();
    }

    public ResponseDTO update(Long id, MenuActionRequest request) {

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        Optional<Action> optional = actionRepository.findByIdAndIsDeletedFalse(id);

        if(optional.isEmpty()){

            return ResponseDTO.builder()
                    .error(true)
                    .message("Action Not Found!")
                    .build();
        }

        Optional<Action> duplication = actionRepository.findDuplicate(id, request.getName(), request.getAction());

        if(duplication.isPresent()){

            return ResponseDTO.builder()
                    .error(true)
                    .message("Duplicate Action exist!")
                    .build();
        }

        Action entity = optional.get();
        entity.setAction(request.getAction());
        entity.setName(request.getName());
        entity.setUpdatedBy(currentUser.getId());
        entity.setUpdatedAt(new Date());
        entity = actionRepository.save(entity);

        return ResponseDTO.builder()
                .error(false)
                .message("Action created")
                .body(MenuActionResponse.toDTO(entity))
                .build();
    }

    public ResponseDTO delete(Long id) {

        Optional<Action> optional = actionRepository.findByIdAndIsDeletedFalse(id);

        if(optional.isEmpty()){
            return ResponseDTO.builder()
                    .error(true)
                    .message("Action not found")
                    .build();
        }

        try{

            Action action = optional.get();
            action.setDeleted(true);
            actionRepository.save(action);

            return ResponseDTO.builder()
                    .error(false)
                    .message("Action deleted!")
                    .build();
        }
        catch (Exception e){

            return ResponseDTO.builder()
                    .error(true)
                    .message(e.getMessage())
                    .build();
        }
    }

    public Boolean isExist(String name, String action) {
        return actionRepository.existsByNameOrAction(name, action);
    }

}
