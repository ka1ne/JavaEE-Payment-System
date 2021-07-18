package com.kjb46.webapps2020.ejb;

import com.kjb46.webapps2020.entity.CreditTransactionRecord;
import com.kjb46.webapps2020.entity.TransactionEntity;
import com.kjb46.webapps2020.entity.SystemUser;
import com.kjb46.webapps2020.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author parisis
 */
@Stateless
public class UserService {

    @PersistenceContext
    EntityManager em;
    
    String nextXhtmlFile = "";
    
    DecimalFormat df = new DecimalFormat("#,###.00");
    
    private SystemUser currUser;

    public UserService() {
    }
    
    public synchronized List<SystemUser> getUserList() {
        List<SystemUser> users = em.createNamedQuery("findAllUsers").getResultList();
        return users;
    }
    
    public List<CreditTransactionRecord> getCredits(long id) {
        SystemUser u = em.find(SystemUser.class, id);
        return u.getCredits();
    }
    
    /**
     *
     * @param username
     * @param userpassword
     * @return
     */
    public String login(String username, String userpassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashPass = bigInt.toString(16);
            
            TypedQuery<SystemUser> userQuery = em.createQuery("SELECT s FROM SystemUser s WHERE s.username = ?1 AND s.userpassword = ?2", SystemUser.class);
            List<SystemUser> users = userQuery.setParameter(1, username).setParameter(2, hashPass).getResultList();
            
            ListIterator<SystemUser> userIt = users.listIterator();
            
            if (userIt.hasNext()) {
                TypedQuery<SystemUserGroup> groupQuery = em.createQuery("SELECT s FROM SystemUserGroup s WHERE s.username = ?3", SystemUserGroup.class);
                List<SystemUserGroup> userGroup = groupQuery.setParameter(3, username).getResultList();
            
                ListIterator<SystemUserGroup> groupIt = userGroup.listIterator();
            
                String group = "";
                if (groupIt.hasNext()) {
                    group = groupIt.next().getGroupName();
                }
            
                switch (group) {
                    case "users":
                        nextXhtmlFile = "users/user";    // user.xhtml
                        break;
                    case "admins":
                        nextXhtmlFile = "admins/admin"; // admin.xhtml
                        break;
                    default:
                        nextXhtmlFile = "error";  // error.xhtml
                        break;
                }
            }
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextXhtmlFile;
    }

    public void registerUser(String username, String userpassword, String name, String surname) {
        try {
            SystemUser sys_user;
            SystemUserGroup sys_user_group;
            TransactionEntity account;

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String passwd = userpassword;
            md.update(passwd.getBytes("UTF-8"));
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String paswdToStoreInDB = bigInt.toString(16);

            // apart from the default constructor which is required by JPA
            // you need to also implement a constructor that will make the following code succeed
            sys_user = new SystemUser(username, paswdToStoreInDB, name, surname);
            sys_user_group = new SystemUserGroup(username, "users");
            // sys_user.setSystemUserGroup(sys_user_group);
            
            account = genAccount(sys_user);

            sys_user.setAccount(account);
            
            em.persist(sys_user);
            em.persist(sys_user_group);
            //em.persist(account);
            
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public TransactionEntity genAccount(SystemUser user) {
        TransactionEntity a = new TransactionEntity();
        a.setUser(user);
        a.setRecordValue(BigDecimal.valueOf(1000));
        return a;
    }

    public SystemUser getCurrUser() {
        return currUser;
    }
}


