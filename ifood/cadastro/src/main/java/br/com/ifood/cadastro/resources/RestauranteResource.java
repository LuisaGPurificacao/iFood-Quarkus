package br.com.ifood.cadastro.resources;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.ifood.cadastro.entities.Restaurante;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestauranteResource {

	@GET
	public List<Restaurante> listar() {
		return Restaurante.listAll();
	}
	
	@POST
	@Transactional
	public Response adicionar(Restaurante dto) {
		dto.persist();
		return Response.status(Status.CREATED).build();
	}

	@PUT
	@Path("{id}")
	@Transactional
	public void atualizar(@PathParam("id") Long id, Restaurante dto) {
		Optional<Restaurante> optional = Restaurante.findByIdOptional(id);
		if (optional.isEmpty())
			throw new NotFoundException();
		Restaurante restaurante = optional.get();
		restaurante.nome = dto.nome;
		restaurante.persist();
	}

	@DELETE
	@Path("{id}")
	@Transactional
	public void remover(@PathParam("id") Long id) {
		Optional<Restaurante> optional = Restaurante.findByIdOptional(id);
		optional.ifPresentOrElse(Restaurante::delete, () -> {throw new NotFoundException();});
	}
	
}
