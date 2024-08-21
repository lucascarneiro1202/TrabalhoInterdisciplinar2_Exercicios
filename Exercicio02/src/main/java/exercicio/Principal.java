package exercicio;

import java.util.*;

public class Principal {
	public static Scanner sc = new Scanner (System.in);
	public static void sair ()
	{
		System.out.println("Saindo da aplicação...");
		System.out.println("Saiu com sucesso!");
	}
	
	public static void listar ()
	{
	//Estabeler conexão com o Banco de Dados
		DAO dao = new DAO();
	//Testar se conexão foi bem efetuada
		if ( dao.conectar() )
		{
		//Definir dados locais
			X xs [] = null;
		//Chamar funcao para ler tabela
			xs = dao.getXs();
		//Testar se a chamada foi concluida
			if (xs != null)
			{
				int n = xs.length;
				for (int i = 0; i < n; i++)
				{
					System.out.println ( xs[i].toString() );
				}
			}
		//Encerrar conexão
			dao.close();
		}		
	}
	
	public static void inserir ()
	{
	//Estabeler conexão com o Banco de Dados
		DAO dao = new DAO();
	//Testar se conexão foi bem efetuada
		if ( dao.conectar() )	
		{
		//Definir elemento padrao
			X x = new X ();
		//Ler dados
			System.out.printf( "Entrar com o código: " );
			x.setCodigo ( sc.nextInt() );
			System.out.printf( "Entrar com o nome: " );
			x.setNome ( sc.next() );
			System.out.printf( "Entrar com a idade: " );
			x.setIdade( sc.nextInt() );
			System.out.printf( "Entrar com a altura (metros): " );
			x.setAltura ( sc.nextDouble() );
			System.out.printf( "Entrar com o sexo (M/F): " );
			x.setSexo ( sc.next().charAt(0) );
		//Inserir objeto na tabela
			if ( dao.inserirX(x) )
			{
				System.out.println("Elemento inserido com sucesso!");
			}
		//Encerrar conexão
			dao.close();			
		}
	}
	
	public static void excluir ()
	{
	//Estabeler conexão com o Banco de Dados
		DAO dao = new DAO();
	//Testar se conexão foi bem efetuada
		if ( dao.conectar() )	
		{
		//Definir dados locais
			int codigo;
		//Ler dados
			System.out.printf( "Entrar com o código de um elemento a ser excluído: " );
			codigo = sc.nextInt();
		//Excluir elemento da tabela
			if ( dao.excluirX(codigo) )
			{
				System.out.println("Elemento excluído com sucesso!");				
			}
		//Encerrar conexão
			dao.close();
		}
	}
	
	public static void atualizar ()
	{
	//Estabeler conexão com o Banco de Dados
		DAO dao = new DAO();
	//Testar se conexão foi bem efetuada
		if ( dao.conectar() )	
		{
		//Definir elemento padrao
			X x = new X ();
		//Ler dados
			System.out.printf( "Entrar com o código do elemento a ser atualizado: " );
			x.setCodigo ( sc.nextInt() );
			System.out.printf( "Entrar com o novo nome: " );
			x.setNome ( sc.next() );
			System.out.printf( "Entrar com a nova idade: " );
			x.setIdade( sc.nextInt() );
			System.out.printf( "Entrar com a nova altura: " );
			x.setAltura ( sc.nextDouble() );
			System.out.printf( "Entrar com o novo sexo: " );
			x.setSexo ( sc.next().charAt(0) );
		//Inserir objeto na tabela
			if ( dao.atualizarX(x) )
			{
				System.out.println("Elemento atualizado com sucesso!");
			}
		//Encerrar conexão
			dao.close();
		}		
	}

	public static void main(String[] args) {
	//Definir dados locais
		int n;
	//Mostrar opções
		System.out.println ("Escolha entre as seguintes opções: \n"
											+ "  1 - Listar elementos da tabela. \n"
											+ "  2 - Inserir novo elemento na tabela. \n"
											+ "  3 - Excluir elemento pre-existente na tabela. \n" 
											+ "  4 - Atualizar elemento pre-existente na tabela. \n"
											+ "  0 - Sair.");
	//Condicao de repeticao
		do
		{
		//Ler dado
			n = sc.nextInt();
		//Escolher metodo correspontente
			switch (n)
			{
			case 0: sair(); break;
			case 1: listar(); break;
			case 2: inserir(); break;
			case 3: excluir(); break;
			case 4: atualizar(); break;
			default: System.out.println ("ERRO: Valor inválido."); break;
			}			
		} while ( n != 0 );
	}
}
