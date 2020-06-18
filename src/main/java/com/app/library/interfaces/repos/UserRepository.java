package com.app.library.interfaces.repos;

import com.app.library.models.UserRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserRequest, String> {

}
