/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.apm.collector.storage.table.alarm;

import org.apache.skywalking.apm.collector.core.data.ColumnName;

/**
 * @author peng-yongsheng
 */
public interface AlarmContactTable {
    String TABLE = "alarm_contact";
    String TABLE_TYPE = "type";
    ColumnName ALARM_CONTACT_ID = new ColumnName("alarm_contact_id", "aci");
    ColumnName CREATE_TIME = new ColumnName("create_time", "ct");
    ColumnName UPDATE_TIME = new ColumnName("update_time", "ut");
    ColumnName STATUS = new ColumnName("status", "status");
    ColumnName PHONE_NUMBER = new ColumnName("phone_number", "pn");
    ColumnName EMAIL = new ColumnName("email", "email");
    ColumnName REAL_NAME = new ColumnName("real_name", "rn");
}