package dao;

import model.Livro;

import java.sql.*;

import java.util.List;

import java.util.ArrayList;


public class LivroDAO extends DAO {
//Construtor da classe LivroDAO
	
	public LivroDAO()
	{
		super();
		conectar();
	}
	
//Metodo para encerrar a conexao com o banco de dados
	public void finalize ()
	{
		close();
	}
	
//Metodos de interacao com o banco de dados (Criar, Editar, Excluir)
	
	public boolean inserirLivro (Livro livro)
	{
	//definir dados locais
		boolean status = false;
	//tentar fazer a chamada
		try
		{
			Statement st = getConexao().createStatement();
			st.executeUpdate("INSERT INTO livro (nome, autor, preco, sinopse, genero, lancamento, paginas, editora)"
					+ " VALUES ('" + livro.getNome() + "', '" + livro.getAutor() + "', " + livro.getPreco() + 
					", '" + livro.getSinopse() + "', '" + livro.getGenero() + "', " + livro.getLancamento() + ", " + livro.getPaginas() + 
					", '" + livro.getEditora() + "');");
			st.close();
			status = true;
		}
	//
		catch (SQLException u)
		{
			throw new RuntimeException(u);
		}
	//retornar
		return status;
	}

	public boolean atualizarLivro (Livro livro)
	{
	//Definir dados locais
		boolean status = false;
	//Tentar fazer a chamada
		try
		{
			Statement st = getConexao().createStatement();
			String sql = "UPDATE livro SET nome = '" + livro.getNome() + "', autor = '" + livro.getAutor() + "', preco = " + livro.getPreco() + 
					", sinopse = '" + livro.getSinopse() + "', genero = '" + livro.getGenero() + "', lancamento = " + livro.getLancamento() + 
					", paginas = " + livro.getPaginas() + ", editora = '" + livro.getEditora() + "' WHERE id = " + livro.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;
		}
	//Coletar o erro
		catch (SQLException u)
		{
			throw new RuntimeException(u);
		}
	//Retornar
		return status;
	}
	
	public boolean excluirLivro (int id)
	{
	//Definir dados locais
		boolean status = false;
	//Tentar fazer a chamada
		try
		{
			Statement st = getConexao().createStatement();
			st.executeUpdate("DELETE FROM livro WHERE id = " + id);
			st.close();
			status = true;
		}
	//Recolher o erro
		catch (SQLException u)
		{
			throw new RuntimeException(u);
		}
	//Retornar
		return status;
	}

//Metodos de leitura do banco de dados (Ler todos, ler um)
	
	public Livro getLivro (int id)
	{
	//Definir dados locais
		Livro livro = null;
	//Tentar fazer a chamada
		try
		{
			Statement st = getConexao().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM livro WHERE id = " + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next())
			{
				livro = new Livro ( rs.getInt("id"), rs.getString("nome"), rs.getString("autor"), 
						rs.getDouble("preco"), rs.getString("sinopse"), rs.getString("genero"),
						rs.getInt("lancamento"), rs.getInt("paginas"), rs.getString("editora") );
			}
			st.close();
		}
	//Recolher o erro
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	//Retornar
		return livro;
	}
	
	public List<Livro> getLivros (String orderBy)
	{
	//Definir dados locais
		List<Livro> livros = new ArrayList<Livro>();
	//Tentar fazer a chamada
		try
		{
			Statement st = getConexao().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM livro" + ( (orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy) );
			ResultSet rs = st.executeQuery(sql);
			while(rs.next())
			{
				Livro l = new Livro ( rs.getInt("id"), rs.getString("nome"), rs.getString("autor"), 
						rs.getDouble("preco"), rs.getString("sinopse"), rs.getString("genero"),
						rs.getInt("lancamento"), rs.getInt("paginas"), rs.getString("editora") );
				livros.add(l);
			}
			st.close();
		}
	//Recolher os erros
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	//Retornar
		return livros;
	}
}
