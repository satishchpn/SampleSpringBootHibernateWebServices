package com.demo.endpoint;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.demo.service.StudentService;
import com.demo.to.StudentBean;

@Component
@Scope("prototype")
@Path("/StudentEndpoint")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StudentEndpoint {

	@Autowired
	StudentService studentService;
	
	private static Logger log=Logger.getLogger(StudentEndpoint.class);

	{
		log.debug("----------IB----------" + this);
	}

	static {
		log.debug("-----------SB-------------");
	}
	
	@PostConstruct
	public void init(){
		log.debug("init called from StudentEndpoint : "+this+"\tStudentService : "+studentService);
	}

	@POST
	public Response createStudent(StudentBean bean, @Context UriInfo uriInfo) {
		log.info("createStudent called..." + bean);
		studentService.saveStudent(bean);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(bean.getStudentId()));
		return Response.created(builder.build()).build();
	}

	@GET
	@Path("{studentId}")
	public Response getStudent(@PathParam("studentId") long id) {
		log.info("----------getStudent----------");
		return Response.ok(studentService.getStudentById(id)).build();
	}

	@GET
	public Response getAllStudent() {
		log.info("----------getAllStudent----------");
		return Response.ok(studentService.getAllStudent()).build();
	}

	@PUT
	@Path("{studentId}")
	public Response updateStudent(StudentBean bean, @PathParam("studentId") long id) {
		log.info("updateStudent called..." + bean + "\tid: " + id);
		studentService.updateStudent(bean, id);
		return Response.ok().build();
	}

	@DELETE
	@Path("{studentId}")
	public Response deleteStudent(@PathParam("studentId") long id) {
		log.info("updateStudent called..." + id);
		studentService.deleteStudent(id);
		return Response.ok().build();
	}

}
