package com.sismics.docs.rest.resource;

import com.sismics.docs.core.model.jpa.UserRequest;
import com.sismics.docs.core.service.UserRequestService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * REST API for user requests.
 */
@Path("/userRequests")
public class UserRequestResource {

    private final UserRequestService userRequestService;

    public UserRequestResource() {
        this.userRequestService = new UserRequestService(new com.sismics.docs.core.dao.UserRequestDao());
    }

    /**
     * 接收表单提交的新用户请求
     */
    @POST
    public Response submitForm(
            @FormParam("email") String email,
            @FormParam("name") String name,
            @FormParam("message") String message
    ) {
        if (email == null || name == null || message == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing required form parameters.")
                    .build();
        }

        UserRequest userRequest = new UserRequest();
        userRequest.setId(UUID.randomUUID().toString());
        userRequest.setEmail(email);
        userRequest.setName(name);
        userRequest.setMessage(message);
        userRequest.setStatus("PENDING");
        userRequest.setCreateDate(new Date());

        userRequestService.submitRequest(userRequest);
        return Response.status(Response.Status.CREATED).build();
    }

    /**
     * 获取所有待审批的请求
     */
//    @GET
//    public List<UserRequest> getPending() {
//        return userRequestService.getPendingRequests();
//    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPendingText() {
        List<UserRequest> list = userRequestService.getPendingRequests();
        StringBuilder sb = new StringBuilder();
        for (UserRequest ur : list) {
            sb.append("ID: ").append(ur.getId())
                    .append(", Name: ").append(ur.getName())
                    .append(", Message: ").append(ur.getMessage())
                    .append(", Email: ").append(ur.getEmail())
                    .append(", Status: ").append(ur.getStatus())
                    .append(", Created: ").append(ur.getCreateDate())
                    .append("\n");
        }
        return sb.toString();
    }

    /**
     * 批准某个请求
     */
    @POST
    @Path("/{id}/approve")
    public Response approve(@PathParam("id") String id) {
        boolean success = userRequestService.approve(id);
        if (!success) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Request not found or already handled")
                    .build();
        }
        return Response.ok().build();
    }

    /**
     * 拒绝某个请求
     */
    @POST
    @Path("/{id}/reject")
    public Response reject(@PathParam("id") String id) {
        boolean success = userRequestService.reject(id);
        if (!success) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Request not found or already handled")
                    .build();
        }
        return Response.ok().build();
    }
}
