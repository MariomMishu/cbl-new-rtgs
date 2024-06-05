package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.CBSAccountTypes;
import com.cbl.cityrtgs.models.entitymodels.configuration.CBSAccountTypesEntity;
import com.cbl.cityrtgs.repositories.configuration.CBSAccountTypesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CBSAccountTypesService {
    private final CBSAccountTypesRepository repository;

    public List<CBSAccountTypes> getAll() {
        List<CBSAccountTypesEntity> accountList = repository.findAll();
        return accountList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    public CBSAccountTypes getById(Long id) {
        CBSAccountTypesEntity entity = repository.findById(id)
                .orElse(null);
        CBSAccountTypes response = this.entityToResponse(entity);
        return response;
    }

    private CBSAccountTypes entityToResponse(CBSAccountTypesEntity entity) {
        return new CBSAccountTypes()
                .setId(entity.getId())
                .setName(entity.getName())
                .setDescription(entity.getDescription());

    }
}
