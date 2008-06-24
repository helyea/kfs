/*
 * Copyright 2006-2007 The Kuali Foundation.
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

package org.kuali.kfs.fp.businessobject;

import static org.kuali.kfs.sys.KFSPropertyConstants.ACCOUNT_NUMBER;
import static org.kuali.kfs.sys.KFSPropertyConstants.AMOUNT;
import static org.kuali.kfs.sys.KFSPropertyConstants.CHART_OF_ACCOUNTS_CODE;
import static org.kuali.kfs.sys.KFSPropertyConstants.FINANCIAL_SUB_OBJECT_CODE;
import static org.kuali.kfs.sys.KFSPropertyConstants.ORGANIZATION_REFERENCE_ID;
import static org.kuali.kfs.sys.KFSPropertyConstants.PROJECT_CODE;
import static org.kuali.kfs.sys.KFSPropertyConstants.SUB_ACCOUNT_NUMBER;

import java.util.Map;

import org.kuali.kfs.fp.document.IndirectCostAdjustmentDocument;
import org.kuali.kfs.fp.document.validation.impl.IndirectCostAdjustmentDocumentRuleConstants;
import org.kuali.kfs.sys.businessobject.AccountingLineParserBase;
import org.kuali.kfs.sys.businessobject.SourceAccountingLine;
import org.kuali.kfs.sys.businessobject.TargetAccountingLine;
import org.kuali.kfs.sys.context.SpringContext;
import org.kuali.kfs.sys.service.ParameterService;

/**
 * This class represents an <code>IndirectCostAdjustmentDocument</code> accounting line parser.
 * 
 * @see org.kuali.kfs.fp.document.IndirectCostAdjustmentDocument
 */
public class IndirectCostAdjustmentDocumentAccountingLineParser extends AccountingLineParserBase {
    private static final String[] FORMAT = { CHART_OF_ACCOUNTS_CODE, ACCOUNT_NUMBER, SUB_ACCOUNT_NUMBER, FINANCIAL_SUB_OBJECT_CODE, PROJECT_CODE, ORGANIZATION_REFERENCE_ID, AMOUNT };

    /**
     * @see org.kuali.core.bo.AccountingLineParserBase#getSourceAccountingLineFormat()
     */
    @Override
    public String[] getSourceAccountingLineFormat() {
        return FORMAT;
    }

    /**
     * @see org.kuali.core.bo.AccountingLineParserBase#getTargetAccountingLineFormat()
     */
    @Override
    public String[] getTargetAccountingLineFormat() {
        return FORMAT;
    }

    /**
     * @see org.kuali.core.bo.AccountingLineParserBase#performCustomSourceAccountingLinePopulation(java.util.Map,
     *      org.kuali.core.bo.SourceAccountingLine, java.lang.String)
     */
    @Override
    protected void performCustomSourceAccountingLinePopulation(Map<String, String> attributeValueMap, SourceAccountingLine sourceAccountingLine, String accountingLineAsString) {
        super.performCustomSourceAccountingLinePopulation(attributeValueMap, sourceAccountingLine, accountingLineAsString);
        String financialObjectCode = SpringContext.getBean(ParameterService.class).getParameterValue(IndirectCostAdjustmentDocument.class, IndirectCostAdjustmentDocumentRuleConstants.GRANT_OBJECT_CODE);
        sourceAccountingLine.setFinancialObjectCode(financialObjectCode);
    }

    /**
     * @see org.kuali.core.bo.AccountingLineParserBase#performCustomTargetAccountingLinePopulation(java.util.Map,
     *      org.kuali.core.bo.TargetAccountingLine, java.lang.String)
     */
    @Override
    protected void performCustomTargetAccountingLinePopulation(Map<String, String> attributeValueMap, TargetAccountingLine targetAccountingLine, String accountingLineAsString) {
        super.performCustomTargetAccountingLinePopulation(attributeValueMap, targetAccountingLine, accountingLineAsString);
        String financialObjectCode = SpringContext.getBean(ParameterService.class).getParameterValue(IndirectCostAdjustmentDocument.class, IndirectCostAdjustmentDocumentRuleConstants.RECEIPT_OBJECT_CODE);
        targetAccountingLine.setFinancialObjectCode(financialObjectCode);
    }

}
