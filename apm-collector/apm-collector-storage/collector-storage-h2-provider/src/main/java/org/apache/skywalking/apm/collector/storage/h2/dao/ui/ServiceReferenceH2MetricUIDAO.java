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

package org.apache.skywalking.apm.collector.storage.h2.dao.ui;

import java.util.List;
import org.apache.skywalking.apm.collector.client.h2.H2Client;
import org.apache.skywalking.apm.collector.storage.dao.ui.IServiceReferenceMetricUIDAO;
import org.apache.skywalking.apm.collector.storage.h2.base.dao.H2DAO;
import org.apache.skywalking.apm.collector.storage.table.MetricSource;
import org.apache.skywalking.apm.collector.storage.ui.common.Step;
import org.apache.skywalking.apm.collector.storage.ui.service.ServiceReferenceMetricBrief;
import org.apache.skywalking.apm.collector.storage.ui.service.ServiceReferenceMetricQueryOrder;

/**
 * @author peng-yongsheng
 */
public class ServiceReferenceH2MetricUIDAO extends H2DAO implements IServiceReferenceMetricUIDAO {

    public ServiceReferenceH2MetricUIDAO(H2Client client) {
        super(client);
    }

    @Override public List<ServiceReferenceMetric> getFrontServices(Step step, long startTimeBucket, long endTimeBucket,
        MetricSource metricSource,
        int behindServiceId) {
        return null;
    }

    @Override public List<ServiceReferenceMetric> getBehindServices(Step step, long startTimeBucket, long endTimeBucket,
        MetricSource metricSource,
        int frontServiceId) {
        return null;
    }

    @Override
    public ServiceReferenceMetricBrief getServiceReferenceMetricBrief(Step step, long startSecondTimeBucket, long endSecondTimeBucket, long minDuration, long maxDuration, MetricSource metricSource, int frontApplicationId, int behindApplicationId, int limit, int from, ServiceReferenceMetricQueryOrder queryOrder) {
        return null;
    }
}
