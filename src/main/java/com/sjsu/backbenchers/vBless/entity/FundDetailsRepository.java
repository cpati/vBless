package com.sjsu.backbenchers.vBless.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FundDetailsRepository extends JpaRepository<FundDetails, Long> {
	List<FundDetails> findByFundPaymentId(Long fundPaymentId);
}