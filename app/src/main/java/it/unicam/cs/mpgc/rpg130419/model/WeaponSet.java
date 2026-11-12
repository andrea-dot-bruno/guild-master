package it.unicam.cs.mpgc.rpg130419.model;

import it.unicam.cs.mpgc.rpg130419.model.weapons.LeftFist;
import it.unicam.cs.mpgc.rpg130419.model.weapons.RightFist;

import java.util.ArrayList;
import java.util.List;

public class WeaponSet {
    private Weapon rightWeapon;
    private Weapon leftWeapon;

    public WeaponSet() {
        rightWeapon = new RightFist();
        leftWeapon = new LeftFist();
    }

    public void setRightWeapon(Weapon rightWeapon) {
        this.rightWeapon = rightWeapon;
    }
    public void setLeftWeapon(Weapon leftWeapon) {
        this.leftWeapon = leftWeapon;
    }

    public int getWeaponSetPower() {
        return leftWeapon.getPower() + rightWeapon.getPower();
    }

    public String getLeftWeaponIconPath() {
        return leftWeapon.getIconPath();
    }
    public String getRightWeaponIconPath() {
        return rightWeapon.getIconPath();
    }

    public int getLeftWeaponPower() {
        return leftWeapon.getPower();
    }
    public int getRightWeaponPower() {
        return rightWeapon.getPower();
    }

    public String getLeftWeaponName() {
        return leftWeapon.getName();
    }
    public String getRightWeaponName() {
        return rightWeapon.getName();
    }

}
