package model;


public class Livro {
//Atributos privados da classe Livro
	
	private int id;
	private String nome;
	private String autor;
	private double preco;
	private String sinopse;
	private String genero;
	private int lancamento;
	private int paginas;
	private String editora;
	
//Construtores da classe Livro
	
	public Livro () 
	{
		this.id = -1;
		this.nome = "";
		this.autor = "";
		this.preco = 0.0;
		this.sinopse = "";
		this.genero = "";
		this.lancamento = 0;
		this.paginas = 0;
		this.editora = "";
	}
	
	public Livro ( int id, String nome, String autor, double preco, String sinopse, String genero, int lancamento, int paginas, String editora )
	{
		setLivroValues(id, nome, autor, preco, sinopse, genero, lancamento, paginas, editora);
	}
	
	public Livro ( String nome, String autor, double preco, String sinopse, String genero, int lancamento, int paginas, String editora )
	{
		setLivroValues(nome, autor, preco, sinopse, genero, lancamento, paginas, editora);
	}
	
//Metodos padrões de acesso aos atributos privados
	
	public void setLivroValues (int id, String nome, String autor, double preco, String sinopse, String genero, int lancamento, int paginas, String editora )
	{
		this.id = id;
		this.nome = nome;
		this.autor = autor;
		this.preco = preco;
		this.sinopse = sinopse;
		this.genero = genero;
		this.lancamento = lancamento;
		this.paginas = paginas;
		this.editora = editora;
	}
	
	public void setLivroValues (String nome, String autor, double preco, String sinopse, String genero, int lancamento, int paginas, String editora )
	{
		this.nome = nome;
		this.autor = autor;
		this.preco = preco;
		this.sinopse = sinopse;
		this.genero = genero;
		this.lancamento = lancamento;
		this.paginas = paginas;
		this.editora = editora;
	}
	
	public int getId ()
	{
		return id;
	}
	
	public String getNome ()
	{
		return nome;
	}
	
	public String getAutor ()
	{
		return autor;
	}
	
	public double getPreco ()
	{
		return preco;
	}
	
	public String getSinopse ()
	{
		return sinopse;
	}
	
	public String getGenero ()
	{
		return genero;
	}
	
	public int getLancamento ()
	{
		return lancamento;
	}
	
	public int getPaginas ()
	{
		return paginas;
	}
	
	public String getEditora ()
	{
		return editora;
	}
	
//Metodo para mostrar o livro em versao string
	
	public String toString ()
	{ 
		return "Nome: " + getNome() + ", Autor: " + getAutor() + ", Preco: " + getPreco() + ", Sinopse: " + getSinopse() + ", Genero: " + getGenero() + ", Lancamento: " + getLancamento() + ", Paginas: " + getPaginas() + ", Editora: " + getEditora() + ".";
	}

}
