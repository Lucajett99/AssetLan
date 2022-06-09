package utils.StEntry;

import ast.Node;

import java.util.Objects;

public class STEntryAsset extends STEntryVar {
    private int liquidity;

    public STEntryAsset(Node type, int offset, int nestingLevel, int liquidity) {
        super(type, offset, nestingLevel);
        this.liquidity = liquidity;
    }

    public int getLiquidity() {
        return liquidity;
    }

    public void setLiquidity(int liquidity) {
        this.liquidity = liquidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        STEntryAsset that = (STEntryAsset) o;
        return liquidity == that.liquidity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), liquidity);
    }
}
