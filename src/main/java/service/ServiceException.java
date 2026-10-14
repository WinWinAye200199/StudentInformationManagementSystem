/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

public class ServiceException extends Exception{
    
    public ServiceException(String message){
        super(message);
    }
    
    public ServiceException(String message, Throwable cause){
        super(message, cause);
    }
    
}
