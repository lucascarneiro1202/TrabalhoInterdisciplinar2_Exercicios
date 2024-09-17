package service;

import java.util.List;

import com.google.gson.Gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import model.Livro;
import dao.LivroDAO;
import response.CorsFilter;
import response.StandardResponse;
import response.StandardResponse.StatusResponse;

import spark.Request;
import spark.Response;


public class LivroService {
	private LivroDAO livroDAO;
	private Gson gson;
	
	public LivroService ()
	{
		livroDAO = new LivroDAO();
		gson = new Gson();
	}
	
	public String get (Request request, Response response)
	{
	//Lidar com CORS policy
		response  = CorsFilter.apply(response);
	//Definir dados locais
		response.type("application/json");
		String data = "";
		Livro livro;
		int id = 0;
	//Tentar ler o id
		if ( request.params(":id") != null )
		{
			id = Integer.parseInt( request.params(":id") );
		//Tentar ler o elemento especificado
			livro = livroDAO.getLivro(id);
		//Testar busca
			if (livro != null)
			{
				StatusResponse status = StatusResponse.SUCCESS;
				JsonElement dataLivro = gson.toJsonTree(livro);
				StandardResponse resposta = new StandardResponse(status, dataLivro);
				data = gson.toJson(resposta);
				System.out.println(data);
				response.status(200); // success
			}
			else
			{
				StatusResponse status = StatusResponse.ERROR;
				String message = "Livro com ID (" + id + ") não foi encontrado.";
				StandardResponse resposta = new StandardResponse (status, message);
				data = gson.toJson(resposta);
	      response.status(404); // 404 Not found
			}
		}
	//Retornar
		return data;
	}
	
	public String insert (Request request, Response response)
	{;
	//Lidar com CORS policy
		response  = CorsFilter.apply(response);
	//Definir dados locais
		String requestBody = request.body();
		JsonObject requestJson = gson.fromJson (requestBody, JsonObject.class);
		String data = "";
	//Definir dados do objeto
		String nome = requestJson.get("nome").getAsString();
		String autor = requestJson.get("autor").getAsString();
		double preco = requestJson.get("preco").getAsDouble();
		String sinopse = requestJson.get("sinopse").getAsString();
		String genero = requestJson.get("genero").getAsString();
		int lancamento = requestJson.get("lancamento").getAsInt();
		int paginas = requestJson.get("paginas").getAsInt();
		String editora = requestJson.get("editora").getAsString();
	//Criar objeto Livro
		Livro livro = new Livro(nome, autor, preco, sinopse, genero, lancamento, paginas, editora);
	//Tentar fazer insercao no Banco de Dados
		if ( livroDAO.inserirLivro(livro) )
		{
			StatusResponse status = StatusResponse.SUCCESS;
			JsonElement dataLivro = gson.toJsonTree(livro);
			StandardResponse resposta = new StandardResponse(status, "O livro \'" + nome + "\' foi inserido com sucesso!", dataLivro);
			data = gson.toJson(resposta);
			System.out.println(data);			
      response.status(200); // 200 Found
		}
		else
		{
			StatusResponse status = StatusResponse.ERROR;
			StandardResponse resposta = new StandardResponse(status, "Não foi possível inserir o livro " + nome + ".");
			data = gson.toJson(resposta);
			response.status(404); // 404 Not found
		}
	//Retornar
		response.body(data);
		return data;
	}
	
	public String update (Request request, Response response)
	{;
	//Lidar com CORS policy
		response  = CorsFilter.apply(response);
	//Definir dados locais
		String requestBody = request.body();
		JsonObject requestJson = gson.fromJson (requestBody, JsonObject.class);
		String data = "";
	//Definir dados do objeto
		int id = requestJson.get("id").getAsInt();
		String nome = requestJson.get("nome").getAsString();
		String autor = requestJson.get("autor").getAsString();
		double preco = requestJson.get("preco").getAsDouble();
		String sinopse = requestJson.get("sinopse").getAsString();
		String genero = requestJson.get("genero").getAsString();
		int lancamento = requestJson.get("lancamento").getAsInt();
		int paginas = requestJson.get("paginas").getAsInt();
		String editora = requestJson.get("editora").getAsString();
	//Criar objeto Livro
		Livro livro = new Livro(id, nome, autor, preco, sinopse, genero, lancamento, paginas, editora);
	//Tentar fazer insercao no Banco de Dados
		if ( livroDAO.atualizarLivro(livro) )
		{
			StatusResponse status = StatusResponse.SUCCESS;
			JsonElement dataLivro = gson.toJsonTree(livro);
			StandardResponse resposta = new StandardResponse(status, "O livro \'" + nome + "\' foi alterado com sucesso!", dataLivro);
			data = gson.toJson(resposta);
			System.out.println(data);			
      response.status(200); // 200 Found
		}
		else
		{
			StatusResponse status = StatusResponse.ERROR;
			StandardResponse resposta = new StandardResponse(status, "Não foi possível inserir o livro " + nome + ".");
			data = gson.toJson(resposta);
			response.status(404); // 404 Not found
		}
	//Retornar
		response.body(data);
		return data;
	}
	
	public String getAll (Request request, Response response)
	{
		System.out.println("entrou");
	//Lidar com CORS policy
		response = CorsFilter.apply(response);
	//Definir dados locais
		response.type("application/json");
		String data = "";
		List<Livro> livros;
	//Tentar ler todos os livros
		livros = livroDAO.getLivros("");
	//Testar leituras
		if (livros != null)
		{
			StatusResponse status = StatusResponse.SUCCESS;
			JsonElement dataLivros = gson.toJsonTree(livros);
			StandardResponse resposta = new StandardResponse(status, "Todos os livros foram lidos com sucesso!", dataLivros);
			data = gson.toJson(resposta);
			System.out.println(data);
			response.status(200); // success
		}
		else
		{
			StatusResponse status = StatusResponse.ERROR;
			String message = "Não foi possível ler todos os dados.";
			StandardResponse resposta = new StandardResponse(status, message);
			data = gson.toJson(resposta);
      response.status(404); // 404 Not found
		}
	//Retornar
		return data;
	}
	
	public String delete (Request request, Response response)
	{
	//Lidar com CORS policy
		response = CorsFilter.apply(response);
	//Definir dados locais
		String data = "";
		int id = Integer.parseInt( request.params("id") );
	//Tentar fazer a exclusao
		if ( livroDAO.excluirLivro(id) )
		{
			StatusResponse status = StatusResponse.SUCCESS;
			StandardResponse resposta = new StandardResponse(status, "O livro de id = " + id + " foi excluído com sucesso!" );
			data = gson.toJson(resposta);
			response.status(200); // 200 DELETED
		}
		else
		{
			StatusResponse status = StatusResponse.ERROR;
			StandardResponse resposta = new StandardResponse(status, "O livro de id = " + id + " não foi encontrado!");
			data = gson.toJson(resposta);
			response.status(404); // 404 NOT FOUND
		}
	//Retornar
		return data;		
	}
}
