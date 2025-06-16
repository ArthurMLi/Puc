#include <iostream>
using namespace std;
// Arthur mendes lima
//Ex03 - Métodos com Retorno de Valor

class Retangulo {
public:
    double largura;
    double altura;

    // Método que calcula e retorna a área
    double calcularArea() {
        return largura * altura;
    }
};

int main() {
    Retangulo r;

    cin >> r.largura;

    cin >> r.altura;

    double area = r.calcularArea();

    cout << area << endl;

    return 0;
}
