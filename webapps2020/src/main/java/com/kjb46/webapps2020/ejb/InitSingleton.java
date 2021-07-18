package com.kjb46.webapps2020.ejb;

import com.kjb46.webapps2020.entity.SystemUser;
import com.kjb46.webapps2020.entity.SystemUserGroup;
import com.kjb46.webapps2020.entity.TransactionEntity;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/*This EJB will be instantiated only once when the appliciation is deployed - @Startup and @Singleton respectively*/
@Startup
@Singleton
public class InitSingleton {

    @PersistenceContext(unitName = "WebappsStoragePU")
    EntityManager em;
    
    @EJB
    UserService usrSrv;

    @PostConstruct
    public void dbInit() {
        System.out.println("At startup: Initialising Database admin1 account.");
            
            /*
            // DB Primary Keys start at 1!
            for (long i = 1; i <= 2; i++) {
                TransactionEntity e = new TransactionEntity();
                e.setRecordValue(1000);
                em.persist(e);
                em.flush();
            }
            */
            
            try {
                SystemUser sys_user;
                SystemUserGroup sys_user_group;
                TransactionEntity account;

                MessageDigest md = MessageDigest.getInstance("SHA-256");
                String passwd = "admin1";
                md.update(passwd.getBytes("UTF-8"));
                byte[] digest = md.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                String paswdToStoreInDB = bigInt.toString(16);

                // apart from the default constructor which is required by JPA
                // you need to also implement a constructor that will make the following code succeed
                sys_user_group = new SystemUserGroup("admin1", "admins");
                //sys_user_group.setId(111111111L);
                sys_user = new SystemUser("admin1", paswdToStoreInDB, "admin1", "admin1");
                
                //sys_user.setId(111111111L);
                
                //sys_user.setSystemUserGroup(sys_user_group);
                
                account = usrSrv.genAccount(sys_user);

                em.persist(account);

                em.persist(sys_user);
                em.persist(sys_user_group);
                em.flush();
            
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // usrSrv.registerUser("admin1", "admin1", "John", "Doe");
            
            
            /*
        try {
            
            
            System.out.println("At startup: Initialising Database with admin user with initial username and password as admin1");
            // Initialise admin user
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = "admin1";
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashPass = bigInt.toString(16);
            
            //sys_user = new SystemUser("admin1", hashPass, "John", "Doe");
            sys_user_group = new SystemUserGroup("admin1", "admins");
            //sys_user_group.setId(sys_user.getId());
            
            //em.persist(sys_user);
            em.persist(sys_user_group);
            em.flush();
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(InitSingleton.class.getName()).log(Level.SEVERE, null, ex);
        } */
    }
}
