public class removermaiorquedez {
    static public int main(String args[]) {

        int ultimo = 6;
        int primeiro = 0;
        int removido;
        int v[] = new int[ultimo + 1];
        int pos = primeiro;
        boolean receba = true;
        for (int i = 0; i < args.length; i++) {
            if (v[i] > 10 && receba) {
                removido = v[i];
                receba = false;
                for (int j = 0; j < v.length; j++) {
                    
                    v[pos] = v[pos + 1];
                }
            }
            pos = (primeiro + 1);
        }
        return removido;
    }

}