package com.kjb46.webapps2020.jsf;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import com.kjb46.webapps2020.ejb.TransactionEJB;
import com.kjb46.webapps2020.ejb.UserService;
import com.kjb46.webapps2020.entity.SystemUser;
import java.math.BigDecimal;

@Named(value = "backingBean")
@ConversationScoped
public class JSFBackingBean implements Serializable {

    @EJB
    TransactionEJB tx;
    
    // df.format(new BigDecimal(1000)) <-- for formatting bigdecimals
    
    
    @EJB
    UserService usrSrv;
    
    private SystemUser selectedItem;
    private List<SystemUser> availableItems;
    
    private BigDecimal amount;
    
    private long otherAccountId;
    private long accountId;
    
    private String txType;
    
    public JSFBackingBean() {
        
    }
    
    public String txType() {
        return "txType";
    } 
    
    
    
    public void submitSend() {
        otherAccountId = selectedItem.getId();
        try {
            tx.transferFund(accountId, amount, otherAccountId);
        }catch(Exception e) {
            System.out.println(e);
        }
    }
    
    public String submitRequest() {
        otherAccountId = selectedItem.getId();
        try {
            tx.transferFund(accountId, amount, otherAccountId);
        }catch(Exception e) {
            System.out.println(e);
        }
        return "user";
    }
    
    public List<SystemUser> refresh() {
        return usrSrv.getUserList();
    }

    public SystemUser getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(SystemUser selectedItem) {
        this.selectedItem = selectedItem;
    }

    public List<SystemUser> getAvailableItems() {
        return availableItems;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public long getOtherAccountId() {
        return otherAccountId;
    }
    
    public long getAccountId(){
        return accountId;
    }
}
