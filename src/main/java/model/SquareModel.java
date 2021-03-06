package model;

/*
 * #%L
 * prgkorny-jatek
 * %%
 * Copyright (C) 2016 Debreceni Egyetem, Informatikai Kar
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


public class SquareModel {
    private int row, col;
    public int owner = 2;
    public boolean isNeutral = true;
    public int state = 0;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setState(int state) {
        this.state = state;
    }

    public SquareModel(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setOwner(int owner) {
        isNeutral = false;
        this.owner = owner;

        isNeutral = !(owner == 0 || owner == 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof SquareModel)) return false;
        return ((SquareModel) obj).owner == this.owner &&
                ((SquareModel) obj).row == this.row &&
                ((SquareModel) obj).col == this.col &&
                ((SquareModel) obj).state == this.state &&
                ((SquareModel) obj).isNeutral == this.isNeutral;
    }
}
