package mk.nvasilev.magnolia.rest.service.v1.employee.endpoint;

import info.magnolia.rest.AbstractEndpoint;
import info.magnolia.rest.registry.ConfiguredEndpointDefinition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import mk.nvasilev.magnolia.rest.service.v1.employee.EmployeeService;
import mk.nvasilev.magnolia.rest.service.v1.employee.dto.EmployeeDTO;
import mk.nvasilev.magnolia.rest.service.v1.employee.dto.GenderDTO;
import mk.nvasilev.magnolia.rest.service.v1.employee.form.EmployeeForm;
import org.hibernate.validator.constraints.NotBlank;
import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.annotations.cache.NoCache;

import javax.inject.Inject;
import javax.validation.Valid;
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
import javax.ws.rs.core.UriInfo;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Employees endpoint.
 */
@Api(value = "/v1/employees", description = "Employees Endpoint")
@Path("awesome-service/v1/")
@NoCache
public class EmployeesEndpoint extends AbstractEndpoint<ConfiguredEndpointDefinition> {

    private static final String STATUS_MESSAGE_OK = "OK";
    private static final String STATUS_MESSAGE_UNAUTHORIZED = "Unauthorized";
    private static final String STATUS_MESSAGE_EMPLOYEES_NOT_FOUND = "Employees not found";
    private static final String STATUS_MESSAGE_ERROR_OCCURRED = "Error occurred";

    private final EmployeeService employeeService;

    @Inject
    public EmployeesEndpoint(ConfiguredEndpointDefinition endpointDefinition, EmployeeService employeeService) {
        super(endpointDefinition);
        this.employeeService = employeeService;
    }

    @GET
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch employees.", notes = "Fetch the employees.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = STATUS_MESSAGE_OK, response = Set.class),
            @ApiResponse(code = 401, message = STATUS_MESSAGE_UNAUTHORIZED),
            @ApiResponse(code = 404, message = STATUS_MESSAGE_EMPLOYEES_NOT_FOUND),
            @ApiResponse(code = 500, message = STATUS_MESSAGE_ERROR_OCCURRED)
    })
    public Response getEmployees() {
        final Set<EmployeeDTO> employees = employeeService.getEmployees();
        if(employees.size() > 0) {
            return Response.ok(employees).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/employees/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Fetch employees.", notes = "Fetch the employees.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = STATUS_MESSAGE_OK, response = EmployeeDTO.class),
            @ApiResponse(code = 401, message = STATUS_MESSAGE_UNAUTHORIZED),
            @ApiResponse(code = 404, message = STATUS_MESSAGE_EMPLOYEES_NOT_FOUND),
            @ApiResponse(code = 500, message = STATUS_MESSAGE_ERROR_OCCURRED)
    })
    public Response getEmployee(@NotBlank @PathParam("employeeId") String employeeId) {
        Optional<EmployeeDTO> employeeDTOOptional = employeeService.getEmployee(employeeId);

        if(employeeDTOOptional.isPresent()) {
            return Response.ok(employeeDTOOptional.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/employees")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Fetch employees.", notes = "Fetch the employees.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = STATUS_MESSAGE_OK, response = EmployeeDTO.class),
            @ApiResponse(code = 401, message = STATUS_MESSAGE_UNAUTHORIZED),
            @ApiResponse(code = 404, message = STATUS_MESSAGE_EMPLOYEES_NOT_FOUND),
            @ApiResponse(code = 500, message = STATUS_MESSAGE_ERROR_OCCURRED)
    })
    public Response addEmployee(@Valid @Form EmployeeForm employeeForm, @Context UriInfo uriInfo) {
        final EmployeeDTO employeeDTO = employeeService.addEmployee(convertToEmployeeDTO(employeeForm));
        return Response.created(uriInfo.getAbsolutePathBuilder().path(employeeDTO.getId()).build())
                .entity(employeeDTO).build();
    }

    @PUT
    @Path("/employees/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Fetch employees.", notes = "Fetch the employees.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = STATUS_MESSAGE_OK, response = EmployeeDTO.class),
            @ApiResponse(code = 401, message = STATUS_MESSAGE_UNAUTHORIZED),
            @ApiResponse(code = 404, message = STATUS_MESSAGE_EMPLOYEES_NOT_FOUND),
            @ApiResponse(code = 500, message = STATUS_MESSAGE_ERROR_OCCURRED)
    })
    public Response editEmployee(@Valid @PathParam("employeeId") String employeeId, @Valid @Form EmployeeForm employeeForm, @Context UriInfo uriInfo) {
        final Optional<EmployeeDTO> employeeDTOOptional = employeeService.editEmployee(employeeId, convertToEmployeeDTO(employeeId, employeeForm));

        if(employeeDTOOptional.isPresent()) {
            return Response.ok(employeeDTOOptional.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @DELETE
    @Path("/employees/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @ApiOperation(value = "Fetch employees.", notes = "Fetch the employees.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = STATUS_MESSAGE_OK),
            @ApiResponse(code = 401, message = STATUS_MESSAGE_UNAUTHORIZED),
            @ApiResponse(code = 404, message = STATUS_MESSAGE_EMPLOYEES_NOT_FOUND),
            @ApiResponse(code = 500, message = STATUS_MESSAGE_ERROR_OCCURRED)
    })
    public Response deleteEmployee(@Valid @PathParam("employeeId") String employeeId) {
        boolean deleted = employeeService.deleteEmployee(employeeId);
        if(deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    private static EmployeeDTO convertToEmployeeDTO(String id, EmployeeForm employeeForm) {
        String identifier = null;
        if(id == null) {
            identifier = UUID.randomUUID().toString();
        } else {
            identifier = id;
        }
        return EmployeeDTO.Builder.withId(identifier)
                .withFirstName(employeeForm.getFirstName())
                .withLastName(employeeForm.getLastName())
                .withGender(GenderDTO.valueOf(employeeForm.getGender()))
                .withDateOfBirth(LocalDate.parse(employeeForm.getDateOfBirth()))
                .withDepartment(employeeForm.getDepartment())
                .createInstance();
    }


    private static EmployeeDTO convertToEmployeeDTO(EmployeeForm employeeForm) {
        return convertToEmployeeDTO(null, employeeForm);
    }

}
