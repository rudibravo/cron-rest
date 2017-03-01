package br.com.intelie;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.intelie.controller.Jobs;
import br.com.intelie.model.Job;

/**
 * Root resource
 */
@Path("jobs")
public class JobsResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Job> get() {
		return Jobs.getInstance().getJobs();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void add(Job job) {
		Jobs.getInstance().addJob(job);
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") String id) {
		Jobs.getInstance().deleteJob(id);
	}
}
