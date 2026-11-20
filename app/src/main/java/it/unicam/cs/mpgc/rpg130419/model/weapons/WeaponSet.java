package it.unicam.cs.mpgc.rpg130419.model.weapons;

public class WeaponSet<W extends Equippable> {
    private W leftWeapon;
    private W rightWeapon;

    public WeaponSet(W leftWeapon, W rightWeapon) {
        this.leftWeapon = leftWeapon;
        this.rightWeapon = rightWeapon;
    }

    public void setLeftWeapon(W leftWeapon) {
        this.leftWeapon = leftWeapon;
    }

    public void setRightWeapon(W rightWeapon) {
        this.rightWeapon = rightWeapon;
    }

    public W getLeftWeapon() {
        return leftWeapon;
    }

    public W getRightWeapon() {
        return rightWeapon;
    }

    public int getWeaponSetPower() {
        return leftWeapon.getPower() + rightWeapon.getPower();
    }
}
