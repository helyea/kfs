<%--
 Copyright 2006-2007 The Kuali Foundation.
 
 Licensed under the Educational Community License, Version 1.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl1.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/jsp/kfs/kfsTldHeader.jsp"%>
	
	<c:set var="orgAttributes" value="${DataDictionary.Org.attributes}" />
	<c:set var="arDocHeaderAttributes" value="${DataDictionary.AccountsReceivableDocumentHeader.attributes}" />
	<c:set var="invoiceAttributes" value="${DataDictionary.CustomerInvoiceDocument.attributes}" />
	<c:set var="accountAttributes" value="${DataDictionary.Account.attributes}" />
	
<kul:page  showDocumentInfo="false"
	headerTitle="Billing Statement Generation" docTitle="Billing Statement Generation" renderMultipart="true"
	transactionalDocument="false" htmlFormAction="arCustomerStatement" errorKey="foo">

	 <table cellpadding="0" cellspacing="0" class="datatable" summary="Billing Statement">
			<tr>		
                <th align=right valign=middle class="bord-l-b" style="width: 25%;">
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${invoiceAttributes.billByChartOfAccountCode}" /></div>
                </th>
                <td align=left valign=middle class="datacell" style="width: 25%;">
					<kul:htmlControlAttribute attributeEntry="${invoiceAttributes.billByChartOfAccountCode}" property="chartCode"  />	
                    <kul:lookup boClassName="org.kuali.kfs.coa.businessobject.Chart"  fieldConversions="chartOfAccountsCode:chartCode"  />
                </td>
				                       
            </tr>
            <tr>
				<th align=right valign=middle class="bord-l-b">
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${invoiceAttributes.billedByOrganizationCode}" /></div>
                </th>
                <td align=left valign=middle class="datacell">
                    <kul:htmlControlAttribute attributeEntry="${invoiceAttributes.billedByOrganizationCode}" property="orgCode"  />
                    <kul:lookup boClassName="org.kuali.kfs.coa.businessobject.Org"  fieldConversions="organizationCode:orgCode" lookupParameters="orgCode:organizationCode,chartCode:chartOfAccountsCode"/>
                </td>                
				            
            </tr>
             <tr>
				<th align=right valign=middle class="bord-l-b">
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${arDocHeaderAttributes.customerNumber}"/></div>
                </th>
                <td align=left valign=middle class="datacell">
                	<kul:htmlControlAttribute attributeEntry="${arDocHeaderAttributes.customerNumber}" property="customerNumber"  />
                	<kul:lookup boClassName="org.kuali.kfs.module.ar.businessobject.Customer" fieldConversions="customerNbr:customerNumber" lookupParameters="customerNumber:customerNbr" />
                </td>                
				            
            </tr>
              <tr>
				<th align=right valign=middle class="bord-l-b">
                    <div align="right"><kul:htmlAttributeLabel attributeEntry="${accountAttributes.accountNumber}"/></div>
                </th>
                <td align=left valign=middle class="datacell">
                	<kul:htmlControlAttribute attributeEntry="${accountAttributes.accountNumber}" property="accountNumber"  />
                	<kul:lookup boClassName="org.kuali.kfs.coa.businessobject.Account" fieldConversions="accountNbr:accountNumber" lookupParameters="accountNumber:accountNbr" />
                </td>                
				            
            </tr>
            
            
         <th>
            <html-el:image property="methodToCall.selectOperation" styleClass="tinybutton" src="${ConfigProperties.externalizable.images.url}tinybutton-generate.gif" />
           </th>
      
        
        </tr>
        </table>
    

</kul:page>
