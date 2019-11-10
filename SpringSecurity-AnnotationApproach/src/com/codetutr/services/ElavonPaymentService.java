package com.codetutr.services;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import com.codetutr.config.springStateMachine.StateMachineService;
import com.codetutr.dao.payment.IPaymentDao;
import com.codetutr.entity.Payment;
import com.codetutr.stateMahine.PaymentEvent;
import com.codetutr.stateMahine.PaymentState;

@Service
public class ElavonPaymentService {
	
	@Autowired 
	private IPaymentDao paymentDao;
	
	public Action<PaymentState, PaymentEvent> authorizePayment(){
		
		return context -> {
			
			System.out.println("Elavon call for authorization initiated.....");
			
			//Get the payment from database
			Long paymentId = (Long) context.getMessageHeader(StateMachineService.PAYMENT_ID_HEADER);
			Payment payment = paymentDao.findPaymentById(paymentId);
			
			//Set the amount to be authorize
			payment.setAmount(new BigDecimal("24.99"));
			
			//make an elavon call.......
			boolean elavonResponse = new Random().nextInt(10) < 5;
			
			//If succeed, then get a transactionToken and save it to the database.
			if(elavonResponse) {
				System.out.println("Elavon call for authorization is successful.....");
				
				payment.setTransactionToken("1Z25AERTVBFF58");
				paymentDao.saveorUpdatePayment(payment);
				
				//update the stateMachine with AUTH_APPROVE
				context.getStateMachine()
						 .sendEvent(MessageBuilder.withPayload(PaymentEvent.AUTH_APPROVED)
								 .setHeader(StateMachineService.PAYMENT_ID_HEADER, context.getMessageHeader(StateMachineService.PAYMENT_ID_HEADER))
								 	.build());
			}
			
			//Otherwise if the elavon response is not succeed, do not save payment to the database
			else {
				System.out.println("Elavon call for authorization failed.....");
				
				//update the stateMachine with AUTH_DECLINE
				context.getStateMachine()
				 .sendEvent(MessageBuilder.withPayload(PaymentEvent.AUTH_DECLINE)
						 .setHeader(StateMachineService.PAYMENT_ID_HEADER, context.getMessageHeader(StateMachineService.PAYMENT_ID_HEADER))
						 	.build());
			}
		};
	}

	public Action<PaymentState, PaymentEvent> capturePayment() {
		return context -> {
			
			System.out.println("Elavon call for Capture initiated.....");
			
			//make an elavon call for capture.......
			boolean elavonResponse = new Random().nextInt(10) < 5;
			
			if(elavonResponse) {
				System.out.println("Elavon call for Capture is successful.....");
				
				
				//update the stateMachine with CAPTURE_APPROVED
				context.getStateMachine()
						 .sendEvent(MessageBuilder.withPayload(PaymentEvent.CAPTURE_APPROVED)
								 .setHeader(StateMachineService.PAYMENT_ID_HEADER, context.getMessageHeader(StateMachineService.PAYMENT_ID_HEADER))
								 	.build());
			}else {
				System.out.println("Elavon call for capture failed.....");
				
				//update the stateMachine with CAPTURE_DECLINE
				context.getStateMachine()
				 .sendEvent(MessageBuilder.withPayload(PaymentEvent.CAPTURE_DECLINE)
						 .setHeader(StateMachineService.PAYMENT_ID_HEADER, context.getMessageHeader(StateMachineService.PAYMENT_ID_HEADER))
						 	.build());
			}
		};
	}

}
