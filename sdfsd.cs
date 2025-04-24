using System;

class ex07
{
    static void Main()
    {
        double na = 1, np = 0, pxn, resp;
        int i = 1;
        Console.WriteLine(@"Digite um numero maior que 2:");
        resp = double.Parse(Console.ReadLine());
        while (resp <= 2)
        {
            Console.WriteLine("Numero invalido! Deve ser maior que 2.");
            Console.WriteLine(@"Digite um numero maior que 2:");
            resp = double.Parse(Console.ReadLine());
        }
        while (resp - 1 >= i)
        {
            pxn = np + na;
            np = na;
            na = pxn;
            i++;
        }
        Console.WriteLine($"A sequencia de Fibonacci ate o {resp} termo = {pxn}");
    }
}
