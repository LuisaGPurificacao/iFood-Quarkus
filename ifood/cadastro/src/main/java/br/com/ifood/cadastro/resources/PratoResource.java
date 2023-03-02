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

import br.com.ifood.cadastro.entities.Prato;
import br.com.ifood.cadastro.entities.Restaurante;

@Path("/pratos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PratoResource {

	@GET
	@Path("{idRestaurante}")
	public List<Prato> listar(@PathParam("idRestaurante") Long idRestaurante) {
		Optional<Restaurante> optional = Restaurante.findByIdOptional(idRestaurante);
		if (optional.isEmpty())
			throw new NotFoundException("Restaurante não existe");
		List<Prato> pratos = Prato.list("restaurante", optional.get());
		return pratos;
	}

	@POST
	@Path("{idRestaurante}")
	@Transactional
	public Response adicionar(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {
		Optional<Restaurante> optional = Restaurante.findByIdOptional(idRestaurante);
		if (optional.isEmpty())
			throw new NotFoundException("Restaurante não existe");
		Prato prato = new Prato();
		prato.nome = dto.nome;
		prato.descricao = dto.descricao;
		prato.preco = dto.preco;
		prato.restaurante = optional.get();
		
		prato.persist();
		
		return Response.ok().build();
	}
	
	@PUT
	@Path("{idRestaurante}/{id}")
	@Transactional
	public void atualizar(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, Prato dto) {
		Optional<Restaurante> optionalRest = Restaurante.findByIdOptional(idRestaurante);
		if (optionalRest.isEmpty())
			throw new NotFoundException("Restaurante não existe");
		Optional<Prato> optional = Prato.findByIdOptional(id);
		if (optional.isEmpty())
			throw new NotFoundException("Prato não existe");
		Prato prato = optional.get();
		prato.preco = dto.preco;
		
		prato.persist();
	}

	@DELETE
	@Path("{idRestaurante}/{id}")
	@Transactional
	public void remover(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {
		Optional<Restaurante> optionalRest = Restaurante.findByIdOptional(idRestaurante);
		if (optionalRest.isEmpty())
			throw new NotFoundException("Restaurante não existe");
		Optional<Prato> optional = Prato.findByIdOptional(id);
		
		optional.ifPresentOrElse(Prato::delete, () -> {throw new NotFoundException();});
	}
	
}
