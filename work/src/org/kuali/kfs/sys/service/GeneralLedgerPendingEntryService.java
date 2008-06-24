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
package org.kuali.kfs.sys.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.core.util.GeneralLedgerPendingEntrySequenceHelper;
import org.kuali.core.util.KualiDecimal;
import org.kuali.kfs.coa.businessobject.Account;
import org.kuali.kfs.fp.businessobject.BankAccount;
import org.kuali.kfs.gl.businessobject.Balance;
import org.kuali.kfs.gl.businessobject.Encumbrance;
import org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntry;
import org.kuali.kfs.sys.businessobject.GeneralLedgerPendingEntrySourceDetail;
import org.kuali.kfs.sys.document.GeneralLedgerPendingEntrySource;
import org.kuali.kfs.sys.document.GeneralLedgerPostingDocument;

/**
 * This interface defines methods that a GeneralLedgerPendingEntry Service must provide
 */
public interface GeneralLedgerPendingEntryService {

    /**
     * This method...
     * 
     * @param universityFiscalYears
     * @param chartOfAccountsCode
     * @param accountNumber
     * @param isDebit
     * @return
     */
    public KualiDecimal getCashSummary(List universityFiscalYears, String chartOfAccountsCode, String accountNumber, boolean isDebit);

    /**
     * This method...
     * 
     * @param universityFiscalYears
     * @param chartOfAccountsCode
     * @param accountNumber
     * @param isDebit
     * @return
     */
    public KualiDecimal getActualSummary(List universityFiscalYears, String chartOfAccountsCode, String accountNumber, boolean isDebit);

    /**
     * This method...
     * 
     * @param universityFiscalYear
     * @param chartOfAccountsCode
     * @param accountNumber
     * @param sufficientFundsObjectCode
     * @param isDebit
     * @param isYearEnd
     * @return
     */
    public KualiDecimal getExpenseSummary(Integer universityFiscalYear, String chartOfAccountsCode, String accountNumber, String sufficientFundsObjectCode, boolean isDebit, boolean isYearEnd);

    /**
     * This method...
     * 
     * @param universityFiscalYear
     * @param chartOfAccountsCode
     * @param accountNumber
     * @param sufficientFundsObjectCode
     * @param isDebit
     * @param isYearEnd
     * @return
     */
    public KualiDecimal getEncumbranceSummary(Integer universityFiscalYear, String chartOfAccountsCode, String accountNumber, String sufficientFundsObjectCode, boolean isDebit, boolean isYearEnd);

    /**
     * This method...
     * 
     * @param universityFiscalYear
     * @param chartOfAccountsCode
     * @param accountNumber
     * @param sufficientFundsObjectCode
     * @param isYearEnd
     * @return
     */
    public KualiDecimal getBudgetSummary(Integer universityFiscalYear, String chartOfAccountsCode, String accountNumber, String sufficientFundsObjectCode, boolean isYearEnd);

    /**
     * @param transactionEntrySequenceId
     * @param documentHeaderId
     */
    public GeneralLedgerPendingEntry getByPrimaryId(Integer transactionEntrySequenceId, String documentHeaderId);

    /**
     * Invokes generateGeneralLedgerPendingEntries method on the transactional document.
     * 
     * @param document - document whose pending entries need generated
     * @return whether the business rules succeeded
     */
    public boolean generateGeneralLedgerPendingEntries(GeneralLedgerPendingEntrySource document);

    /**
     * The fiscal year and period is null in quite a few glpe's. This will put in a sensible default.
     * 
     * @param glpe
     */
    public void fillInFiscalPeriodYear(GeneralLedgerPendingEntry glpe);

    /**
     * @param generalLedgerPendingEntry
     */
    public void save(GeneralLedgerPendingEntry generalLedgerPendingEntry);

    /**
     * @param documentHeaderId
     */
    public void delete(String documentHeaderId);

    /**
     * Delete all pending entries for a specific document approved code
     * 
     * @param financialDocumentApprovedCode
     */
    public void deleteByFinancialDocumentApprovedCode(String financialDocumentApprovedCode);

    /**
     * Does the given account have any general ledger entries? It is necessary to check this before closing an account.
     * 
     * @param account
     * @return
     */
    public boolean hasPendingGeneralLedgerEntry(Account account);

    /**
     * The method finds all pending ledger entries
     * 
     * @return all pending ledger entries
     */
    public Iterator findApprovedPendingLedgerEntries();

    /**
     * This method retrieves all pending ledger entries for the given encumbrance
     * 
     * @param encumbrance the encumbrance entry
     * @param isApproved the flag that indicates whether the pending entries are approved or don't care
     * @return all pending ledger entries of the given encumbrance
     */
    public Iterator findPendingLedgerEntries(Encumbrance encumbrance, boolean isApproved);

