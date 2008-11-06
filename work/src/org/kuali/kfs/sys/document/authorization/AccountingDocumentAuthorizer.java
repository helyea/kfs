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
package org.kuali.kfs.sys.document.authorization;

import java.util.List;
import java.util.Map;

import org.kuali.kfs.sys.businessobject.AccountingLine;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.document.TransactionalDocument;
import org.kuali.rice.kns.document.authorization.TransactionalDocumentAuthorizer;

/**
 * Extension to TransactionalDocumentAuthorizer interface which adds financial-document-specific methods.
 */
public interface AccountingDocumentAuthorizer extends TransactionalDocumentAuthorizer {

    /**
     * Initially wanted to use a Set, but JSTL doesn't seem to allow me to navigate Sets as easily as Maps. Initially used Account
     * objects as keys, but the Accounts of AccountingLines are sometimes left unpopulated when you reach the JSP, so I had to add a
     * method to Account and to AccountingLine which would generate a well-formatted String from the primitive account-related keys
     * in an Account or AccountingLine; that well-formatted String is now used as the key in this Map.
     * 
     * @param document
     * @param user
     * @return Map of Account objects, indexed by accountKey (return value of account.buildAccountKey), which the given user should
     *         be allowed to edit
     */
    public Map getEditableAccounts(TransactionalDocument document, Person user);

    /**
     * This method takes a list of accounting lines, and it returns a map with the keys being well-formatted representations of the
     * primary keys of the accounts that the given user can actually edit.
     * 
     * @param lines the accountingLine objects to check for editability.
     * @param user the user to authorize each accounting line for
     * @return a map with keys holding well formated primary keys of the editable accounts.
     */
    public Map getEditableAccounts(List<AccountingLine> lines, Person user);
}

