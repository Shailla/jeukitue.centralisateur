package jkt.centralisateur.storage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jkt.centralisateur.common.Constantes;
import jkt.centralisateur.storage.dto.UserDto;
import jkt.centralisateur.storage.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath:applicationContext-model.xml",
        "classpath:applicationContext-storage.xml"})
public class UserServiceTest {
    @Autowired
    @Qualifier(value="userService")
    private UserService userService;
    
    @Test
    public void testUser() {
        UserDto user = new UserDto();
        user.setEmail("ahuut@yahoo.fr");
        user.setEnabled(false);
        user.setLogin("C" + new Date().getTime());
        user.setPassword("***");
        user.setUuidInscription(new Date().toString());
        
        Set<String> roles = new HashSet<String>();
        roles.add(Constantes.ROLE_USER);
        user.setRoles(roles);
        
        userService.jktCreateUser(user);
    }
}
