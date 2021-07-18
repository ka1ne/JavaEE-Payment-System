package com.kjb46.webapps2020.ejb;

import com.kjb46.webapps2020.entity.TransactionEntity;
import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

@Stateless
@TransactionAttribute(REQUIRED)
public class TransactionEJB {

    @PersistenceContext(unitName = "WebappsStoragePU")
    EntityManager em;
    
    @Resource
    private UserTransaction tx;
    
    /**
     *
     * @param fromAccountId
     * @param amount
     * @param toAccountId
     * @throws Exception
     */
    public void transferFund(long fromAccountId, BigDecimal amount , 
      long toAccountId) throws Exception{
        
      try{
          tx.begin();
          withdrawAmount(fromAccountId, amount);
          depositAmount(toAccountId, amount);
          tx.commit();
      }catch (PaymentException | InsufficientFundException ex) {
         tx.rollback();
         System.out.println(ex);
      }
   }
    
    public void withdrawAmount(long id, BigDecimal amount) throws InsufficientFundException {
        TransactionEntity e = em.find(TransactionEntity.class, id);
        BigDecimal worth = e.getRecordValue();
        if (worth.compareTo(amount) < 0) {
            throw new InsufficientFundException("Low funds : " + worth);
        } else if (worth.compareTo(amount) >= 0) {
            e.setRecordValue(worth.subtract(amount));
        }
        em.persist(e);
        em.flush();
    }
    
    public void depositAmount(long id, BigDecimal amount) throws PaymentException {
        TransactionEntity e = em.find(TransactionEntity.class, id);
        BigDecimal worth = e.getRecordValue();
        if (worth.compareTo(amount) < 0) {
            throw new PaymentException("Not enough in account: " + worth);
        } else {
            e.setRecordValue(worth.add(amount));
        }
        em.persist(e);
        em.flush();
    }
}
