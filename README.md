**Projeto AT3 N2 de Programação Concorrente e Distribuída.**

Este projeto implementa um servidor de biblioteca utilizando sockets em Java 17. O servidor permite a listagem, aluguel, devolução e cadastro de livros. Os dados dos livros são armazenados em um arquivo JSON que serve como uma base de dados.

**Estrutura do Projeto:**

O projeto é dividido nos seguintes pacotes e classes:

**model**

Livro: Representa um livro da biblioteca com atributos como título, autor, gênero e número de exemplares.

**server**

Biblioteca: Contém a lista de livros e métodos para manipulação dos livros (listar, alugar, devolver, cadastrar).

ServerBiblioteca: Implementa o servidor que se comunica com os clientes e manipula os dados da biblioteca.

**client**

ClientBiblioteca: Implementa o cliente que se comunica com o servidor para realizar operações.

**Funcionalidades:**

O servidor de biblioteca suporta as seguintes operações:

1 - Listar livros
2 - Alugar livro
3 - Devolver livro
4 - Cadastrar novo livro
5 - Sair
