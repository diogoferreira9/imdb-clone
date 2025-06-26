public class InputInvalido {
    String nomeFicheiro;
    int linhasOK;
    int linhasNaoOK;
    int primeiraLinhaErro;

    public InputInvalido(String nomeFicheiro, int linhasOK, int linhasNaoOK, int primeiraLinhaErro) {
        this.nomeFicheiro = nomeFicheiro;
        this.linhasOK = linhasOK;
        this.linhasNaoOK = linhasNaoOK;
        this.primeiraLinhaErro = primeiraLinhaErro;
    }

    public String getNomeFicheiro() { return nomeFicheiro; }
    public int getLinhasOK() { return linhasOK; }
    public int getLinhasNaoOK() { return linhasNaoOK; }
    public int getPrimeiraLinhaErro() { return primeiraLinhaErro; }

    @Override
    public String toString() {
        return nomeFicheiro + " | " + linhasOK + " | " + linhasNaoOK + " | " + primeiraLinhaErro;
    }
}
