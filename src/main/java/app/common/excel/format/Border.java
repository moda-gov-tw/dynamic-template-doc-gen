/*
 * Copyright (c) 2010-2020 IISI.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of IISI.
 */
package app.common.excel.format;

import lombok.Getter;

/**
 * 框線
 */
@Getter
public enum Border {

    NONE(Border.no_border), //
    N(Border.no_border), //

    // 1
    T(Border.top), TOP(Border.top), //
    B(Border.bottom), BOTTOM(Border.bottom), //
    R(Border.right), RIGHT(Border.right), //
    L(Border.left), LEFT(Border.left), //

    // 2
    TB(Border.top | Border.bottom), //
    TR(Border.top | Border.right), //
    TL(Border.top | Border.left), //

    BT(Border.bottom | Border.top), //
    BR(Border.bottom | Border.right), //
    BL(Border.bottom | Border.left), //

    RT(Border.right | Border.top), //
    RB(Border.right | Border.bottom), //
    RL(Border.right | Border.left), //

    LT(Border.left | Border.top), //
    LB(Border.left | Border.bottom), //
    LR(Border.left | Border.right), //

    // 3
    NT(Border.box & ~Border.top), //
    NB(Border.box & ~Border.bottom), //
    NR(Border.box & ~Border.right), //
    NL(Border.box & ~Border.left), //

    ALL(Border.box), //
    BOX(Border.box), //
    A(Border.box), //
    ;

    private final int value;

    public static final int no_border = 0;
    public static final int top = 1;
    public static final int bottom = 2;
    public static final int left = 4;
    public static final int right = 8;
    public static final int box = 15;

    Border(int value) {
        this.value = value;
    }

    public boolean matchAny(int value) {
        return (this.value & value) > 0;
    }

    public boolean matchAny(Border otherBorder) {
        if (otherBorder == null) {
            return false;
        }
        return (this.value & otherBorder.value) > 0;
    }
}
