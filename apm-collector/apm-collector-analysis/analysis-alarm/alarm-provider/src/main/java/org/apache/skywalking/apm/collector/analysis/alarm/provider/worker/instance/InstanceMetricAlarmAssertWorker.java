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

package org.apache.skywalking.apm.collector.analysis.alarm.provider.worker.instance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.skywalking.apm.collector.analysis.alarm.define.graph.AlarmWorkerIdDefine;
import org.apache.skywalking.apm.collector.analysis.alarm.provider.worker.AlarmAssertWorker;
import org.apache.skywalking.apm.collector.analysis.alarm.provider.worker.AlarmAssertWorkerProvider;
import org.apache.skywalking.apm.collector.configuration.ConfigurationModule;
import org.apache.skywalking.apm.collector.configuration.service.IInstanceAlarmRuleConfig;
import org.apache.skywalking.apm.collector.core.module.ModuleManager;
import org.apache.skywalking.apm.collector.core.util.Const;
import org.apache.skywalking.apm.collector.storage.StorageModule;
import org.apache.skywalking.apm.collector.storage.dao.ui.IInstanceUIDAO;
import org.apache.skywalking.apm.collector.storage.table.MetricSource;
import org.apache.skywalking.apm.collector.storage.table.alarm.AlarmType;
import org.apache.skywalking.apm.collector.storage.table.alarm.InstanceAlarm;
import org.apache.skywalking.apm.collector.storage.table.instance.InstanceMetric;
import org.apache.skywalking.apm.collector.storage.table.register.Instance;

/**
 * @author peng-yongsheng
 */
public class InstanceMetricAlarmAssertWorker extends AlarmAssertWorker<InstanceMetric, InstanceAlarm> {

    private final Gson gson = new Gson();
    private final IInstanceUIDAO instanceDAO;
    private final IInstanceAlarmRuleConfig instanceAlarmRuleConfig;

    public InstanceMetricAlarmAssertWorker(ModuleManager moduleManager) {
        super(moduleManager);
        this.instanceDAO = moduleManager.find(StorageModule.NAME).getService(IInstanceUIDAO.class);
        this.instanceAlarmRuleConfig = moduleManager.find(ConfigurationModule.NAME).getService(IInstanceAlarmRuleConfig.class);
    }

    @Override
    public int id() {
        return AlarmWorkerIdDefine.INSTANCE_METRIC_ALARM_ASSERT_WORKER_ID;
    }

    @Override
    protected InstanceAlarm newAlarmObject(String id, InstanceMetric inputMetric) {
        InstanceAlarm instanceAlarm = new InstanceAlarm();
        instanceAlarm.setId(id + Const.ID_SPLIT + inputMetric.getInstanceId());
        instanceAlarm.setApplicationId(inputMetric.getApplicationId());
        instanceAlarm.setInstanceId(inputMetric.getInstanceId());
        return instanceAlarm;
    }

    @Override
    protected void generateAlarmContent(InstanceAlarm alarm, double threshold) {
        Instance instance = instanceDAO.getInstance(alarm.getInstanceId());
        JsonObject osInfo = gson.fromJson(instance.getOsInfo(), JsonObject.class);
        String serverName = Const.UNKNOWN;
        if (osInfo != null && osInfo.has("hostName")) {
            serverName = osInfo.get("hostName").getAsString();
        }

        String clientOrServer = "服务端";
        if (MetricSource.Caller.getValue() == alarm.getSourceValue()) {
            clientOrServer = "客户端";
        }

        if (AlarmType.ERROR_RATE.getValue() == alarm.getAlarmType()) {
            alarm.setAlarmContent(clientOrServer + "检测到主机[" + serverName + "]的成功率低于" + threshold);
        } else if (AlarmType.SLOW_RTT.getValue() == alarm.getAlarmType()) {
            alarm.setAlarmContent(clientOrServer + "检测到主机[" + serverName + "]的响应时间慢于" + threshold + " ms.");
        }
    }

    @Override
    protected Double calleeErrorRateThreshold() {
        return instanceAlarmRuleConfig.calleeErrorRateThreshold();
    }

    @Override
    protected Double callerErrorRateThreshold() {
        return instanceAlarmRuleConfig.callerErrorRateThreshold();
    }

    @Override
    protected Double calleeAverageResponseTimeThreshold() {
        return instanceAlarmRuleConfig.calleeAverageResponseTimeThreshold();
    }

    @Override
    protected Double callerAverageResponseTimeThreshold() {
        return instanceAlarmRuleConfig.callerAverageResponseTimeThreshold();
    }

    public static class Factory extends AlarmAssertWorkerProvider<InstanceMetric, InstanceAlarm, InstanceMetricAlarmAssertWorker> {

        public Factory(ModuleManager moduleManager) {
            super(moduleManager);
        }

        @Override
        public InstanceMetricAlarmAssertWorker workerInstance(ModuleManager moduleManager) {
            return new InstanceMetricAlarmAssertWorker(moduleManager);
        }

        @Override
        public int queueSize() {
            return 1024;
        }
    }
}
