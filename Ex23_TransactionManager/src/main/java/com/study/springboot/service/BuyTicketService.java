package com.study.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.study.springboot.dao.ITransaction1Dao;
import com.study.springboot.dao.ITransaction2Dao;

import oracle.jdbc.internal.OracleConnection.TransactionState;

// @Service 어노테이션을 지정하면 이 클래스를 빈으로 사용하라는 의미
@Service
public class BuyTicketService implements IBuyTicketService
{
	// new는 사용하지 않고 자동으로 대입
	// Jdbc 연동을 위한 자동 주입
	@Autowired	// 자동 주입을 받아 변수를 만든다.
	ITransaction1Dao transaction1;
	@Autowired
	ITransaction2Dao transaction2;
	
	// 트랜잭션 처리를 위한 자동 주입
	@Autowired	// 스프링안에 기본적으로 제공하는 것
	PlatformTransactionManager transactionManager;
	@Autowired
	TransactionDefinition definition;
	// definition : 기본 설정 값을 사용하겠다는 것
	
	@Override
	public int buy(String consumerId, int amount, String error) {
		
		TransactionStatus status = transactionManager.getTransaction(definition);
		
		try {
			transaction1.pay(consumerId, amount);	
			
			// 의도적 에러 발생
			if(error.equals("1")) {int n = 10/0;}
			
			transaction2.pay(consumerId, amount);	// 회계장부 상황
			// 여기로 롤백을 함.
			transactionManager.commit(status); // commit 전으로 돌아간다는것?
			return 1;
		} catch (Exception e) {
			System.out.println("[PlatformTransactionManager] Rollback");
			// 에러가 나면 transaction1안에 들어와 있던 데이터도 롤백이 되어서 없어짐.
			// 오라클에 명령을 내리는 것 (catch문 위로 올라가는 것이 아닌_서버에 커밋한 걸 취소하는 것)
			// 이곳 프로그램이 아닌 서버에 이전으로 돌아가라는 명령?을 내리는 것) 전산에서 취소시키는 것
			// Rollback 오라클에서 앞으로 돌아가는 것
			transactionManager.rollback(status);
			return 0;
		}
	}
}
