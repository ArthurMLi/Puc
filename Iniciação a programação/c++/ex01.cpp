#include <iostream>
#include <string>
using namespace std;
// Arthur mendes lima
//Ex01 - Criar uma Classe Simples  


class Livro {
public:
    string titulo;
    string autor;

    // Método para exibir os dados do livro
    void exibir() {
        cout << "Titulo: " << titulo << endl;
        cout << "Autor: " << autor << endl;
    }
};

int main() {
    Livro meuLivro;

    getline(cin, meuLivro.titulo); // Lê título com espaços

    getline(cin, meuLivro.autor); // Lê autor com espaços

    meuLivro.exibir();

    return 0;
}
