package sae.scanmedback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sae.scanmedback.entities.Token;
import sae.scanmedback.entities.User;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{
    boolean existsTokenByUserAndToken(User user, String token);
    boolean existsTokenByUserAndDevice(User user, String device);
    void deleteByDeviceAndUser(String device, User user);
    void deleteTokensByUser(User user);
}
