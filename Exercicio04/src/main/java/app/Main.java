package app;

import okhttp3.*;
import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.util.Map;
import java.util.HashMap;

public class Main {
/* ------------------------- DADOS DA API ----------------------------- */
	
  private static final String ENDPOINT = "https://servicoscognitivosti2.cognitiveservices.azure.com/vision/v3.2/detect";
  private static final String SUBSCRIPTION_KEY = "8fb4266d88a746fbb20c64cf401103fe";

/* -------------------------- CHAMAR A API ----------------------------- */

  public static String detectObjects(byte[] imageBytes) throws IOException 
  {
  //Definir dados locais
    OkHttpClient client = new OkHttpClient();
  //Criação do corpo da requisição com a imagem
    RequestBody body = RequestBody.create(imageBytes, MediaType.parse("application/octet-stream"));
  //Montar a requisição HTTP
    Request request = new Request.Builder()
	    .url(ENDPOINT)
	    .addHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY)
	    .addHeader("Content-Type", "application/octet-stream")
	    .post(body)
	    .build();
  //Tentar executar a requisição
    try (Response response = client.newCall(request).execute()) 
    {
    //Testar se resposta foi bem sucedida
      if (!response.isSuccessful()) throw new IOException("Erro na requisição: " + response);
    //Retornar resultado bem sucedido
      return response.body().string();
    }
  }
  
  /* ---------------------- ANALISAR RESPOSTA DA API ----------------------- */
  
  public static String nomeValido(JsonObject object, double limite) 
  {
  //Definir dados locais
    double confidence = object.get("confidence").getAsDouble();
    String name = object.get("object").getAsString();
  //Testar confianca do objeto em si
    if (confidence >= limite) return name; 
  //Testar se o objeto possui parent
    if (object.has("parent")) 
    {
      JsonObject parent = object.getAsJsonObject("parent"); //Identificar o parent
      return nomeValido(parent, limite); // Verifica recursivamente o nome do parent
    }
  //Retornar
    return name;
  }
  
  public static boolean temConfianca(JsonObject object, double limite) 
  {
  //Definir dados locais
    double confidence = object.get("confidence").getAsDouble();
  //Testar confianca do elemento em si
    if (confidence >= limite) return true;
  //Verificar se o objeto tem "parent"
    if ( object.has("parent") ) 
    {
      JsonObject parent = object.getAsJsonObject("parent"); //Identificar o parent
      return temConfianca(parent, limite); //Verificar recursivamente
    }
  //Retornar
    return false;
  }
  
  public static Map<String, Integer> contarAnimais (String response)
  {
  //Definir dados locais
  	Gson gson = new Gson();
    Map<String, Integer> contagem = new HashMap<>();
    double limite = 0.7;
  //Converter resposta para JsonObject  
    JsonObject convertedObject = gson.fromJson(response, JsonObject.class);
  //Converter objeto JSON para arranjo de JSON
    JsonArray objects = convertedObject.getAsJsonArray ("objects");
  //Iterar para cada elemento no aray e adicioanr a contagem no HashMap
    for (JsonElement element : objects)
    {      	
    	if ( temConfianca(element.getAsJsonObject(), limite) )
    	{
    		String objectName = element.getAsJsonObject().get("object").getAsString();
    		contagem.put(objectName, contagem.getOrDefault(objectName, 0) + 1);    		
    	}
    }
  //Retornar
    return contagem;
  }
    
  public static void main(String[] args) 
  {
  //Tentar fazer a chamada da API
  	try 
    {
  	//Iterar para n casos
  		int n = 7;
  		for (int i = 1; i <= n; i++)
  		{
  		//Caminho para a imagem que será enviada
  			String imagePath = "./artefatos/" + i + ".jpg";
  			byte[] imageBytes = Files.readAllBytes(new File(imagePath).toPath());
  		//Enviar a requisição HTTP      
  			String response = detectObjects(imageBytes);    
  		//Contar quantidade de cada tipo de animal
  			Map<String, Integer> contagem = contarAnimais(response);
  		//Mostrar inicio da amostra:
  			System.out.println("Imagem testada: " + imagePath);
  		//Mostrar o resultado de cada especie de animal
  			System.out.print("  Objetos encontrados:");
  			contagem.forEach( (animal, count) -> System.out.print(" " + animal) );
  			System.out.print("\n  Quantidade encontrada:");
  			contagem.forEach( (animal, count) ->  System.out.print(" " + count + " " + animal + " ") );
  		//Mostrar response
  			System.out.println("\nResponse: " + response + "\n");			
  		}
    } 
  	catch (IOException e) 
  	{
        e.printStackTrace();
    }
  }
}


/*
	{
		"objects":[
			{
				"rectangle":{"x":22,"y":275,"w":287,"h":258},
				"object":"cat",
				"confidence":0.573,
				"parent":{
					"object":"mammal",
					"confidence":0.867,
					"parent":{
						"object":"animal",
						"confidence":0.868
					}
				}
			},
			{
				"rectangle":{"x":242,"y":96,"w":744,"h":460},
				"object":"dog",
				"confidence":0.889,
				"parent": {
					"object":"mammal",
					"confidence":0.953,
					"parent":{
						"object":"animal",
						"confidence":0.954
					}
				}
			}
		],
		"requestId":"956f6de4-b053-4e22-9198-2da52618abe0",
		"metadata":{"height":700,"width":1000,"format":"Jpeg"},
		"modelVersion":"2021-04-01"
*/







