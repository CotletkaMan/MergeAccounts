import java.util.Objects;

public final class Pair<L, R> {
    final private L left;
    final private R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    public boolean equals(Object pair) {
        if (pair == null || !(pair instanceof Pair)) {
            return false;
        }

        Pair pairT  = (Pair)pair;

        return Objects.equals(left, pairT.getLeft()) && Objects.equals(right, pairT.getRight());
    }

    public int hashCode() {
        return Objects.hash(left, right);
    }
}
