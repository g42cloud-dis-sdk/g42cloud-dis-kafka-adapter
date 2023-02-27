/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.g42cloud.dis.adapter.common.consumer;

import com.g42cloud.dis.Constants;
import com.g42cloud.dis.DISClient;
import com.g42cloud.dis.DISConfig;
import com.g42cloud.dis.core.DefaultRequest;
import com.g42cloud.dis.core.Request;
import com.g42cloud.dis.core.http.HttpMethodName;
import com.g42cloud.dis.core.restresource.AppsResource;
import com.g42cloud.dis.core.restresource.ConsumersResource;
import com.g42cloud.dis.core.restresource.HeartbeatsResource;
import com.g42cloud.dis.core.restresource.PartitionsResource;
import com.g42cloud.dis.core.restresource.ResourcePathBuilder;
import com.g42cloud.dis.iface.app.IAppService;
import com.g42cloud.dis.iface.coordinator.ICoordinatorService;
import com.g42cloud.dis.iface.coordinator.request.HeartbeatRequest;
import com.g42cloud.dis.iface.coordinator.request.JoinGroupRequest;
import com.g42cloud.dis.iface.coordinator.request.LeaveGroupRequest;
import com.g42cloud.dis.iface.coordinator.request.SyncGroupRequest;
import com.g42cloud.dis.iface.coordinator.response.HeartbeatResponse;
import com.g42cloud.dis.iface.coordinator.response.JoinGroupResponse;
import com.g42cloud.dis.iface.coordinator.response.SyncGroupResponse;
import org.apache.http.HttpRequest;

class InnerDisClient extends DISClient implements ICoordinatorService, IAppService {

    public InnerDisClient(DISConfig disConfig) {
        super(disConfig);
    }

    public InnerDisClient() {
        super();
    }

    @Override
    public HeartbeatResponse handleHeartbeatRequest(HeartbeatRequest heartbeatRequest) {
        Request<HttpRequest> request = new DefaultRequest<>(Constants.SERVICENAME);
        request.setHttpMethod(HttpMethodName.POST);

        final String resourcePath =
                ResourcePathBuilder.standard()
                        .withProjectId(disConfig.getProjectId())
                        .withResource(new AppsResource(heartbeatRequest.getGroupId()))
                        .withResource(new ConsumersResource(heartbeatRequest.getClientId()))
                        .withResource(new HeartbeatsResource(null, null))
                        .build();

        request.setResourcePath(resourcePath);
        setEndpoint(request, disConfig.getManagerEndpoint());
        HeartbeatResponse result = request(heartbeatRequest, request, HeartbeatResponse.class);
        return result;
    }

    @Override
    public JoinGroupResponse handleJoinGroupRequest(JoinGroupRequest joinGroupRequest) {
        Request<HttpRequest> request = new DefaultRequest<>(Constants.SERVICENAME);
        request.setHttpMethod(HttpMethodName.POST);

        final String resourcePath =
                ResourcePathBuilder.standard()
                        .withProjectId(disConfig.getProjectId())
                        .withResource(new AppsResource(joinGroupRequest.getGroupId()))
                        .withResource(new ConsumersResource(null, null))
                        .build();

        request.setResourcePath(resourcePath);
        setEndpoint(request, disConfig.getManagerEndpoint());
        JoinGroupResponse result = request(joinGroupRequest, request, JoinGroupResponse.class);
        return result;
    }

    @Override
    public SyncGroupResponse handleSyncGroupRequest(SyncGroupRequest syncGroupRequest) {
        Request<HttpRequest> request = new DefaultRequest<>(Constants.SERVICENAME);
        request.setHttpMethod(HttpMethodName.POST);

        final String resourcePath =
                ResourcePathBuilder.standard()
                        .withProjectId(disConfig.getProjectId())
                        .withResource(new AppsResource(syncGroupRequest.getGroupId()))
                        .withResource(new ConsumersResource(syncGroupRequest.getClientId()))
                        .withResource(new PartitionsResource(null, null))
                        .build();

        request.setResourcePath(resourcePath);
        setEndpoint(request, disConfig.getManagerEndpoint());
        SyncGroupResponse result = request(syncGroupRequest, request, SyncGroupResponse.class);
        return result;
    }

    @Override
    public void handleLeaveGroupRequest(LeaveGroupRequest leaveGroupRequest) {
        Request<HttpRequest> request = new DefaultRequest<>(Constants.SERVICENAME);
        request.setHttpMethod(HttpMethodName.DELETE);

        final String resourcePath =
                ResourcePathBuilder.standard()
                        .withProjectId(disConfig.getProjectId())
                        .withResource(new AppsResource(leaveGroupRequest.getGroupId()))
                        .withResource(new ConsumersResource(leaveGroupRequest.getClientId()))
                        .build();

        request.setResourcePath(resourcePath);
        setEndpoint(request, disConfig.getManagerEndpoint());
        request(null, request, null);
    }
}
