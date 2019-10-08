package hr.fer.zemris.trisat;

public class Clause {

    public Clause(int[] indexes) {

    }

    // vraća broj literala koji čine klauzulu
    public int getSize() {
        return -1;
    }

    // vraća indeks varijable koja je index-ti član ove klauzule
    public int getLiteral(int index) {
        return -1;
    }

    // vraća true ako predana dodjela zadovoljava ovu klauzulu
    public boolean isSatisfied(BitVector assignment) {
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
