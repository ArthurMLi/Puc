#include <iostream>
#include <string>
using namespace std;
// Arthur mendes lima
//Ex02 - Adicionar Métodos Simples

class Livro {
public:
    string titulo;
    string autor;

    // Método para exibir as informações do livro
    void exibir() {
        cout << "Titulo: " << titulo << endl;
        cout << "Autor: " << autor << endl;
    }
};

int main() {
    Livro meuLivro;

    getline(cin, meuLivro.titulo);

    getline(cin, meuLivro.autor);

    meuLivro.exibir();

    return 0;
}
