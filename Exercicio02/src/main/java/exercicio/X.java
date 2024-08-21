package exercicio;

public class X {
//Atributos privados da classe X
	private int codigo;
	private String nome;
	private int idade;
	private double altura;
	private char sexo;
	
//Construtores da classe X
	public X ()
	{
		this.codigo = -1;
		this.nome = "";
		this.idade = -1;
		this.altura = -1.0;
		this.sexo = '*';
	}
	
	public X (int codigo, String nome, int idade, double altura, char sexo)
	{
		this.codigo = codigo;
		this.nome = nome;
		this.idade = idade;
		this.altura = altura;
		this.sexo = sexo;
	}
//Metodos de acesso para cada atributo da classe X
	public int getCodigo()
	{
		return codigo;
	}
	
	public void setCodigo (int codigo)
	{
		this.codigo = codigo;
	}
	

	public String getNome()
	{
		return nome;
	}
	
	public void setNome (String nome)
	{
		this.nome = nome;
	}
	

	public int getIdade()
	{
		return idade;
	}
	
	public void setIdade (int idade)
	{
		this.idade = idade;
	}
	

	public double getAltura()
	{
		return altura;
	}
	
	public void setAltura (double altura)
	{
		this.altura = altura;
	}
	

	public char getSexo()
	{
		return sexo;
	}
	
	public void setSexo (char sexo)
	{
		this.sexo = sexo;
	}
	
//Metodo para mostrar uma entidade da classe X
	public String toString ()
	{
		return "X [codigo=" + codigo + ", nome=" + nome + ", idade=" + idade + ", altura=" + altura + ", sexo=" + sexo + "]";
	}
	
}
