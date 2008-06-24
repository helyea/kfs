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
package org.kuali.kfs.module.bc.document.web.struts;

import java.util.List;

import org.kuali.kfs.module.bc.BCConstants;
import org.kuali.kfs.module.bc.businessobject.BudgetConstructionIntendedIncumbent;
import org.kuali.kfs.module.bc.businessobject.PendingBudgetConstructionAppointmentFunding;
import org.kuali.kfs.module.bc.businessobject.PendingBudgetConstructionAppointmentFundingAware;


public class IncumbentSalarySettingForm extends DetailSalarySettingForm {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(IncumbentSalarySettingForm.class);

    private BudgetConstructionIntendedIncumbent budgetConstructionIntendedIncumbent;

    /**
     * Constructs a IncumbentSalarySettingForm.java.
     */
    public IncumbentSalarySettingForm() {
        super();

        setBudgetConstructionIntendedIncumbent(new BudgetConstructionIntendedIncumbent());
    }

    /**
     * @see org.kuali.kfs.module.bc.document.web.struts.DetailSalarySettingForm#populateBCAFLines()
     */
    @Override
    public void populateBCAFLines() {
        List<PendingBudgetConstructionAppointmentFunding> appointmentFundings = budgetConstructionIntendedIncumbent.getPendingBudgetConstructionAppointmentFunding();
        for (PendingBudgetConstructionAppointmentFunding appointmentFunding : appointmentFundings) {
            this.populateBCAFLine(appointmentFunding);
        }
    }

    /**
     * Gets the budgetConstructionIntendedIncumbent attribute.
     * 
     * @return Returns the budgetConstructionIntendedIncumbent.
     */
    public BudgetConstructionIntendedIncumbent getBudgetConstructionIntendedIncumbent() {
        return budgetConstructionIntendedIncumbent;
    }

    /**
     * Sets the budgetConstructionIntendedIncumbent attribute value.
     * 
     * @param budgetConstructionIntendedIncumbent The budgetConstructionIntendedIncumbent to set.
     */
    public void setBudgetConstructionIntendedIncumbent(BudgetConstructionIntendedIncumbent budgetConstructionIntendedIncumbent) {
        this.budgetConstructionIntendedIncumbent = budgetConstructionIntendedIncumbent;
    }

    /**
     * @see org.kuali.kfs.module.bc.document.web.struts.DetailSalarySettingForm#getBudgetConstructionDetail()
     */
    @Override
    public PendingBudgetConstructionAppointmentFundingAware getBudgetConstructionDetail() {
        return this.budgetConstructionIntendedIncumbent;
    }

    /**
     * @see org.kuali.kfs.module.bc.document.web.struts.DetailSalarySettingForm#getRefreshCallerName()
     */
    @Override
    public String getRefreshCallerName() {
        return BCConstants.INCUMBENT_SALARY_SETTING_REFRESH_CALLER;
    }
}
