#include <iostream>
#include <iomanip> // Necessário para setprecision
using namespace std;
// Arthur mendes lima

//Ex04 - Métodos sem Retorno

// Definição da classe Retangulo
class Retangulo {
private:
    double largura;
    double altura;

public:
    // Método para ler os dados
    void lerDados() {
        cin >> largura;
        cin >> altura;
    }

    // Método que calcula e retorna a área
    double calcularArea() {
        return largura * altura;
    }

    // Método que imprime os dados formatados
    void imprimir() {
        cout << fixed << setprecision(2); // Define 2 casas decimais
        cout << "Largura: " << largura << ", Altura: " << altura << endl;
        cout << " Area do retangulo: " << calcularArea() << endl;
    }
};

int main() {
    Retangulo r;

    r.lerDados();
    r.imprimir();

    return 0;
}
