/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kjb46.webapps2020.ejb;

/**
 *
 * @author email
 */
public class InsufficientFundException extends Exception {
    public InsufficientFundException(String msg) {
        super(msg);
    }
}