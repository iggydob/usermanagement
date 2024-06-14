package bg.energopro.resource;

import bg.energopro.domain.User;
import bg.energopro.dto.UserDto;
import bg.energopro.exceptions.ApiException;
import bg.energopro.exceptions.*;
import bg.energopro.form.*;
import bg.energopro.service.UserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Path("/users")
@RequiredArgsConstructor
@Transactional(SUPPORTS)
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @Path("/create")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new user.", description = "Returns a newly created user.")
    @APIResponse(responseCode = "200", description = "Create a new customer.")
    @APIResponse(responseCode = "400", description = "User with this email already exists.")
    public Response createUser(@Valid User user) {
        try {
            UserDto userDto = userService.createUser(user);
            return Response.ok(userDto).build();
        } catch (EntityDuplicateException exception) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(exception.getMessage())
                    .build();
        }
    }

    @PATCH
    @Path("/update")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a user.", description = "Updates user details. Returns updated user.")
    @APIResponse(responseCode = "200", description = "User details updated.")
    @APIResponse(responseCode = "404", description = "Parameter is missing.")
    @APIResponse(responseCode = "400", description = "User with this email not found.")
    public Response updateUserDetails(@Valid UpdateForm user) {
        try {
            UserDto updatedUser = userService.updateUserDetails(user);
            return Response.ok(updatedUser).build();
        } catch (ApiException exception) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(exception.getMessage())
                    .build();
        } catch (EntityNotFoundException exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get a user by email.", description = "Fetch user by email.")
    @APIResponse(responseCode = "200", description = "User fetched.")
    @APIResponse(responseCode = "400", description = "User with this email not found.")
    public Response getUserByEmail(@PathParam("email") String email) {
        try {
            UserDto user = userService.getUserByEmail(email);
            return Response.ok(user).build();
        } catch (EntityNotFoundException exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/delete")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete a user.", description = "Delete a user. Returns deleted user.")
    @APIResponse(responseCode = "200", description = "User deleted.")
    @APIResponse(responseCode = "400", description = "User not found.")
    public Response deleteUser(@Valid UserDto user) {
        try {
            UserDto userDto = userService.deleteUser(user);
            return Response.ok(userDto).build();
        } catch (EntityNotFoundException exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get/filter users.", description = "Get all users if no query parameters added.")
    @APIResponse(responseCode = "200", description = "User(s) fetched.")
    public Response filterUsers(
            @QueryParam("firstName") String firstName,
            @QueryParam("lastName") String lastName,
            @QueryParam("email") String email) {

        FilterForm filterForm = new FilterForm(firstName, lastName, email);
        List<User> users = userService.filterUsers(filterForm);

        return Response.ok(users).build();
    }
}
