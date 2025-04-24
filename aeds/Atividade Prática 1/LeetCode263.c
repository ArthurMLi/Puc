bool isUgly(int n)
{

    /*Autor :Arthur Mendes Lima
    Checa se o numero é 0 pois ele quebra o codigo */

    if (n <= 0)
    {
        return 0;
    }
    /*fatora o valor por 2 ,3 e 5 */
    while (n % 2 == 0)
    {
        n /= 2;
    }
    while (n % 3 == 0)
    {
        n /= 3;
    }
    while (n % 5 == 0)
    {
        n /= 5;
    }
    /*Verifica se o resultado da fatoração foi igual a 1(Numero feio)*/

    return n == true;
}
