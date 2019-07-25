package com.practice.demoapp.repository;

import com.practice.demoapp.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

    UserEntity findUserByEmail(String email);

    UserEntity findByUserId(String id);
}

//by implementing PAgingAn.... interface the existing methods like findBy..  will still work




// before we added pagination
//@Repository
//public interface UserRepository extends CrudRepository<UserEntity, Long> {
//
//    UserEntity findUserByEmail(String email);
//
//    UserEntity findByUserId(String id);
//}
