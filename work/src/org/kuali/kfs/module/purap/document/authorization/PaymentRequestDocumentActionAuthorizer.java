/*
 * Copyright 2007 The Kuali Foundation.
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.module.purap.document.authorization;

import org.kuali.core.bo.user.UniversalUser;
import org.kuali.kfs.util.SpringServiceLocator;
import org.kuali.module.purap.PurapConstants;
import org.kuali.module.purap.PurapParameterConstants;
import org.kuali.module.purap.PurapConstants.PaymentRequestStatuses;
import org.kuali.module.purap.document.PaymentRequestDocument;

/**
 * This class determines permissions for a user
 */
public class PaymentRequestDocumentActionAuthorizer {

    private String docStatus;    
    private boolean requestCancelIndicator;
    private boolean holdIndicator;
    private boolean extracted;
    private boolean canRemoveHold;
    private boolean canRemoveRequestCancel;
    
    private boolean apUser;
    private boolean fiscalOfficerDelegateUser;
    private boolean approver;
        
    public PaymentRequestDocumentActionAuthorizer(PaymentRequestDocument preq, UniversalUser user){
        
        //doc indicators
        this.docStatus = preq.getStatusCode();        
        this.requestCancelIndicator = preq.getPaymentRequestedCancelIndicator();
        this.holdIndicator = preq.isHoldIndicator();
        this.extracted = (preq.getExtractedDate() ==  null ? false : true);
        
        //special indicators
        canRemoveHold = SpringServiceLocator.getPaymentRequestService().canRemoveHoldPaymentRequest(preq, user);
        canRemoveRequestCancel = SpringServiceLocator.getPaymentRequestService().canUserRemoveRequestCancelOnPaymentRequest(preq, user);
        
        //user indicators
        this.approver = preq.getDocumentHeader().getWorkflowDocument().isApprovalRequested();
        
        String apGroup = SpringServiceLocator.getKualiConfigurationService().getApplicationParameterValue(PurapParameterConstants.PURAP_ADMIN_GROUP, PurapConstants.Workgroups.WORKGROUP_ACCOUNTS_PAYABLE);        
        if( user.isMember(apGroup) ){
            this.apUser = true;
        }
        
        if( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus()) && isApprover() ){
            this.fiscalOfficerDelegateUser = true;
        }                
    }
    
    private String getDocStatus(){
        return docStatus;
    }
    
    private boolean isRequestCancelIndicator(){
        return requestCancelIndicator;
    }
    
    private boolean isHoldIndicator(){
        return holdIndicator;        
    }
    
    private boolean isExtracted(){
        return extracted;
    }

    public boolean isCanRemoveHold() {
        return canRemoveHold;
    }

    public boolean isCanRemoveRequestCancel() {
        return canRemoveRequestCancel;
    }

    private boolean isApUser(){
        return apUser;
    }
    
    private boolean isFiscalOfficerDelegateUser(){
        return fiscalOfficerDelegateUser;
    }
    
    private boolean isApprover(){
        return approver;
    }
    
    public boolean canCalculate(){
        boolean hasPermission = false;
        
        //Phase 2B Rule: (PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus() ) && isApprover()) ||
        
        if( ( PaymentRequestStatuses.AWAITING_ACCOUNTS_PAYABLE_REVIEW.equals( getDocStatus() ) && isApUser()) ||
            ( PaymentRequestStatuses.IN_PROCESS.equals( getDocStatus()) && isApUser() ) ){
            hasPermission = true;
        }
        
        return hasPermission;
    }
    
    public boolean canApprove(){
        boolean hasPermission = false;
        
         if( ( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus()) && isFiscalOfficerDelegateUser() && 
               isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||                 
             ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus()) && isApprover() && 
               isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||
             ( PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus()) && isApprover() && 
               isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||
             ( PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus()) && isApprover() && 
               isRequestCancelIndicator() == false && isHoldIndicator() == false ) ){
             hasPermission = true;
         }

        return hasPermission;        
    }
    
    public boolean canSave(){
        boolean hasPermission = false;
        
        //TODO: 
        if( (PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus()) && isApUser()) ||
            (PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus()) && isApUser()) ||
            (PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus()) && isApUser()) ||
            (PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus()) && isApUser()) ||
            (PaymentRequestStatuses.DEPARTMENT_APPROVED.equals( getDocStatus()) && isApUser() && isExtracted() == false) ||            
            (PaymentRequestStatuses.AUTO_APPROVED.equals( getDocStatus()) && isApUser() && isExtracted() == false) ||
            (PaymentRequestStatuses.AWAITING_ACCOUNTS_PAYABLE_REVIEW.equals( getDocStatus() ) && isApUser()) ||
            (PaymentRequestStatuses.IN_PROCESS.equals( getDocStatus()) && isApUser()) ){
            hasPermission = true;
        }

        return hasPermission;        
    }
    
    public boolean canHold(){
        boolean hasPermission = false;
        
        if( ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus() ) && isApUser() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||
                ( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus() ) && isApUser() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||
                ( PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus() ) && isApUser() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||
                ( PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus() ) && isApUser() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||
                ( PaymentRequestStatuses.DEPARTMENT_APPROVED.equals( getDocStatus() ) && isApUser() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false && isExtracted() == false) ||
                ( PaymentRequestStatuses.AUTO_APPROVED.equals( getDocStatus() ) && isApUser() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false && isExtracted() == false) ||                  
                ( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus() ) && isFiscalOfficerDelegateUser() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||
                ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus() ) && isApprover() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||
                ( PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus() ) && isApprover() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false ) ||
                ( PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus() ) && isApprover() &&
                  isRequestCancelIndicator() == false && isHoldIndicator() == false ) ){
                hasPermission = true;
        }
   
        return hasPermission;        
    }
    
    public boolean canRemoveHold(){
        boolean hasPermission = false;

        if(   isCanRemoveHold() ||
              ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus() ) && isApUser() &&
                isHoldIndicator() == true ) ||
              ( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus() ) && isApUser() &&
                isHoldIndicator() == true ) ||
              ( PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus() ) && isApUser() &&
                isHoldIndicator() == true ) ||
              ( PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus() ) && isApUser() &&
                isHoldIndicator() == true ) ||
              ( PaymentRequestStatuses.DEPARTMENT_APPROVED.equals( getDocStatus() ) && isApUser() &&
                isHoldIndicator() == true && isExtracted() == false) ||
              ( PaymentRequestStatuses.AUTO_APPROVED.equals( getDocStatus() ) && isApUser() &&
                isHoldIndicator() == true && isExtracted() == false) ||                
              ( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus() ) && isFiscalOfficerDelegateUser() &&
                isHoldIndicator() == true ) ||
              ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus() ) && isApprover() &&
                isHoldIndicator() == true ) ||
              ( PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus() ) && isApprover() &&
                isHoldIndicator() == true ) ||
              ( PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus() ) && isApprover() &&
                isHoldIndicator() == true ) ){
              hasPermission = true;
          }

        return hasPermission;        
    }
    
    public boolean canCancel(){
        boolean hasPermission = false;
        
        if( ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus() ) && isApUser() && isRequestCancelIndicator()) ||
            ( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus() ) && isApUser() && isRequestCancelIndicator()) ||
            ( PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus() ) && isApUser() && isRequestCancelIndicator()) ||
            ( PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus() ) && isApUser() && isRequestCancelIndicator()) ||
            ( PaymentRequestStatuses.DEPARTMENT_APPROVED.equals( getDocStatus() ) && isApUser() && 
              isRequestCancelIndicator() == false && isHoldIndicator() == false && isExtracted() == false) ||
            ( PaymentRequestStatuses.AUTO_APPROVED.equals( getDocStatus() ) && isApUser() && 
              isRequestCancelIndicator() == false && isHoldIndicator() == false && isExtracted() == false) ||              
            ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus() ) && isApUser() && isHoldIndicator() == false) ){
            hasPermission = true;
        }

        return hasPermission;        
    }
    
    public boolean canRequestCancel(){
        boolean hasPermission = false;
        
        if( ( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus() ) && isFiscalOfficerDelegateUser() && 
              isRequestCancelIndicator() == false && isHoldIndicator() == false) ||
            ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus() ) && isApprover() && 
              isRequestCancelIndicator() == false && isHoldIndicator() == false) ||              
            ( PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus() ) && isApprover() && 
              isRequestCancelIndicator() == false && isHoldIndicator() == false) ||              
            ( PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus() ) && isApprover() && 
              isRequestCancelIndicator() == false && isHoldIndicator() == false) ){
            hasPermission = true;
        }
  
        return hasPermission;        
    }
    
    public boolean canRemoveRequestCancel(){
        boolean hasPermission = false;
        
        if(  isCanRemoveRequestCancel() ||                
            ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus() ) && isApUser() && isRequestCancelIndicator() ) ||
            ( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus() ) && isApUser() && isRequestCancelIndicator() ) ||
            ( PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus() ) && isApUser() && isRequestCancelIndicator() ) ||
            ( PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus() ) && isApUser() && isRequestCancelIndicator() ) ||
            ( PaymentRequestStatuses.AWAITING_FISCAL_REVIEW.equals( getDocStatus() ) && isFiscalOfficerDelegateUser() && 
              isRequestCancelIndicator() ) ||
            ( PaymentRequestStatuses.AWAITING_SUB_ACCT_MGR_REVIEW.equals( getDocStatus() ) && isApprover() && 
              isRequestCancelIndicator() ) ||
            ( PaymentRequestStatuses.AWAITING_ORG_REVIEW.equals( getDocStatus() ) && isApprover() && 
              isRequestCancelIndicator() ) ||
            ( PaymentRequestStatuses.AWAITING_TAX_REVIEW.equals( getDocStatus() ) && isApprover() && 
              isRequestCancelIndicator() ) ){
            hasPermission = true;
        }
  
        return hasPermission;        
    }
    
    public boolean canExit(){
        boolean hasPermission = true;        
        return hasPermission;        
    }
}
