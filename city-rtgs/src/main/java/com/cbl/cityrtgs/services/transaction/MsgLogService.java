package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.message.RtgsMsgLogMapper;
import com.cbl.cityrtgs.models.dto.message.*;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.messagelog.InOutMsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import com.cbl.cityrtgs.repositories.message.InOutMsgLogRepository;
import com.cbl.cityrtgs.repositories.message.MsgLogRepository;
import com.cbl.cityrtgs.repositories.specification.RtgsMessageLogSpecification;
import com.cbl.cityrtgs.common.utility.DateUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MsgLogService {
    private final InOutMsgLogRepository inOutMsgLogRepository;
    private final MsgLogRepository msgLogRepository;
    private final RtgsMsgLogMapper mapper;

    public Page<MsgLogResponse> getAll(Pageable pageable, RtgsMessageFilter filter) {
        if (!StringUtils.isNotBlank(filter.getMsgDate())) {
            filter.setMsgDate(DateUtility.localDateToStringDate());
        }
        Page<MsgLogEntity> entities = msgLogRepository.findAll(RtgsMessageLogSpecification.all(filter), pageable);
        return entities.map(mapper::entityToResponse);
    }

    public MsgLogResponse getById(Long id) {
        MsgLogEntity entity = msgLogRepository.findById(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Rtgs Message Not Found"));
        return mapper.entityToResponse(entity);
    }

    public boolean existOne(Long id) {
        return msgLogRepository.existsById(id);
    }

    public boolean isExist(Long id) {
        return msgLogRepository.existsByIdAndProcessStatus(id, MessageProcessStatus.PROCESSED.toString());
    }

    public boolean isExistProcessing(Long id) {
        return msgLogRepository.existsByIdAndProcessStatus(id, MessageProcessStatus.PROCESSING.toString());
    }

    public boolean isExistArrived(Long id) {
        return msgLogRepository.existsByIdAndProcessStatus(id, MessageProcessStatus.ARRIVED.name());
    }

    public boolean isExistQueued(Long id) {
        return msgLogRepository.existsByIdAndProcessStatus(id, MessageProcessStatus.QUEUED.toString());
    }

    public boolean logExists(String businessMsgId) {
        return msgLogRepository.existsByBusinessMessageId(businessMsgId);
    }

    public void update(Long id, MsgLogRequest request) {

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<MsgLogEntity> optional = msgLogRepository.findByIdAndIsDeletedFalse(id);

        if (optional.isPresent()) {
            MsgLogEntity entity = optional.get();
            entity.setId(optional.get().getId());
            entity.setMxMessage(request.getMxMessage());
            entity.setCreatedAt(optional.get().getCreatedAt());
            entity.setCreatedBy(optional.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            msgLogRepository.save(entity);
        }
        log.info("Message Log Updated");
    }

    public MsgLogEntity createRtgsMsgLog(String routingType, String msgContents, String bizMsgId, String msgNetMir, String msgUsrRef, String msgType, long userInfoId, String msgProcessSts, String errorMessage,String uuid) {
        Date dateTime = new Date();
        if (!logExists(bizMsgId)) {

            String messageDirection = "";
            if (routingType.equals(RoutingType.Outgoing.name())) {
                messageDirection = MessageDirectionsType.OUTWARD.name();
            }

            MsgLogEntity msgLogEntity = MsgLogEntity.builder()
                    .messageDirections(messageDirection)
                    .messageFormat("MX")
                    .messageSender("CIBLBDDHAXXX")
                    .messageReceiver("BBHOBDDHXRTG")
                    .messageType(msgType)
                    .messageUserReference(msgUsrRef)
                    .uuid(uuid)
                    .businessMessageId(bizMsgId)
                    .processStatus(msgProcessSts)
                    .messageNetMir(msgNetMir)
                    .msgDate(dateTime)
                    .msgTime(dateTime)
                    .errorMessage(errorMessage)
                    .mxMessage(msgContents)
                    .build();
            msgLogEntity.setCreatedAt(new Date());

            if (userInfoId != 0) {
                msgLogEntity.setCreatedBy(userInfoId);
            } else {
                msgLogEntity.setCreatedBy(LoggedInUserDetails.getUserInfoDetails().getId());
            }

            try {
                msgLogEntity = this.save(msgLogEntity);
                if (msgLogEntity != null) {
                    log.info("New Message {} is saved in rtgs message Table", msgLogEntity.getBusinessMessageId());
                }
            } catch (Exception e) {
                log.error("Error saving MsgLogEntity: {}", e.getMessage());
            }
            return msgLogEntity;
        }
        return null;
    }

    public void createInOutMessage(String sentMsgId1, String batchNumber, String reference, String messageType, String routingType, long userInfoId) {
        InOutMsgLogEntity inOutMsgLogEntity = new InOutMsgLogEntity();
        inOutMsgLogEntity
                .setBatchNumber(batchNumber)
                .setMsgCreationDate(new Date())
                .setMsgId(sentMsgId1)
                .setMsgType(messageType)
                .setRouteType(routingType)
                .setTxnReferenceNumber(reference);
        if (userInfoId != 0) {
            inOutMsgLogEntity.setCreatedBy(userInfoId);
        } else {
            inOutMsgLogEntity.setCreatedBy(LoggedInUserDetails.getUserInfoDetails().getId());
        }
        inOutMsgLogEntity.setCreatedAt(new Date());
        inOutMsgLogRepository.save(inOutMsgLogEntity);
        log.info("New Message {} is saved in out message Table", sentMsgId1);
    }

    public MsgLogEntity save(MsgLogEntity msgLogEntity) {

        if (msgLogEntity != null) {
            try {
                log.info("Saving MsgLogEntity...");
                MsgLogEntity msgLog = msgLogRepository.save(msgLogEntity);
                log.info("MsgLogEntity Saved!");

                return msgLog;
            } catch (Exception e) {
                log.error("Error saving MsgLogEntity: {}", e.getMessage());
            }
        } else {
            log.error("Error: MsgLogEntity Is Empty!");
        }

        return null;
    }

    public void updateRtgsMsgLog(Long msgLogId, String msgProcessStatus, String errorMessage) {

        Optional<MsgLogEntity> optional = msgLogRepository.findById(msgLogId);

        if (optional.isPresent()) {
            MsgLogEntity entity = optional.get();
            entity.setId(optional.get().getId());
            entity.setMxMessage(optional.get().getMxMessage());
            entity.setCreatedAt(optional.get().getCreatedAt());
            entity.setCreatedBy(optional.get().getCreatedBy());
            entity.setUpdatedBy(0);
            entity.setUpdatedAt(new Date());
            try {
                log.info("Saving MsgLogEntity...");
                entity.setProcessStatus(msgProcessStatus);
                entity.setErrorMessage(errorMessage);
                msgLogRepository.save(entity);
                log.info("Message Log Updated");
            } catch (Exception e) {
                entity.setErrorMessage(errorMessage + e.getMessage());
                log.error("Error: Message Log Update Failed!: {}", e.getMessage());
                entity.setProcessStatus(msgProcessStatus);
                msgLogRepository.save(entity);
            }
        } else {
            log.error("Error: Message Log Update Failed! Id: {}", msgLogId);
        }

    }

    public MsgLogResponse getCamtInfoByTxnReference(String txnReference) {
        var reference = "";
        if (StringUtils.isNotBlank(txnReference)) {
            reference = '%' + txnReference + '%';
            Optional<MsgLogEntity> optional = msgLogRepository.findByTxnReferenceAndStatusArrived(reference);
            return optional.map(mapper::entityToResponse).orElse(null);
        } else {
            return null;
        }
    }

}
