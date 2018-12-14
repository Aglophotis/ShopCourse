package ru.aglophotis.mirea.microservice.cart.shop.entities;

import ru.aglophotis.mirea.microservice.cart.shop.data.DatabaseContract;

import javax.persistence.*;

@Entity
@Table(name = DatabaseContract.TABLE_NAME)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DatabaseContract.COLUMN_ID)
    private int id;

    @Column(name = DatabaseContract.COLUMN_AUTHOR_ID)
    private int authorId;

    @Column(name = DatabaseContract.COLUMN_ITEM_ID)
    private int itemId;

    public CartItem(int authorId, int itemId) {
        this.authorId = authorId;
        this.itemId = itemId;
    }

    public CartItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
