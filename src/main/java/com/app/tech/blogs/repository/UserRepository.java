package com.app.tech.blogs.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.app.tech.blogs.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	// The method name needs to follow naming convention "findBy + field name"
	UserEntity findByEmail(String email);

	UserEntity findByUserId(String userId);
}
