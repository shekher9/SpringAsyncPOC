package com.ri.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ri.entity.UserEntity;
import com.ri.repository.UserRepository;
@Service("userservice")
public class UserServiceImpl implements UserService {
	@Autowired(required = true)
	private UserRepository userrepo;
	
	Object target;
    Logger logger = LoggerFactory.getLogger(UserService.class);

	@Async
    @Override
	public CompletableFuture<List<UserEntity>> saveNewUser(MultipartFile file) throws Exception {
		long start = System.currentTimeMillis();
        List<UserEntity> users = parseCSVFile(file);
        logger.info("saving list of users of size {}", users.size(), "" + Thread.currentThread().getName());
        users = userrepo.saveAll(users);
        long end = System.currentTimeMillis();
        logger.info("Total time {}", (end - start));
        return CompletableFuture.completedFuture(users);

		
	}

	@Async
	@Override
	public CompletableFuture<List<UserEntity>> getAllUser() {
		logger.info("get list of user by "+Thread.currentThread().getName());
        List<UserEntity> users=userrepo.findAll();
        return CompletableFuture.completedFuture(users);
	}

	@Override
	public List<UserEntity> parseCSVFile(MultipartFile file) throws Exception {
		final List<UserEntity> users = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final UserEntity user = new UserEntity();
                    user.setName(data[0]);
                    user.setEmail(data[1]);
                    user.setGender(data[2]);
                    users.add(user);
                }
                return users;
            }
        } catch (final IOException e) {
            logger.error("Failed to parse CSV file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
	}

}
