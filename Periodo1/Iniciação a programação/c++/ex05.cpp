#include <iostream>
#include <iomanip>
using namespace std;
// Arthur mendes lima
//Ex05 - Métodos com Parâmetros e retorno
class Circulo
{
public:
    double raio;
    // Define o valor do raio diretamente
    void setRaio(double r)
    {
        raio = r;
    }
    
    // Calcula a circunferência usando o valor de pi fornecido
    double calcularCircunferencia(double pi)
    {
        return 2 * pi * raio;
    }

    // Imprime as informações formatadas
    void imprimirInformacoes()
    {
        cout << fixed << setprecision(2);
        cout << "Raio:" << raio << endl;
        cout << "Circunferencia do circulo: " << calcularCircunferencia(3.1416) << endl;
    }
};

int main()
{
    string prefixo;
    double valor;

    
    cin >> valor;

    Circulo c;
    c.setRaio(valor);
    c.imprimirInformacoes();

    return 0;
}
