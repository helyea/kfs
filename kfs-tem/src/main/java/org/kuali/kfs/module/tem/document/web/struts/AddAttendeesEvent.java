/*
 * The Kuali Financial System, a comprehensive financial management system for higher education.
 * 
 * Copyright 2005-2014 The Kuali Foundation
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.kuali.kfs.module.tem.document.web.struts;

import static org.kuali.kfs.module.tem.TemPropertyConstants.NEW_ATTENDEE_LINE;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.kuali.kfs.module.tem.businessobject.Attendee;
import org.kuali.kfs.module.tem.document.TravelEntertainmentDocument;
import org.kuali.kfs.module.tem.document.service.TravelEntertainmentDocumentService;
import org.kuali.kfs.module.tem.document.validation.event.AddAttendeeLineEvent;
import org.kuali.kfs.module.tem.document.web.bean.TravelEntertainmentMvcWrapperBean;
import org.kuali.rice.krad.service.KualiRuleService;
import org.kuali.rice.krad.util.GlobalVariables;

public class AddAttendeesEvent implements Observer {
    private KualiRuleService kualiRuleService;
    private TravelEntertainmentDocumentService travelEntertainmentDocumentService;

    @Override
    public void update(final Observable observable, Object arg) {
        if (!(arg instanceof TravelEntertainmentMvcWrapperBean)) {
            return;
        }
        final TravelEntertainmentMvcWrapperBean wrapper = (TravelEntertainmentMvcWrapperBean) arg;

        final TravelEntertainmentDocument document = (TravelEntertainmentDocument)wrapper.getTravelDocument();
        final Attendee newAttendeeLine = wrapper.getNewAttendeeLine();

        if (newAttendeeLine != null) {
            newAttendeeLine.refreshReferenceObject("id");
        }

        getTravelEntertainmentDocumentService().handleNewAttendee(newAttendeeLine);
        int errCount = GlobalVariables.getMessageMap().getErrorCount();
        if(errCount > 0) {
            return;
        } 

        boolean rulePassed = true;

        // check any business rules
        rulePassed &= getRuleService().applyRules(new AddAttendeeLineEvent<Attendee>(NEW_ATTENDEE_LINE, document, newAttendeeLine));

        if (rulePassed) {
            if (newAttendeeLine != null) {
                document.addAttendee(newAttendeeLine);
            }

            Attendee newAttendee = wrapper.getNewAttendeeLine();

            List<Attendee> newAttendeeLines = new ArrayList<Attendee>();
            List<Attendee> attendeeLines = wrapper.getNewAttendeeLines();

            int selectedLine = -1;
            for (int i = 0; i < attendeeLines.size(); i++) {
                Attendee attendeeLine = attendeeLines.get(i);
                if (i == 0 && attendeeLine.equals(newAttendee)) {
                    selectedLine = 0;
                    attendeeLine = new Attendee();
                    newAttendeeLines.add(attendeeLine);
                }
                else {
                    if (attendeeLine.equals(newAttendee)) {
                        Attendee line = new Attendee();
                        line.setAttendeeType(attendeeLine.getAttendeeType());
                        line.setCompany(attendeeLine.getCompany());
                        line.setTitle(attendeeLine.getTitle());
                        line.setName(attendeeLine.getName());

                        newAttendeeLines.add(line);
                    }
                    else {
                        newAttendeeLines.add(attendeeLine);
                    }
                }
            }

            if (selectedLine == 0) {
                Attendee attendee = new Attendee();
                attendee.setAttendeeType(newAttendee.getAttendeeType());
                attendee.setCompany(newAttendee.getCompany());
                attendee.setTitle(newAttendee.getTitle());
                attendee.setName(newAttendee.getName());
                newAttendeeLines.add(attendee);
            }

            wrapper.setNewAttendeeLines(newAttendeeLines);
            wrapper.setNewAttendeeLine(new Attendee());
            document.setAttendeeDetail(null);
        }
    }


    public KualiRuleService getKualiRuleService() {
        return kualiRuleService;
    }

    public void setKualiRuleService(KualiRuleService kualiRuleService) {
        this.kualiRuleService = kualiRuleService;
    }


    public TravelEntertainmentDocumentService getTravelEntertainmentDocumentService() {
        return travelEntertainmentDocumentService;
    }


    public void setTravelEntertainmentDocumentService(TravelEntertainmentDocumentService travelEntertainmentDocumentService) {
        this.travelEntertainmentDocumentService = travelEntertainmentDocumentService;
    }


    /**
     * Sets the kualiRulesService attribute.
     * 
     * @return Returns the kualiRuleService.
     */
    public void setRuleService(final KualiRuleService kualiRuleService) {
        this.kualiRuleService = kualiRuleService;
    }

    /**
     * Gets the kualiRulesService attribute.
     * 
     * @return Returns the kualiRuleseService.
     */
    protected KualiRuleService getRuleService() {
        return kualiRuleService;
    }
}
