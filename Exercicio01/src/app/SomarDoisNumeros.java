package app;

import java.util.*;


public class SomarDoisNumeros {

	public static void main(String[] args) {
	//Definir dados locais
		@SuppressWarnings("resource")
		Scanner sc = new Scanner (System.in);
		int x, y, soma;
	//Ler numeros inteiros
		System.out.printf("Digite um número: ");
		x = sc.nextInt();
		System.out.printf("Digite outro número: ");
		y = sc.nextInt();
	//Somar os dois números
		soma = x + y;
	//Mostrar resultado
		System.out.println(x + " + " + y + " = " + soma);
	}

}
