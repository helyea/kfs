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
package org.kuali.kfs.module.cg.document.authorization;

import org.kuali.kfs.sys.document.authorization.FinancialSystemMaintenanceDocumentAuthorizerBase;

/**
 * Authorizer class for ResearchRiskTypeMaintenanceDocument - allows for targeted field disabling.
 */
public class ResearchRiskTypeMaintenanceDocumentAuthorizer extends FinancialSystemMaintenanceDocumentAuthorizerBase {
// TODO move to presentation controller
//    @Override
//    public void addMaintenanceDocumentRestrictions(MaintenanceDocumentAuthorizations auths, MaintenanceDocument document, Person user) {
//        ResearchRiskType researchRisk = (ResearchRiskType) document.getNewMaintainableObject().getBusinessObject();
//        BusinessObjectService service = SpringContext.getBean(BusinessObjectService.class);
//        ResearchRiskType persistedResearchRisk = (ResearchRiskType) service.retrieve(researchRisk);
//
//        // If the research risk exists in db, set read-only fields
//        if (ObjectUtils.isNotNull(persistedResearchRisk) && persistedResearchRisk.getResearchRiskTypeCode() != null) {
//            auths.addReadonlyAuthField(CGPropertyConstants.RESEARCH_RISK_TYPE_DESCRIPTION);
//            auths.addReadonlyAuthField(CGPropertyConstants.CONTROL_ATTRIBUTE_TYPE_CODE);
//        }
//    }
}

