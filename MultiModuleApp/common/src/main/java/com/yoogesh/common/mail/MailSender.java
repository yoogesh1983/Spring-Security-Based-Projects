package com.yoogesh.common.mail;

import javax.mail.MessagingException;

/**
 * The mail sender interface for sending mail
 * 
 * @author Sanjay Patel
 */
public interface MailSender {

	void send(String to, String subject, String body) throws MessagingException;

}