    /**
     * This method retrieves all pending ledger entries for the given encumbrance
     * 
     * @param balance the balance entry
     * @param isApproved the flag that indicates whether the pending entries are approved or don't care
     * @param isConsolidated determine whether the search results are consolidated
     * @return all pending ledger entries of the given encumbrance
     */
    public Iterator findPendingLedgerEntries(Balance balance, boolean isApproved, boolean isConsolidated);

    /**
     * This method retrieves all pending ledger entries matching the given entry criteria
     * 
     * @param isApproved the flag that indicates whether the pending entries are approved or don't care
     * @param fieldValues the input fields and values
     * @return all pending ledger entries matching the given balance criteria
     */
    public Iterator findPendingLedgerEntriesForEntry(Map fieldValues, boolean isApproved);

    /**
     * This method retrieves all pending ledger entries matching the given balance criteria
     * 
     * @param isApproved the flag that indicates whether the pending entries are approved or don't care
     * @param fieldValues the input fields and values
     * @return all pending ledger entries matching the given balance criteria
     */
    public Iterator findPendingLedgerEntriesForBalance(Map fieldValues, boolean isApproved);

    /**
     * This method retrieves all pending ledger entries that may belong to cash balance in the future
     * 
     * @param isApproved the flag that indicates whether the pending entries are approved or don't care
     * @return all pending ledger entries that may belong to cash balance
     */
    public Iterator findPendingLedgerEntriesForCashBalance(Map fieldValues, boolean isApproved);

    /**
     * This method retrieves all pending ledger entries that may belong to encumbrance table in the future
     * 
     * @param isApproved the flag that indicates whether the pending entries are approved or don't care
     * @return all pending ledger entries that may belong to encumbrance table
     */
    public Iterator findPendingLedgerEntriesForEncumbrance(Map fieldValues, boolean isApproved);

    /**
     * This method retrieves all pending ledger entries that may belong to the given account balance record in the future
     * 
     * @param fieldValues
     * @param isApproved the flag that indicates whether the pending entries are approved or don't care
     * @return all pending ledger entries that may belong to encumbrance table
     */
    public Iterator findPendingLedgerEntrySummaryForAccountBalance(Map fieldValues, boolean isApproved);

    /**
     * This method retrieves all pending ledger entries that may belong to the given account balance record in the future
     * 
     * @param fieldValues
     * @param isApproved the flag that indicates whether the pending entries are approved or don't care
     * @return all pending ledger entries that may belong to encumbrance table
     */
    public Iterator findPendingLedgerEntriesForAccountBalance(Map fieldValues, boolean isApproved);

    /**
     * @param fieldValues
     * @return
     */
    public Collection findPendingEntries(Map fieldValues, boolean isApproved);
    
    /**
     * This populates an empty GeneralLedgerPendingEntry explicitEntry object instance with default values.
     * 
     * @param accountingDocument
     * @param accountingLine
     * @param sequenceHelper
     * @param explicitEntry
     */
    public void populateExplicitGeneralLedgerPendingEntry(GeneralLedgerPendingEntrySource poster, GeneralLedgerPendingEntrySourceDetail postable, GeneralLedgerPendingEntrySequenceHelper sequenceHelper, GeneralLedgerPendingEntry explicitEntry);
    
    /**
     * This populates an GeneralLedgerPendingEntry offsetEntry object instance with values that differ from the values supplied in
     * the explicit entry that it was cloned from. Note that the entries do not contain BOs now.
     * 
     * @param universityFiscalYear
     * @param explicitEntry
     * @param sequenceHelper
     * @param offsetEntry Cloned from the explicit entry
     */
    public boolean populateOffsetGeneralLedgerPendingEntry(Integer universityFiscalYear, GeneralLedgerPendingEntry explicitEntry, GeneralLedgerPendingEntrySequenceHelper sequenceHelper, GeneralLedgerPendingEntry offsetEntry);
    
    /**
     * This populates an empty GeneralLedgerPendingEntry instance with default values for a bank offset. A global error will be
     * posted as a side-effect if the given BankAccount has not defined the necessary bank offset relations.
     * 
     * @param bankAccount
     * @param depositAmount
     * @param financialDocument
     * @param universityFiscalYear
     * @param sequenceHelper
     * @param bankOffsetEntry
     * @param errorPropertyName
     */
    public boolean populateBankOffsetGeneralLedgerPendingEntry(BankAccount bankAccount, KualiDecimal depositAmount, GeneralLedgerPostingDocument financialDocument, Integer universityFiscalYear, GeneralLedgerPendingEntrySequenceHelper sequenceHelper, GeneralLedgerPendingEntry bankOffsetEntry, String errorPropertyName);

}
