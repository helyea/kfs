/*
 * Copyright 2010 The Kuali Foundation.
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
package org.kuali.kfs.module.endow.dataaccess;

import org.kuali.kfs.module.endow.businessobject.TransactionArchiveSecurity;

public interface TransactionArchiveSecurityDao {

    /**
     * Gets a transactionArchiveSecurity by primary keys.
     * 
     * @param documentNumber, lineNumber, lineTypeCode
     * @return a transactionArchive
     */
    public  TransactionArchiveSecurity getByPrimaryKey(String documentNumber, int lineNumber, String lineTypeCode);
}
