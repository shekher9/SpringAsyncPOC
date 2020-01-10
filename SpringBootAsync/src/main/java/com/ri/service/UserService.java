package com.ri.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.multipart.MultipartFile;

import com.ri.entity.UserEntity;

public interface UserService {
	
	public CompletableFuture<List<UserEntity>> saveNewUser(MultipartFile file) throws Exception;
	public CompletableFuture<List<UserEntity>> getAllUser();
	public List<UserEntity> parseCSVFile(final MultipartFile file) throws Exception;

}
