<%--
 Copyright 2007-2009 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/jsp/sys/kfsTldHeader.jsp"%>

<c:set var="travelPaymentAttributes" value="${DataDictionary.TravelPayment.attributes}" />
<c:set var="checkAmountEntry" value="${KualiForm.editingMode['checkAmountEntry']}" />

<kul:tab tabTitle="Payment Information" defaultOpen="false" tabErrorKey="${KFSConstants.DV_PAYMENT_TAB_ERRORS},document.paymentMethodCode,${KFSConstants.DV_PAYEE_TAB_ERRORS},${KFSConstants.TRVL_SPECHAND_TAB_ERRORS},${KFSConstants.WIRETRANSFER_TAB_ERRORS},${KFSConstants.FOREIGNDRAFTS_TAB_ERRORS}">
    <div class="tab-container" align=center > 
        <h3>Payment Information</h3>
		<table cellpadding=0 class="datatable" summary="Payment Section">
            <tr>
              <th width="20%"  class="bord-l-b">
              	<div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.checkTotalAmount}"/></div>
              </th>
              <td width="30%"  class="datacell">
                <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.checkTotalAmount}" property="document.travelPayment.checkTotalAmount" readOnly="${!fullEntry&&!checkAmountEntry}"/>
              </td>
              <th width="20%"  class="bord-l-b"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.dueDate}"/></div></th>
              <td width="30%"  class="datacell">
                 <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.dueDate}" property="document.travelPayment.dueDate" datePicker="true" readOnly="${!fullEntryMode}"/>
              </td>
            </tr>
            
            <tr>
              <th  class="bord-l-b"><div align="right">Payment Type:</div></th>
              <td valign="top"  class="datacell">
                <c:if test="${taxEntryMode}">
                  <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.lienPaymentCode}" property="document.travelPayment.alienPaymentCode"/>
                  <kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.alienPaymentCode}" noColon="true" />
                  <br><br>
                </c:if>
                <c:if test="${!taxEntryMode}">
                    <kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.alienPaymentCode}"/>
                    <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.alienPaymentCode}" property="document.travelPayment.alienPaymentCode" readOnly="true"/>
                    <br><br>
                </c:if>
				<%--
                <kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.disbVchrPayeeEmployeeCode}"/> <bean:write  name="KualiForm" property="document.travelPayment.payeeEmployeeCode" /><br><br>
				<c:if test="${KualiForm.document.travelPayment.payeeTypeCode=='V'}">
                	<kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.employeePaidOutsidePayrollCode}"/><bean:write  name="KualiForm" property="document.travelPayment.employeePaidOutsidePayrollCode" /><br><br>
                </c:if>
				--%>
              </td>  
              <th width="20%"  class="bord-l-b"><div align="right">Other Considerations: </div></th>
              <td width="30%"  class="datacell">
				<c:choose>
					<c:when test="${fullEntryMode || paymentHandlingEntryMode}"> 
						<kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.attachmentCode}" property="document.travelPayment.attachmentCode" readOnly="false"/>
						<kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.attachmentCode}" noColon="true" /><br>
					</c:when>
					<c:otherwise> 
						<kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.attachmentCode}"/>
						<kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.attachmentCode}" property="document.travelPayment.attachmentCode" readOnly="true"/><br>
					</c:otherwise>
				</c:choose>
         
				<c:choose>
                 <c:when test="${fullEntryMode || specialHandlingChangingEntryMode}">        
                   <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.specialHandlingCode}" property="document.travelPayment.specialHandlingCode" onclick="specialHandlingMessage(this);" readOnly="false"/>
                   <kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.specialHandlingCode}" noColon="true" /><br>
                 </c:when>
                 <c:otherwise>
                   <kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.specialHandlingCode}"/>
                   <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.specialHandlingCode}" property="document.travelPayment.specialHandlingCode" readOnly="true"/><br>          
                 </c:otherwise>
				</c:choose>
                 
                 <c:set var="w9IndReadOnly" value="${!fullEntryMode}"/>
                 <%-- cannot change w9 indicator if it has previousely been checked --%>
                 <c:if test="${KualiForm.document.travelPayment.editW9W8BENbox==true}">  
                     <c:set var="w9IndReadOnly" value="true"/>    
                 </c:if>
                 <c:choose>
					<c:when test="${w9IndReadOnly}">    
						<kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.payeeW9CompleteCode}" property="document.travelPayment.payeeW9CompleteCode" disabled="true"/>
						<kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.payeeW9CompleteCode}" noColon="true" /><br>                     
					</c:when>
					<c:otherwise>                
						<kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.payeeW9CompleteCode}" property="document.travelPayment.payeeW9CompleteCode"/>
						<kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.payeeW9CompleteCode}" noColon="true"/><br>
					</c:otherwise>
				</c:choose>
                 
                 
				<c:choose>
                 <c:when test="${fullEntryMode}">        
                   <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.exceptionAttachedIndicator}" property="document.travelPayment.exceptionAttachedIndicator" onclick="exceptionMessage(this);" readOnly="false"/>
                   <kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.exceptionAttachedIndicator}" noColon="true" /><br>
                 </c:when>
                 <c:otherwise>
                   <kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.exceptionAttachedIndicator}"/>
                   <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.exceptionAttachedIndicator}" property="document.travelPayment.exceptionAttachedIndicator" readOnly="true"/><br>          
                 </c:otherwise>
				</c:choose>     

				<c:choose>
                 <c:when test="${immediateDisbursementEntryMode}">        
                   <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.immediatePaymentIndicator}" property="document.travelPayment.immediatePaymentIndicator" readOnly="false"/>
                   <kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.immediatePaymentIndicator}" noColon="true" /><br>
                 </c:when>
                 <c:otherwise>
                   <kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.immediatePaymentIndicator}"/>
                   <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.immediatePaymentIndicator}" property="document.travelPayment.immediatePaymentIndicator" readOnly="true"/><br>          
                 </c:otherwise>
				</c:choose>
                 </td>
            </tr>
            
            <tr>
              <th  class="bord-l-b"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.paymentMethodCode}"/></div></th>
              <td  class="datacell">
                <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.paymentMethodCode}" property="document.travelPayment.paymentMethodCode" extraReadOnlyProperty="document.travelPayment.paymentMethodName" onchange="paymentMethodMessages(this.value);" readOnly="${!fullEntryMode && !frnEntryMode}"/>
              </td>
              <th  class="bord-l-b"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.documentationLocationCode}"/></div></th>
              <td  class="datacell">
                <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.documentationLocationCode}" property="document.travelPayment.documentationLocationCode" extraReadOnlyProperty="document.travelPayment.documentationLocationName" onchange="documentationMessage(this.value);" readOnly="${!fullEntryMode}"/>
                <c:if test="${fullEntryMode}">
              		<kul:lookup boClassName="org.kuali.kfs.sys.businessobject.PaymentDocumentationLocation" fieldConversions="paymentDocumentationLocationCode:document.travelPayment.documentationLocationCode" 
              		lookupParameters="document.travelPayment.documentationLocationCode:paymentDocumentationLocationCode" />
              	</c:if>
              </td>
            </tr>
            <tr>
              <th scope="row"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.checkStubText}"/></div></th>
              <td colspan="3"><kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.checkStubText}" property="document.travelPayment.checkStubText" readOnly="${!fullEntryMode && !paymentHandlingEntryMode}"/></td>
            </tr>
        </table>
		<%-- SPECIAL HANDLING --%>
		<h3>Send Check To</h3>
	<table cellpadding=0 class="datatable" summary="Special Handling Section">          
            <tr>
              <th class="bord-l-b">&nbsp;</th>
              <th align=right valign=middle class="bord-l-b"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.specialHandlingCityName}"/></div></th>
              <td align=left valign=middle class="datacell">
                <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.specialHandlingCityName}" property="document.travelPayment.specialHandlingCityName" readOnly="${!(fullEntryMode || specialHandlingChangingEntryMode) && !paymentHandlingEntryMode}"/>  
              </td>
            </tr>
            
            <tr>
              <th align=right valign=middle class="bord-l-b"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.specialHandlingLine1Addr}"/></div></th>
              <td align=left valign=middle class="datacell">
                <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.specialHandlingLine1Addr}" property="document.travelPayment.specialHandlingLine1Addr" readOnly="${!(fullEntryMode || specialHandlingChangingEntryMode) && !paymentHandlingEntryMode}"/>  
              </td>
              <th align=right valign=middle class="bord-l-b"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.specialHandlingStateCode}"/></div></th>
              <td align=left valign=middle class="datacell">
                <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.specialHandlingStateCode}" property="document.travelPayment.specialHandlingStateCode" readOnly="${!(fullEntryMode || specialHandlingChangingEntryMode) && !paymentHandlingEntryMode}"/>  
              </td>
            </tr>
            
            <tr>
              <th align=right valign=middle class="bord-l-b"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.specialHandlingLine2Addr}"/></div></th>
              <td align=left valign=middle class="datacell">
                <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.specialHandlingLine2Addr}" property="document.travelPayment.specialHandlingLine2Addr" readOnly="${!(fullEntryMode || specialHandlingChangingEntryMode) && !paymentHandlingEntryMode}"/>  
              </td>
              <th align=right valign=middle class="bord-l-b"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.specialHandlingZipCode}"/></div></th>
              <td align=left valign=middle class="datacell">
                <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.specialHandlingZipCode}" property="document.travelPayment.specialHandlingZipCode" readOnly="${!(fullEntryMode || specialHandlingChangingEntryMode) && !paymentHandlingEntryMode}"/>  
              </td>
            </tr>
            
            <tr>
              <th align=right valign=middle class="bord-l-b"></th>
              <td align=left valign=middle class="datacell">
              </td>
              <th align=right valign=middle class="bord-l-b"><div align="right"><kul:htmlAttributeLabel attributeEntry="${travelPaymentAttributes.specialHandlingCountryCode}"/></div></th>
              <td align=left valign=middle class="datacell">
                <kul:htmlControlAttribute attributeEntry="${travelPaymentAttributes.specialHandlingCountryCode}" property="document.travelPayment.specialHandlingCountryCode" readOnly="${!(fullEntryMode || specialHandlingChangingEntryMode) && !paymentHandlingEntryMode}"/>  
              </td>
            </tr>
     </table>
	 <%-- WIRE TRANSFER --%>
	 <sys:innerWireTransfer/>
	 <%-- FOREIGN DRAFT --%>
	 <sys:innerForeignDraft/>
     </div>
</kul:tab>