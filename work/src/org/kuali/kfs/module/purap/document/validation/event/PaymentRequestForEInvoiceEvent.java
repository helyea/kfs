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
package org.kuali.kfs.module.purap.document.validation.event;

import org.kuali.kfs.module.purap.document.AccountsPayableDocument;
import org.kuali.kfs.module.purap.document.validation.CalculateAccountsPayableRule;
import org.kuali.kfs.module.purap.document.validation.impl.PaymentRequestDocumentRule;
import org.kuali.kfs.sys.KFSConstants;
import org.kuali.rice.kns.document.Document;
import org.kuali.rice.kns.rule.BusinessRule;
import org.kuali.rice.kns.rule.event.KualiDocumentEventBase;

public final class PaymentRequestForEInvoiceEvent extends KualiDocumentEventBase {

    public PaymentRequestForEInvoiceEvent(Document document) {
        this(KFSConstants.EMPTY_STRING, document);
    }

    public PaymentRequestForEInvoiceEvent(String errorPathPrefix, Document document) {
        super("Validaing for EInvoice " + getDocumentId(document), errorPathPrefix, document);
    }

    public Class getRuleInterfaceClass() {
        return PaymentRequestDocumentRule.class;
    }

    public boolean invokeRuleMethod(BusinessRule rule) {
        return ((PaymentRequestDocumentRule) rule).processRouteDocumentBusinessRules(getDocument());
    }
}
