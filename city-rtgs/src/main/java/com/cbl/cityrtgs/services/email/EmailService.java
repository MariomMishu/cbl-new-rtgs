package com.cbl.cityrtgs.services.email;

import com.cbl.cityrtgs.models.dto.response.ResponseDTO;

import javax.mail.MessagingException;

public interface EmailService {

    ResponseDTO sendMail(String to) throws MessagingException;
}
