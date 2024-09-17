package app;

import static spark.Spark.*;

import response.CorsFilter;
import service.LivroService;

import response.CorsFilter;


@SuppressWarnings("unused")
public class Aplicacao {
	public static LivroService livroService = new LivroService();
	public static CorsFilter corsFilter = new CorsFilter();
	
	public static void main (String args [])
	{
    port(4567);
	//End-point para receber a requisicao de OPTIONS (pre-flight)
		options( "/*", (request, response) -> CorsFilter.apply(response) );
	//End-point para receber a requisicao de POST unitario
		post( "/livro/insert", (request, response) -> livroService.insert (request, response) );
	//End-point para receber a requisicao de GET unitario
		get( "/livro/:id", (request, response) -> livroService.get (request, response) );
	//End-point para receber a requisicao de GET em grupo
		get( "/livro", (request, response) -> livroService.getAll (request, response) );
	//End-point para receber a requisicao de PUT unitario
		put( "/livro/update/:id", (request, response) -> livroService.update (request, response) );
	//End-point para receber a requisicao de DELETE unitario
		delete("/livro/delete/:id" , (request, response) -> livroService.delete (request, response) );
	}
}
